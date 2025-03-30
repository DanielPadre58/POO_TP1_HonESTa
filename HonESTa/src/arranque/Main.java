package arranque;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;

import cliente.Cartao;
import cliente.Cupao;
import comercio.Inventario;
import comercio.ProdutoInfo;
import menu.JanelaCartao;
import menu.JanelaCompra;
import util.GeradorId;

public class Main {

    public static void main(String[] args) {
        // criar o inventário e restantes elementos necessário à aplicação
        Inventario inventario = new Inventario();
        criarProdutos(inventario);
        criarCupoes(inventario);
        criarCartoes(inventario);

        // criar e apresentar as janelas da aplicação
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JanelaCompra jc = new JanelaCompra("Loja HonESta de Castelo Branco", inventario);
                jc.setLocation(20, 20);
                jc.setVisible(true);

                JanelaCartao jcard = new JanelaCartao(inventario.getCartoes());

                jcard.setLocation(20 + jc.getWidth() + 20, 20);
                jcard.setVisible(true);
            }
        });
    }

    /**
     * Cria os produtos e adiciona-os ao inventário
     * 
     * @param inventario onde colocar os produtos
     */
    private static void criarProdutos(Inventario inventario) {
        inventario.adicionarProduto(new ProdutoInfo("123-001", "EST", "BarraMix maçã canela", 89));
        inventario.adicionarProduto(new ProdutoInfo("123-002", "EST", "CeriCrisp", 149));
        inventario.adicionarProduto(new ProdutoInfo("123-003", "EST", "Sumix Limonada", 149));
        inventario.adicionarProduto(new ProdutoInfo("123-004", "EST", "Chocolate com amêndoas", 149));
        inventario.adicionarProduto(new ProdutoInfo("123-005", "EST", "Arroz agulha", 109));
        inventario.adicionarProduto(new ProdutoInfo("123-006", "EST", "Arroz carolino", 119));
        inventario.adicionarProduto(new ProdutoInfo("123-007", "EST", "Arroz basmati ", 169));
        inventario.adicionarProduto(new ProdutoInfo("222-001", "AlbiCereal", "Arroz Agulha", 169));
        inventario.adicionarProduto(new ProdutoInfo("222-002", "AlbiCereal", "Arroz Carolino", 179));
        inventario.adicionarProduto(new ProdutoInfo("222-003", "AlbiCereal", "Céu Estrelado", 219));
        inventario.adicionarProduto(new ProdutoInfo("222-004", "AlbiCereal", "Aveia crunch", 249));
        inventario.adicionarProduto(new ProdutoInfo("222-005", "AlbiCereal", "Massa Esparguete", 99));
        inventario.adicionarProduto(new ProdutoInfo("222-006", "AlbiCereal", "Massa macarronete", 109));
        inventario.adicionarProduto(new ProdutoInfo("301-001", "DoceVida", "Chocolate de leite", 149));
        inventario.adicionarProduto(new ProdutoInfo("301-002", "DoceVida", "Chocolate com Avelã", 169));
        inventario.adicionarProduto(new ProdutoInfo("301-003", "DoceVida", "Chocolate negro 70%", 189));
        inventario.adicionarProduto(new ProdutoInfo("404-001", "Referescante", "Sumo de maçã", 139));
        inventario.adicionarProduto(new ProdutoInfo("404-002", "Referescante", "Sumo de laranja", 139));
        inventario.adicionarProduto(new ProdutoInfo("404-003", "Referescante", "Sumo de ananás ", 139));
        //Adicionar mais 2 produtos
    }

    /**
     * Cria e configura os cupões e adiciona-os ao inventário
     * 
     * @param inventario onde colocar os cupões
     */
    private static void criarCupoes(Inventario inventario) {
        inventario.adicionarCupao(new Cupao("1001", "em refrigerantes", 0.5f, 0, 7,
                Arrays.asList(
                        inventario.getProduto("123-003"),
                        inventario.getProduto("404-001"),
                        inventario.getProduto("404-002"),
                        inventario.getProduto("404-003")
                )));

        inventario.adicionarCupao(new Cupao("1002", "em chocolates", 0.25f, 0, 7,
                Arrays.asList(
                        inventario.getProduto("123-004"),
                        inventario.getProduto("301-001"),
                        inventario.getProduto("301-002"),
                        inventario.getProduto("301-003")
                )));

        inventario.adicionarCupao(new Cupao("1003", "em massa", 0.25f, 0, 7,
                Arrays.asList(
                        inventario.getProduto("222-005"),
                        inventario.getProduto("222-006")
                )));

        inventario.adicionarCupao(new Cupao("1004", "em arroz", 0.25f, 0, 7,
                Arrays.asList(
                        inventario.getProduto("123-005"),
                        inventario.getProduto("123-006"),
                        inventario.getProduto("123-007"),
                        inventario.getProduto("222-001"),
                        inventario.getProduto("222-002")
                )));

        inventario.adicionarCupao(new Cupao("1005", "na marca EST", 0.15f, 0, 7,
                Arrays.asList(
                        inventario.getProduto("123-001"),
                        inventario.getProduto("123-002"),
                        inventario.getProduto("123-003"),
                        inventario.getProduto("123-004"),
                        inventario.getProduto("123-005"),
                        inventario.getProduto("123-006"),
                        inventario.getProduto("123-007")
                )));

        inventario.adicionarCupao(new Cupao("1006", "em cereais de pequeno-almoço", 0.25f, 8, 15,
                Arrays.asList(
                        inventario.getProduto("123-002"),
                        inventario.getProduto("222-003"),
                        inventario.getProduto("222-004")
                )));
    }

    /**
     * Cria e configura os cartões e adiciona-os ao inventário
     * 
     * @param inventario onde colocoar os cartões
     */
    private static void criarCartoes(Inventario inventario) {
        inventario.adicionarCartao(new Cartao("10101", 0,
                Arrays.asList(
                        inventario.getCupao("1001"),
                        inventario.getCupao("1003"),
                        inventario.getCupao("1005"),
                        inventario.getCupao("1006")
                )));

        inventario.adicionarCartao(new Cartao("20202", 250,
                Arrays.asList(
                        inventario.getCupao("1001"),
                        inventario.getCupao("1002"),
                        inventario.getCupao("1004"),
                        inventario.getCupao("1006")
                )));

        inventario.adicionarCartao(new Cartao("30303", 58,
                Arrays.asList(
                        inventario.getCupao("1002"),
                        inventario.getCupao("1003"),
                        inventario.getCupao("1004"),
                        inventario.getCupao("1005"),
                        inventario.getCupao("1006")
                )));
    }
}
