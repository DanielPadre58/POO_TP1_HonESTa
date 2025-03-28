package cliente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe que representa um cartão de fidelização na cadeia de lojas HonESta.
 */
public class Cartao {
    private final String id;
    private long saldo;
    private HashMap<Cupao, Boolean> cupoes;
    private boolean estaAtivo;

    public Cartao(String id, long saldo, List<Cupao> cupoes) {
        this.id = id;

        verificarSaldo(saldo);
        this.saldo = saldo;

        verificarCupoes(cupoes);
        for (Cupao cupao : cupoes) {
            this.cupoes.put(cupao, false);
        }
    }

    public Cartao(String id) {
        this.id = id;
        this.saldo = 0;
        this.cupoes = new HashMap<>();
    }

    public Cartao(String id, long saldo) {
        this.id = id;

        verificarSaldo(saldo);
        this.saldo = saldo;

        this.cupoes = new HashMap<>();
    }

    private void verificarCupoes(List<Cupao> cupoes) {
        if(cupoes == null){
            throw new IllegalArgumentException("A lista de cupoes nao deve ser nula");
        }
    }


    private void verificarSaldo(long saldo) {
        if(saldo < 0){
            throw new IllegalArgumentException("Saldo deve ser maior que zero");
        }
    }

    /**
     * Ativa os cupões selecionados. Não aceita cupões que não estejam na lista de
     * cupões do cliente
     *
     * @param ativos lista de cupões para ativar
     */
    public void ativar(List<Cupao> ativos) {
        for (Cupao cupao : ativos) {
            cupoes.replace(cupao, true);
        }
    }

    /**
     * Usar o cartão numa venda, se este estiver ativo. Se tiver cupões ativos estes
     * devem ser aplicados também. Os cupões usados na venda serão removidos. Os
     * cupões ativos mas não usados na venda, deixam de estar ativos, mas permanecem
     * associados ao cartão. No final do uso o cartão é automaticamente desativado.
     * 
     * @param v a venda onde usar o cartão
     * @throws IllegalStateException se o cartão não estiver ativo
     */
    public void usar(Venda v) {
        // TODO implementar este método
        if(!estaAtivo){
            throw new IllegalStateException("Nao pode usar um carto que nao foi ativo");
        }

        for (Cupao cupao : cupoes.keySet()) {
            if(cupoes.get(cupao) && v.foiUsado(cupao)) {
                cupao.aplicar(this, v);
                cupoes.remove(cupao);
            }
        }
    }

    /**
     * Retorna uma lista com os cupões disponíveis. Os cupões disponíveis são
     * aqueles que estão válidos no dia atual.
     * 
     * @return uma lista com os cupões disponíveis
     */
    public List<Cupao> getCupoesDisponiveis() {
        // TODO implementar este método
        List<Cupao> cupoesDisponiveis = new ArrayList<>();
        for(Cupao cupao : cupoes.keySet()) {
            if(cupao.estaValido(LocalDate.now())){
                cupoesDisponiveis.add(cupao);
            }
        }

        return cupoesDisponiveis;
    }

    /**
     * Retorna uma lista com os cupões que estarão disponíveis no futuro, isto é,
     * cujo início é após o dia de hoje.
     * 
     * @return uma lista com os cupões que estarão disponíveis no futuro
     */
    public List<Cupao> getCupoesFuturos() {
        // TODO implementar este método
        return null;
    }

    /**
     * Atualiza os cupões, removendo os que já passaram de validade
     */
    public void atualizarCupoes() {
        // TODO implementar este método
        for(Cupao cupao : cupoes.keySet()) {
            if(!cupao.estaValido()){
                cupoes.remove(cupao);
            }
        }
    }

    /**
     * Usar o saldo do cartão. Deve garantir que o gasto não é maior que o saldo.
     * 
     * @param gasto o que retirar do saldo.
     */
    public void reduzirSaldo(long gasto) {
        // TODO implementar este método
        if(gasto > saldo){
            saldo = 0;
            return;
        }

        saldo -= gasto;
    }

    /**
     * Acumular saldo no cartão
     * 
     * @param valor valor a acumular no saldo
     */
    public void acumularSaldo(long valor) {
        // TODO implementar este método
        saldo += valor;
    }

    /**
     * indica se um cartão está ativo. isto é, pronto a ser usado numa compra.
     * 
     * @return true se está ativo.
     */
    public boolean estaAtivo() {
        // TODO implementar este método
        return estaAtivo;
    }
}
