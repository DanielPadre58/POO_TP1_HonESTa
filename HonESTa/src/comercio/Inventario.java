package comercio;

import cliente.Cartao;
import cliente.Cupao;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa o inventário da empresa, desde os produtos, até aos
 * cartões e cupões. Responsável por armazenar todos esses dados, bem como
 * fornecer métodos de pesquisa que usem os códigos para descobrir os produtos.
 */
public class Inventario {
    List<ProdutoInfo> stockProdutos;
    List<Cartao> cartoes;
    List<Cupao> cupoesEmpresa;

    public List<Cartao> getCartoes() {
        return cartoes;
    }

    public List<ProdutoInfo> getStockProdutos() {
        return stockProdutos;
    }

    public List<Cupao> getCupoesEmpresa() {
        return cupoesEmpresa;
    }

    public Inventario(List<ProdutoInfo> stockProdutos, List<Cartao> cartoes, List<Cupao> cupoesEmpresa) {
        verificarProdutos(stockProdutos);
        this.stockProdutos = new ArrayList<>();

        verificarCartoes(cartoes);
        this.cartoes = cartoes;

        verificarCupoes(cupoesEmpresa);
        this.cupoesEmpresa = cupoesEmpresa;
    }

    public Inventario(List<ProdutoInfo> stockProdutos) {
        this(stockProdutos, new ArrayList<>(), new ArrayList<>());
    }

    public Inventario() {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    private void verificarCupoes(List<Cupao> cupoes) {
        if(cupoes == null) {
            throw new IllegalArgumentException("A lista de cupoes da empresa nao deve ser nula!");
        }
    }

    private void verificarProdutos(List<ProdutoInfo> stockProdutos) {
        if(stockProdutos == null) {
            throw new IllegalArgumentException("A lista de produtos em stock nao deve ser nula!");
        }
    }

    private void verificarCartoes(List<Cartao> cartoes) {
        if(cartoes == null) {
            throw new IllegalArgumentException("A lista de cartoes nao deve ser nula!");
        }
    }

    /**
     * Retorna qual o produto que tem um dado código de barras
     * 
     * @param codigoBarras o código de barras do produto que se pretende
     * @return o produto com o código de barras, ou null caso não exista
     */
    public ProdutoInfo getProduto(String codigoBarras) {
        for (ProdutoInfo produto : stockProdutos){
            if(produto.getCodigoBarras().equals(codigoBarras)) {
                return produto;
            }
        }

        return null;
    }

    /**
     * Retorna o cartão com um dado número
     * 
     * @param numero o número do cartão a procurar
     * @return o cartão com o número pedido, ou null caso não exista
     */
    public Cartao getCartao(String numero) {
        for (Cartao cartao : cartoes){
            if (cartao.getId().equals(numero)) {
                return cartao;
            }
        }

        return null;
    }

    /**
     * Retorna o cupão com um dado número
     * 
     * @param numero o número do cupão a procurar
     * @return o cupão com o número pedido, ou null caso não exista
     */
    public Cupao getCupao(String numero) {
        for (Cupao cupao : cupoesEmpresa){
            if (cupao.getId().equals(numero)) {
                return cupao;
            }
        }

        return null;
    }

    public void adicionarProduto(ProdutoInfo produto) {
        stockProdutos.add(produto);
    }

    public void adicionarCartao(Cartao cartao) {
        cartoes.add(cartao);
    }

    public void adicionarCupao(Cupao cupao) {
        cupoesEmpresa.add(cupao);
    }
}
