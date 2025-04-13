package cliente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import comercio.Produto;
import comercio.ProdutoVendido;
import comercio.Venda;
import util.Validator;

/**
 * Classe que representa um cupão emitido pela cadeia de lojas HonESta. Este
 * cupão pode ser associado a um ou mais cartões de fidelização. Cada cupão dá
 * direito a um desconto (em cartão) na compra dos produtos que abrange.
 */
public class Cupao {
    private final String id;
    private final String descricao;
    private final float desconto;
    private final LocalDate inicio;
    private final LocalDate validade;
    private List <Produto> produtos;

    public Cupao(String id, String descricao, float desconto, int inicio, int validade, List<Produto> produtos) {
        this.id = Validator.requireNonBlank(id);

        this.descricao = Validator.requireNonBlank(descricao);

        this.desconto = Validator.requireInsideRange(desconto, 0, 1);

        this.validade = LocalDate.now().plusDays(Validator.requirePositive(validade));

        this.inicio = LocalDate.now().plusDays(Validator.requirePositive(inicio));

        this.produtos = Objects.requireNonNull(produtos);
    }

    // Getters

    public String getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public float getDesconto() {
        return desconto;
    }

    public LocalDate getInicio(){
        return inicio;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    // Setters

    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = Objects.requireNonNull(produtos);
    }

    public boolean eFuturo(){
        return inicio.isAfter(LocalDate.now());
    }

    public boolean estaValido() {
        return (LocalDate.now().isBefore(validade) || LocalDate.now().equals(validade));
    }

    public boolean estaValido(LocalDate data) {
        return (data.isBefore(validade) || data.equals(validade));
    }

    /**
     * Aplica o cupão de desconto a um cartão e a uma venda. Se a venda contiver
     * algum dos produtos abrangidos pelo cupão, este é dado como tendo sido
     * aplicado. Se for aplicado deve ser removido do cartão.
     * 
     * @param cartao o cartão a ser usado na venda
     * @param venda a venda a ser processada
     * @return true se o cupão foi aplicado na venda
     */

    //🤓☝️
    public boolean aplicar(Cartao cartao, Venda venda) {
        boolean aplicadoEmPeloMenosUm = false;
        for(ProdutoVendido produto : venda.getProdutosVendidos()) {
            if(abrange(produto)) {
                aplicar(cartao, produto);
                aplicadoEmPeloMenosUm = true;
            }
        }

        return aplicadoEmPeloMenosUm;
    }

    /**
     * Indica se o produto indicado é abrangido pelo cupão. O produto é abrangido
     * pelo cupão se fizer parte da lista dos produtos e não tiver já sido aplicado
     * ao produto um desconto maior do que o dado pelo cupão.
     * 
     * @param produto o produto a testar
     * @return true, se o produto é abrangido pela cupão
     */
    public boolean abrange(ProdutoVendido produto) {
        return produtos.contains(produto.getProduto()) && produto.getDescontoAplicado() < desconto;
    }

    /**
     * Método auxiliar para aplicar o cupão a um produto.
     * 
     * @param cartao o cartão onde acumular o saldo
     * @param produto o produto a ser usado
     */
    private void aplicar(Cartao cartao, ProdutoVendido produto) {
        produto.setDescontoAplicado(desconto);
        cartao.acumularSaldo((long)(produto.getPreco() * desconto));
    }
}
