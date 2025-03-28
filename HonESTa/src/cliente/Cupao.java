package cliente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import comercio.ProdutoInfo;


/*Cupao{
	final String id;
	final byte desconto;
	final String descricaoProdutoValidos;
	LocalDate validade
	ArrayList<Produto>
}
 */

/**
 * Classe que representa um cupão emitido pela cadeia de lojas HonESta. Este
 * cupão pode ser associado a um ou mais cartões de fidelização. Cada cupão dá
 * direito a um desconto (em cartão) na compra dos produtos que abrange.
 */
public class Cupao {
    private final String id;
    private final String descricao;
    private final byte desconto;
    private final LocalDate validade;
    private List <ProdutoInfo> produtos;

    public Cupao(String id, String descricao, byte desconto, LocalDate validade,
        List<ProdutoInfo> produtos) {

        verificarId(id);
        this.id = id;
        verificarDescricao(descricao);
        this.descricao = descricao;
        verificarCupao(desconto);
        this.desconto = desconto;
        verificarValidade(validade);
        this.validade = validade;
        verificarProdutos(produtos);
        this.produtos = produtos;
    }

    // Verificações

    public void verificarId(String id) {
        if (id == null || id.isEmpty() || id.isBlank()) {
            throw new IllegalArgumentException("O id não pode ser vazio ou nulo!");
        }
    }

    public void verificarDescricao(String descricao) {
        if (descricao == null || descricao.isEmpty() || descricao.isBlank()) {
            throw new IllegalArgumentException("A descrição não pode ser vazia ou nula!");
        }
    }

    public void verificarCupao(byte desconto) {
        if (desconto <= 0 || desconto >= 100) {
            throw new IllegalArgumentException("O desconto não pode ser negativo ou igual a 0 ou maior ou igual que 100!");
        }
    }

    public void verificarValidade(LocalDate validade) {
        if (validade == null || validade.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A validade não pode ser nula ou anterior a de hoje!");
        }
    }

    public void verificarProdutos(List<ProdutoInfo> produtos) {
        if (produtos == null || produtos.isEmpty()) {
            throw new IllegalArgumentException("A lista de produtos não pode ser vazia ou nula!");
        }
    }

    // Getters

    public String getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public byte getDesconto() {
        return desconto;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public List<ProdutoInfo> getProdutos() {
        return produtos;
    }

    // Setters

    public void setProdutos(ArrayList<ProdutoInfo> produtos) {
        this.produtos = produtos;
    }

    public boolean estaValido() {
        if (validade.isBefore(LocalDate.now())) {
            return false;
        }
        return true;
    }

    public boolean estaValido(LocalDate data) {
        if (data.isBefore(validade) || data.equals(validade)) {
            return true;
        }
        return false;
    }

    /**
     * Aplica o cupão de desconto a um cartão e a uma venda. Se a venda contiver
     * algum dos produtos abrangidos pelo cupão, este é dado como tendo sido
     * aplicado. Se for aplicado deve ser removido do cartão.
     * 
     * @param c o cartão a ser usado na venda
     * @param v a venda a ser processada
     * @return true se o cupão foi aplicado na venda
     */
    public boolean aplicar(Cartao c, Venda v) {
        // TODO implementar este método
        return false;
    }

    /**
     * Indica se o produto indicado é abrangido pelo cupão. O produto é abrangido
     * pelo cupão se fizer parte da lista dos produtos e não tiver já sido aplicado
     * ao produto um desconto maior do que o dado pelo cupão.
     * 
     * @param p o produto a testar
     * @return true, se o produto é abrangido pela cupão
     */
    public boolean abrange(ProdutoVendido p) {
        // TODO implementar este método
        return false;
    }

    /**
     * Método auxiliar para aplicar o cupão a um produto.
     * 
     * @param c o cartão onde acumular o saldo
     * @param p o produto a ser usado
     */
    private void aplicar(Cartao c, ProdutoVendido p) {
        // TODO implementar este método
    }
}
