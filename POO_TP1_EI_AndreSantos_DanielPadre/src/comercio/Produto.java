package comercio;

import util.Validator;

public class Produto {
    //Codigo de barras serve como ID do produto
    //Marca indica a marca do produto
    //O modelo indica o nome do produto
    private final String codigoBarras, marca, modelo;
    //Indica o preço do produto
    private long precoAtual;

    public Produto(String codigoBarras, String marca, String modelo, long precoAtual) {
        this.codigoBarras = Validator.requireNonBlank(codigoBarras);

        this.marca = Validator.requireNonBlank(marca);

        this.modelo = Validator.requireNonBlank(modelo);

        this.precoAtual = Validator.requirePositive(precoAtual);
    }

    // Getters

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public long getPrecoAtual() {
        return precoAtual;
    }

    // Setters

    public void alterarPreco(long precoAtual) {
        this.precoAtual = Validator.requirePositive(precoAtual);
    }
}
