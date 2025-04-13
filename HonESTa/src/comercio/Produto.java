package comercio;

import util.Validator;

public class Produto {
    private final String codigoBarras, marca, modelo;
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
