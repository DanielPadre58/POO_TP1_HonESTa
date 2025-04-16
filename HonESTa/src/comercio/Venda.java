package comercio;

import cliente.Cupao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Classe responsável por armazenar os produtos vendidos numa venda. Tem a data
 * em que foi realizada, os produtos e os cupões aplicados.
 */
public class Venda {
    private LocalDate dataVenda;
    private ArrayList<ProdutoVendido> produtosVendidos;
    private ArrayList<Cupao> cupoesUsados;
    private long totalCompra;

    public long getTotalCompra() {
        return totalCompra;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public ArrayList<ProdutoVendido> getProdutosVendidos() {
        return produtosVendidos;
    }

    public ArrayList<Cupao> getCupoesUsados() {
        return cupoesUsados;
    }

    public Venda(LocalDate dataVenda, ArrayList<ProdutoVendido> produtosVendidos, ArrayList<Cupao> cupoesUsados) {
        this.dataVenda = Objects.requireNonNull(dataVenda);

        this.produtosVendidos = Objects.requireNonNull(produtosVendidos);

        this.cupoesUsados = Objects.requireNonNull(cupoesUsados);
    }

    public Venda(LocalDate dataVenda, ArrayList<ProdutoVendido> produtosVendidos) {
        this(dataVenda, produtosVendidos, new ArrayList<>());
    }

    public Venda(){
        this(LocalDate.now(), new ArrayList<>(), new ArrayList<>());
    }

    public boolean foiUsado(Cupao cupao) {
        return cupoesUsados.contains(cupao);
    }

    public void adicionarProduto(ProdutoVendido produto) {
        produtosVendidos.add(Objects.requireNonNull(produto));
        totalCompra += produto.getPreco();
    }

    public void adicionarCupaoUsado(Cupao cupao) {
        cupoesUsados.add(Objects.requireNonNull(cupao));
    }
}
