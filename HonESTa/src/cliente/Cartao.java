package cliente;

import comercio.Venda;
import util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Classe que representa um cartão de fidelização na cadeia de lojas HonESta.
 */
public class Cartao {
    private final String id;
    private long saldo;
    private HashMap<Cupao, Boolean> cupoes;
    private boolean estaAtivo;

    public String getId() {
        return id;
    }

    public long getSaldo() {
        return saldo;
    }

    public Cartao(String id, long saldo, List<Cupao> cupoes) {
        this.id = Validator.requireNonBlank(id);

        this.saldo = Validator.requirePositiveOrZero(saldo);

        this.cupoes = new HashMap<>();
        for (Cupao cupao : Objects.requireNonNull(cupoes)) {
            this.cupoes.put(cupao, false);
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

        estaAtivo = true;
    }

    public void desativar(){
        HashMap<Cupao, Boolean> cupoesPlaceHolder = new HashMap<>(cupoes);
        cupoesPlaceHolder.forEach((Cupao cupao, Boolean valido) ->{
            cupoes.replace(cupao, false);
        });

        estaAtivo = false;
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
        if(!estaAtivo){
            throw new IllegalStateException("Nao pode usar um cartão que nao foi ativo");
        }

        HashMap<Cupao, Boolean> cupoesPlaceHolder = new HashMap<>(cupoes);
        cupoesPlaceHolder.forEach((Cupao cupao, Boolean valido) ->{
            if(cupoes.get(cupao) && !v.foiUsado(cupao)) {
                if(cupao.aplicar(this, v)){
                    v.adicionarCupaoUsado(cupao);
                }
            }

            for(Cupao cupaoRemover : v.getCupoesUsados()){
                cupoes.remove(cupaoRemover);
            }
        });

        estaAtivo = false;
    }

    /**
     * Retorna uma lista com os cupões disponíveis. Os cupões disponíveis são
     * aqueles que estão válidos no dia atual.
     * 
     * @return uma lista com os cupões disponíveis
     */
    public List<Cupao> getCupoesDisponiveis() {
        List<Cupao> cupoesDisponiveis = new ArrayList<>();
        cupoes.forEach((Cupao cupao, Boolean ativo) ->{
            if(cupao.estaValido() && !cupao.eFuturo()){
                cupoesDisponiveis.add(cupao);
            }
        });

        return cupoesDisponiveis;
    }

    /**
     * Retorna uma lista com os cupões que estarão disponíveis no futuro, isto é,
     * cujo início é após o dia de hoje.
     * 
     * @return uma lista com os cupões que estarão disponíveis no futuro
     */
    public List<Cupao> getCupoesFuturos() {
        List<Cupao> cupoesFuturos = new ArrayList<>();
        cupoes.forEach((Cupao cupao, Boolean ativo) ->{
            if(cupao.eFuturo()){
                cupoesFuturos.add(cupao);
            }
        });

        return cupoesFuturos;
    }

    /**
     * Atualiza os cupões, removendo os que já passaram de validade
     */
    public void atualizarCupoes() {
        HashMap<Cupao, Boolean> cupoesPlaceHolder = new HashMap<>(cupoes);
        cupoesPlaceHolder.forEach((Cupao cupao, Boolean ativo) ->{
            if(!cupao.estaValido()){
                cupoes.remove(cupao);
            }
        });
    }

    /**
     * Usar o saldo do cartão. Deve garantir que o gasto não é maior que o saldo.
     * 
     * @param gasto o que retirar do saldo.
     */
    public void reduzirSaldo(long gasto) {
        saldo = saldo - gasto < 0 ? 0 : saldo - gasto;
    }

    /**
     * Acumular saldo no cartão
     * 
     * @param valor valor a acumular no saldo
     */
    public void acumularSaldo(long valor) {
        saldo += valor;
    }

    /**
     * indica se um cartão está ativo. isto é, pronto a ser usado numa compra.
     * 
     * @return true se está ativo.
     */
    public boolean estaAtivo() {
        return estaAtivo;
    }
}
