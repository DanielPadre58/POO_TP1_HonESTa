package cliente;

import comercio.ProdutoInfo;

/**
 * Representa um produto vendido numa compra. Atenção, esta classe não contém a
 * informação sobre o produto propriamente dito (para isso usa um ProdutoInfo),
 * mas sim sobre as condições de venda desse produto, nomeadamente o preço usado
 * na altura e o desconto.
 */
public class ProdutoVendido {
    final private ProdutoInfo produto;
    final private long preco;
    private long descontoAplicado;

    public ProdutoInfo getProduto() {
        return produto;
    }

    public long getPreco() {
        return preco;
    }

    public long getDescontoAplicado() {
        return descontoAplicado;
    }

    public void setDescontoAplicado(long descontoAplicado) {
        this.descontoAplicado = descontoAplicado;
    }

    public ProdutoVendido(ProdutoInfo produto, long preco, long descontoAplicado) {
        validarProduto(produto);
        this.produto = produto;

        validarPreco(preco);
        this.preco = preco;

        validarDesconto(descontoAplicado);
        this.descontoAplicado = descontoAplicado;
    }

    public ProdutoVendido(ProdutoInfo produto, long preco) {
        validarProduto(produto);
        this.produto = produto;

        validarPreco(preco);
        this.preco = preco;

        this.descontoAplicado = 0;
    }

    private void validarDesconto(long descontoAplicado) {
        if(descontoAplicado < 0){
            throw new RuntimeException("Desconto nao pode ser negativo");
        }
    }

    private void validarPreco(long preco) {
        if(preco <= 0){
            throw new IllegalArgumentException("Preco do produto deve ser maior que zero");
        }
    }

    private void validarProduto(ProdutoInfo produto) {
        if(produto == null) {
            throw new IllegalArgumentException("Produto vendido nao pode ser nulo");
        }
    }
}
