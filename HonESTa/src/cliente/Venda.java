package cliente;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Classe responsável por armazenar os produtos vendidos numa venda. Tem a data
 * em que foi realizada, os produtos e os cupões aplicados.
 */
public class Venda {
    private LocalDate dataVenda;
    private ArrayList<ProdutoVendido> produtosVendidos;
    private ArrayList<Cupao> cupoesUsados;

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
        verificarDataVenda(dataVenda);
        this.dataVenda = dataVenda;

        verificarProdutosVendidos(produtosVendidos);
        this.produtosVendidos = produtosVendidos;

        verificarCupoesUsados(cupoesUsados);
        this.cupoesUsados = cupoesUsados;
    }

    public Venda(LocalDate dataVenda, ArrayList<ProdutoVendido> produtosVendidos) {
        verificarDataVenda(dataVenda);
        this.dataVenda = dataVenda;

        verificarProdutosVendidos(produtosVendidos);
        this.produtosVendidos = produtosVendidos;

        this.cupoesUsados = new ArrayList<>();
    }

    private void verificarCupoesUsados(ArrayList<Cupao> cupoesUsados) {
        if(cupoesUsados == null){
            throw new IllegalArgumentException("Lista de cupoes usado nao pode ser nula");
        }
    }

    private void verificarProdutosVendidos(ArrayList<ProdutoVendido> produtosVendidos) {
        if (produtosVendidos == null) {
            throw new IllegalArgumentException("Lista de produtos vendidos nao deve ser nula");
        }
    }

    private void verificarDataVenda(LocalDate dataVenda) {
        if(dataVenda == null){
            throw new IllegalArgumentException("Data de venda nao deve ser nula");
        }
    }

    public boolean foiUsado(Cupao cupao) {
        return cupoesUsados.contains(cupao);
    }
}
