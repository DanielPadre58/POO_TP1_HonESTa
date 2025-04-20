package loja;

import cliente.Cartao;
import cliente.Cupao;
import comercio.Produto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe que representa o inventário da empresa, desde os produtos, até aos
 * cartões e cupões. Responsável por armazenar todos esses dados, bem como
 * fornecer métodos de pesquisa que usem os códigos para descobrir os produtos.
 */
public class Inventario {
    //Armazena todos os produtos em stock
    List<Produto> stockProdutos;
    //Armazena todos os cartoes disponiveis na loja
    List<Cartao> cartoes;
    //Armazena todos os cupoes disponiveis na loja
    List<Cupao> cupoesEmpresa;

    public List<Cartao> getCartoes() {
        return cartoes;
    }

    public List<Produto> getStockProdutos() {
        return stockProdutos;
    }

    public List<Cupao> getCupoesEmpresa() {
        return cupoesEmpresa;
    }

    public Inventario(List<Produto> stockProdutos, List<Cartao> cartoes, List<Cupao> cupoesEmpresa) {
        this.stockProdutos = Objects.requireNonNull(stockProdutos);

        this.cartoes = Objects.requireNonNull(cartoes);

        this.cupoesEmpresa = Objects.requireNonNull(cupoesEmpresa);
    }

    public Inventario(List<Produto> stockProdutos) {
        this(stockProdutos, new ArrayList<>(), new ArrayList<>());
    }

    public Inventario() {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    /**
     * Retorna qual o produto que tem um dado código de barras
     * 
     * @param codigoBarras o código de barras do produto que se pretende
     * @return o produto com o código de barras, ou null caso não exista
     */
    public Produto getProduto(String codigoBarras) {
        for (Produto produto : stockProdutos){
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

    //Adiciona um Produto a lista de produtos
    public void adicionarProduto(Produto produto) {
        stockProdutos.add(Objects.requireNonNull(produto));
    }

    //Adiciona um Cartao a lista de cartoes
    public void adicionarCartao(Cartao cartao) {
        cartoes.add(Objects.requireNonNull(cartao));
    }

    //Adiciona um Cupao a lista de cupoes
    public void adicionarCupao(Cupao cupao) {
        cupoesEmpresa.add(Objects.requireNonNull(cupao));
    }
}
