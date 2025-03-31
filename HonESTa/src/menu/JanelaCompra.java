package menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.util.Collection;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cliente.Cartao;
import cliente.ProdutoVendido;
import cliente.Venda;
import comercio.Inventario;
import comercio.ProdutoInfo;

/**
 * Janela que simula uma caixa das lojas da cadeia HonESTa. No lado direito,
 * para simular o leitor do código de barras tem uma lista com os produtos. No
 * lado esquerdo aparece a lista dos produtos vendidos e o total da compra.
 */
public class JanelaCompra extends JFrame {
    // constantes para vários aspetos gráficos da janela
    private static final int ALTURA_JANELA = 600;
    private static final int LARGURA_JANELA = RendererListaInventario.DIM_BASE.width * 2
            + RendererListaVenda.DIM_BASE.width + 50;
    private static final Font ftMuitoGrande = new Font("Arial", Font.BOLD, 22);
    private static final Font ftGrande = new Font("Arial", Font.BOLD, 12);
    private static final Font ftMedia = ftGrande.deriveFont(Font.PLAIN, 12);
    private static final Font ftLista = new Font("Monospaced", Font.BOLD, 12);

    // elementos gráficos da janela
    private DefaultListModel<ProdutoInfo> vendaModel;
    private JLabel totalLbl;

    // a venda atual, isto é, a venda que está a ser feita neste momento. Assim que
    // se terminar uma venda, outra é começada de imediato.
    private Venda vendaAtual = new Venda();

    /**
     * Cria a janela de simulação de uma caixa.
     * 
     * @param title título da janela
     * @param inv   o inventário da loja
     */
    public JanelaCompra(String title, Inventario inv) throws HeadlessException {
        super(title);
        setupJanela(inv);
    }

    /**
     * Método chamado pela janela quando se pressiona num produto: simula a passagem
     * do produto pelo leitor de códigos de barras
     * 
     * @param p o produto identificado pelo leitor de códigos de barras
     */
    private void adicionarProdutoVenda(ProdutoInfo p) {
        vendaAtual.adicionarProduto(new ProdutoVendido(p));

        atualizarPrecoTotal(vendaAtual.getTotalCompra());
    }

    /**
     * Método chamado quando se termina a venda e é preciso pagar usando um cartão
     * 
     * @param c o cartão a usar na compra
     */
    private void pagarComCartao(Cartao c) {
        if (!c.estaAtivo()) {
            JOptionPane.showMessageDialog(this, "Por favor, ative cartão na aplicação!");
            return;
        }

        long saldoCartao = c.getSaldo();
        int numeroCupoesUsados = c.getCupoesUsados().size();
        long totalVenda = vendaAtual.getTotalCompra();

        if (saldoCartao > 0) {
            int opcao = JOptionPane.showConfirmDialog(this,
                    "Deseja usar o saldo de " + precoToString(saldoCartao) + "?", "Usar saldo",
                    JOptionPane.YES_NO_OPTION);
            if (opcao == JOptionPane.YES_OPTION)
                c.reduzirSaldo(totalVenda);  // VERIFICAR ISTO
        }

        c.usar(vendaAtual);

        // apresentar a mensagem de agradecimento
        String mensagem = "<html>Obrigado pela sua compra de " + precoToString(totalVenda);
        if (numeroCupoesUsados >= 2)
            mensagem += "<br>Usou " + numeroCupoesUsados + " cupões.";
        else if (numeroCupoesUsados == 1)
            mensagem += "<br>Usou " + numeroCupoesUsados + " cupão.";
        JOptionPane.showMessageDialog(this, mensagem);

        vendaAtual = new Venda();

        // atualizar a lista das vendas
        atualizarPrecoTotal(000);
        vendaModel.clear();
    }

    /**
     * Método chamado para saber qual o preço de um produto para o apresentar na
     * lista de venda
     * 
     * @param p o produto cuja informação é precisa
     * @return o preço do produto
     */
    private long getPrecoProduto(ProdutoInfo p) {
        return p.getPrecoAtual();
    }

    /**
     * Método chamado para saber qual a marca de um produto
     * 
     * @param p o produto cuja informação é precisa
     * @return uma string que indica a marca do produto
     */
    private String getMarcaProduto(ProdutoInfo p) {
        return p.getMarca();
    }

    /**
     * Método chamado para saber qual o modelo de um produto
     * 
     * @param p o produto cuja informação é precisa
     * @return uma string que indica o modelo do produto
     */
    private String getModeloProduto(ProdutoInfo p) {
        return p.getModelo();
    }

    /**
     * responsável por desenhar a informação de um cartão na lista de cartões
     */
    private final class RendererCartoes extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                boolean cellHasFocus) {
            Cartao c = (Cartao) value; // o cartão a ser desenhado

            String numeroCartao = c.getId();

            return super.getListCellRendererComponent(list, numeroCartao, index, isSelected, cellHasFocus);
        }
    }

    /**
     * Prepara a janela do inventário
     * 
     * @param inv o inventário a apresentar
     */
    private void setupJanela(Inventario inv) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(LARGURA_JANELA, ALTURA_JANELA);
        setResizable(false);
        JPanel inventario = setupInventario(inv.getStockProdutos());
        JPanel compra = setupCompra(inv.getCartoes());
        getContentPane().add(inventario, BorderLayout.CENTER);
        getContentPane().add(compra, BorderLayout.WEST);
    }

    /**
     * Escreve o novo total na janela
     * 
     * @param total o total a ser apresentado
     */
    private void atualizarPrecoTotal(long total) {
        totalLbl.setText(precoToString(total));
    }

    /**
     * converte um preço para uma string já formatada em que o preço tem sempre 2
     * casas decimais e o simbolo € no final
     * 
     * @param total o preço a formatar
     * @return uma string com o preço formatado
     */
    private String precoToString(long total) {
        return String.format("%.2f€", total / 100f);
    }

    /**
     * Prepara a janela dos produtos, ou sej,a o simuilador do leitor dos códigos de
     * barras
     * 
     * @param prods os produtos a apresentar
     * @return o painel com os controlos dos produtos
     */
    private JPanel setupInventario(Collection<ProdutoInfo> prods) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(RendererListaInventario.DIM_BASE.width * 2 + 10, ALTURA_JANELA));
        DefaultListModel<ProdutoInfo> produtosModel = new DefaultListModel<>();
        produtosModel.addAll(prods);
        JList<ProdutoInfo> produtos = new JList<>(produtosModel);
        produtos.setMaximumSize(new Dimension(RendererListaInventario.DIM_BASE.width * 2, ALTURA_JANELA + 30));
        produtos.setLayoutOrientation(JList.VERTICAL_WRAP);
        produtos.setVisibleRowCount(-1);
        produtos.setCellRenderer(new RendererListaInventario());
        produtos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        produtos.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() || produtos.getSelectedValue() == null)
                    return;
                ProdutoInfo prod = produtos.getSelectedValue();
                adicionarProdutoVenda(prod);
                vendaModel.addElement(prod);
                produtos.clearSelection();
            }
        });
        panel.add(new JScrollPane(produtos));
        return panel;
    }

    /**
     * Prepara a zona da compra
     * 
     * @param cartoes os cartões que poderão ser usados numa compra
     * @return o painel já preparado
     */
    private JPanel setupCompra(Collection<Cartao> cartoes) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(RendererListaVenda.DIM_BASE.width + 20, ALTURA_JANELA));
        totalLbl = new JLabel("0.00€");
        totalLbl.setFont(ftMuitoGrande);
        totalLbl.setHorizontalAlignment(JLabel.RIGHT);
        panel.add(totalLbl, BorderLayout.NORTH);

        JPanel vendaPnl = new JPanel(new BorderLayout());
        vendaPnl.setBorder(new TitledBorder("Produtos comprados"));
        vendaModel = new DefaultListModel<>();
        JList<ProdutoInfo> vendaList = new JList<>(vendaModel);
        vendaList.setEnabled(false);
        vendaList.setCellRenderer(new RendererListaVenda());
        vendaPnl.add(new JScrollPane(vendaList));
        panel.add(vendaPnl, BorderLayout.CENTER);

        JPanel cardPnl = new JPanel(new GridLayout(0, 1));
        JComboBox<Cartao> cartoesList = new JComboBox<>(new Vector<>(cartoes));
        cartoesList.setRenderer(new RendererCartoes());
        cardPnl.add(cartoesList);
        JButton pagaBt = new JButton("Pagar");
        pagaBt.addActionListener(l -> {
            pagarComCartao((Cartao) cartoesList.getSelectedItem());

        });
        cardPnl.add(pagaBt);
        panel.add(cardPnl, BorderLayout.SOUTH);
        return panel;
    }

    /**
     * Responsável por apresentar a informação de um produto na lista de produtos
     */
    private final class RendererListaInventario extends DefaultListCellRenderer {

        private static final Dimension DIM_BASE = new Dimension(150, 40);
        private ProdutoInfo produto;

        @Override
        public Dimension getPreferredSize() {
            return DIM_BASE;
        }

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                boolean cellHasFocus) {
            produto = (ProdutoInfo) value;
            return super.getListCellRendererComponent(list, "", index, isSelected, cellHasFocus);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.black);
            g.drawRoundRect(2, 2, DIM_BASE.width - 4, DIM_BASE.height - 4, 8, 8);
            g.setFont(ftGrande);
            g.drawString(getMarcaProduto(produto), 5, 15);
            g.setFont(ftMedia);
            g.drawString(getModeloProduto(produto), 5, 35);
            g.drawString(precoToString(getPrecoProduto(produto)), 90, 15);
        }
    }

    /**
     * Responsável por apresentar a informação dos produtos na lista de produtos
     * vendidos
     */
    private final class RendererListaVenda extends DefaultListCellRenderer {
        private static final int MAXIMO_LINHA = 25;
        private static final Dimension DIM_BASE = new Dimension(260, 20);
        private ProdutoInfo produto;

        /**
         * Método chamado para saber qual a marca e modelo de um produto
         * 
         * @param p o produto cuja informação é precisa
         * @return uma string composta mela marca e modelo do produto
         */
        private String getMarcaModeloProduto(ProdutoInfo p) {
            return getMarcaProduto(p) + " " + getModeloProduto(p);
        }

        @Override
        public Dimension getPreferredSize() {
            return DIM_BASE;
        }

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                boolean cellHasFocus) {
            produto = (ProdutoInfo) value;
            return super.getListCellRendererComponent(list, "", index, isSelected, cellHasFocus);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setFont(ftLista);
            String descricao = getMarcaModeloProduto(produto);
            if (descricao.length() > MAXIMO_LINHA)
                descricao = descricao.substring(0, MAXIMO_LINHA - 3) + "...";
            String linha = String.format("%-" + MAXIMO_LINHA + "s %6.2f", descricao, getPrecoProduto(produto) / 100f);
            g.drawString(linha, 5, 15);
        }

    }
}
