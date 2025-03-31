package comercio;

public class ProdutoInfo {
    private final String codigoBarras, marca, modelo;
    private long precoAtual;

    public ProdutoInfo(String codigoBarras, String marca, String modelo, long precoAtual) {
        verficarCodigoDeBarras(codigoBarras);
        this.codigoBarras = codigoBarras;

        verificarMarca(marca);
        this.marca = marca;

        verificarModelo(modelo);
        this.modelo = modelo;

        verificarPreco(precoAtual);
        this.precoAtual = precoAtual;
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

    public void setPrecoAtual(long precoAtual) {
        verificarPreco(precoAtual);
        this.precoAtual = precoAtual;
    }

    // Verificações

    private void verficarCodigoDeBarras(String codigoBarras) {
        if(codigoBarras == null || codigoBarras.isBlank()) {
            throw new IllegalArgumentException("Codigo de barras nao pode ser vazio");
        }
    }

    public void verificarPreco(long precoAtual) {
        if (precoAtual <= 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo ou zero!");
        }
    }

    public void verificarMarca(String marca) {
        if (marca == null || marca.isBlank()) {
            throw new IllegalArgumentException("A marca não pode ser vazia ou nulo!");
        }
    }

    public void verificarModelo(String modelo) {
        if (modelo == null || modelo.isBlank()) {
            throw new IllegalArgumentException("O modelo não pode ser vazia ou nulo!");
        }
    }
}
