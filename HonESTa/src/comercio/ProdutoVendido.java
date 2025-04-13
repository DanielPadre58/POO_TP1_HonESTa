package comercio;

import util.Validator;

import java.util.Objects;

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
        this.produto = Objects.requireNonNull(produto);

        this.preco = Validator.requirePositive(preco);

        this.descontoAplicado = Validator.requirePositiveOrZero(descontoAplicado);
    }

    public ProdutoVendido(Produto produto, long preco) {
        this(produto, preco, 0);
    }

    public ProdutoVendido(Produto produto) {
        this.produto = Objects.requireNonNull(produto);

        this.preco = produto.getPrecoAtual();

        this.descontoAplicado = 0;
    }
}
