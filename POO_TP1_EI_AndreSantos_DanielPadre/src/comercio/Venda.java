package comercio;

import cliente.Cupao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe responsável por armazenar os produtos vendidos numa venda. Tem a data
 * em que foi realizada, os produtos e os cupões aplicados.
 */
public class Venda {
    //Indica a data que foi efetuada a venda
    private LocalDate dataVenda;
    //Armazena os produtos que foram vendidos
    private List<ProdutoVendido> produtosVendidos;
    //Armazena os cupoes que foram utilizados na venda
    private List<Cupao> cupoesUsados;
    //Indica o preço total da venda
    private long totalCompra;

    public long getTotalCompra() {
        return totalCompra;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public List<ProdutoVendido> getProdutosVendidos() {
        return produtosVendidos;
    }

    public List<Cupao> getCupoesUsados() {
        return cupoesUsados;
    }

    public Venda(LocalDate dataVenda, List<ProdutoVendido> produtosVendidos, List<Cupao> cupoesUsados) {
        this.dataVenda = Objects.requireNonNull(dataVenda);

        this.produtosVendidos = Objects.requireNonNull(produtosVendidos);

        this.cupoesUsados = Objects.requireNonNull(cupoesUsados);
    }

    public Venda(LocalDate dataVenda, List<ProdutoVendido> produtosVendidos) {
        this(dataVenda, produtosVendidos, new ArrayList<>());
    }

    public Venda(){
        this(LocalDate.now(), new ArrayList<>(), new ArrayList<>());
    }

    //Recebe um cupao e retorna true se esse cupao foi utilizado na venda
    public boolean foiUsado(Cupao cupao) {
        return cupoesUsados.contains(cupao);
    }

    //Adiciona um Produto a lista de produtos vendidos
    public void adicionarProduto(ProdutoVendido produto) {
        produtosVendidos.add(Objects.requireNonNull(produto));
        totalCompra += produto.getPreco();
    }

    //Adiciona um cupao a lista de cupoes utilizados
    public void adicionarCupaoUsado(Cupao cupao) {
        cupoesUsados.add(Objects.requireNonNull(cupao));
    }
}
