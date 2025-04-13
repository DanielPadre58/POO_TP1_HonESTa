package comercio;

/**
 * Representa um produto vendido numa compra. Atenção, esta classe não contém a
 * informação sobre o produto propriamente dito (para isso usa um ProdutoInfo),
 * mas sim sobre as condições de venda desse produto, nomeadamente o preço usado
 * na altura e o desconto.
 */
public class ProdutoVendido {
    final private Produto produto;
    final private long preco;
    private float descontoAplicado;

    public Produto getProduto() {
        return produto;
    }

    public long getPreco() {
        return preco;
    }

    public float getDescontoAplicado() {
        return descontoAplicado;
    }

    public void setDescontoAplicado(float descontoAplicado) {
        this.descontoAplicado = descontoAplicado;
    }

    public ProdutoVendido(Produto produto, long preco, long descontoAplicado) {
        validarProduto(produto);
        this.produto = produto;

        validarPreco(preco);
        this.preco = preco;

        validarDesconto(descontoAplicado);
        this.descontoAplicado = descontoAplicado;
    }

    public ProdutoVendido(Produto produto, long preco) {
        this(produto, preco, 0);
    }

    public ProdutoVendido(Produto produto) {
        validarProduto(produto);
        this.produto = produto;

        this.preco = produto.getPrecoAtual();

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

    private void validarProduto(Produto produto) {
        if(produto == null) {
            throw new IllegalArgumentException("Produto vendido nao pode ser nulo");
        }
    }
}
