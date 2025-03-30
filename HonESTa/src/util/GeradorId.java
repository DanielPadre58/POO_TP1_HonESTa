package util;

import java.util.ArrayList;
import java.util.List;

public class GeradorId {
    private static final int tamanhoIdCartao = 20;
    //Em um programa mais complexo, esta lista seria armazenado exteriormente
    private static List<String> listaDeIdsDeCartoesExistentes = new ArrayList<>();

    private static final int tamanhoIdCupao = 10;
    //Em um programa mais complexo, esta lista seria armazenado exteriormente
    private static List<String> listaDeIdsDeCupoesExistentes = new ArrayList<>();

    private static final int tamanhoCodigoBarras = 9;
    //Em um programa mais complexo, esta lista seria armazenado exteriormente
    private static List<String> listaDeIdsDeCodigosExistentes = new ArrayList<>();


    public static String geradorIdCartao(){
        return getString(tamanhoIdCartao, listaDeIdsDeCartoesExistentes);
    }

    public static String geradorIdCupao(){
        return getString(tamanhoIdCupao, listaDeIdsDeCupoesExistentes);
    }

    public static String geradorCodigoBarras(){
        StringBuilder codigoBarras = new StringBuilder();
        for (int i = 1; i <= tamanhoCodigoBarras; i++) {
            //Na metade do codigo e colocado um "-", para simular um codigo de barras
            if(i == (tamanhoCodigoBarras / 2) + 1){
                codigoBarras.append("-");
                continue;
            }

            codigoBarras.append((int)(Math.random() * 10));

            if(i == tamanhoCodigoBarras + 1 && listaDeIdsDeCodigosExistentes.contains(codigoBarras.toString())){
                i = 1;
                codigoBarras = new StringBuilder();
            }
        }

        return codigoBarras.toString();
    }

    //Gerador de um id com caracteres Alfanumericos
    private static String getString(int tamanhoIdCartao, List<String> listaDeIds) {
        StringBuilder idBuilder = new StringBuilder();
        for(int i = 1; i <= tamanhoIdCartao; i++){
            //Escolhe que tipo de caracter sera (numero, letra maiuscula ou minuscula)
            gerarCaracter(idBuilder);

            //Verifica se o Id ja existe
            if(i == tamanhoIdCartao && listaDeIds.contains(idBuilder.toString())){
                i = 1;
                idBuilder = new StringBuilder();
            }
        }

        return idBuilder.toString();
    }

    private static void gerarCaracter(StringBuilder idBuilder) {
        int typeOfChar = (int)(Math.random() * 3) + 1;
        switch (typeOfChar){
            //Numero
            case 1 -> idBuilder.append((char)(Math.random() * 10 + 48));
            //Letra maiuscula
            case 2 -> idBuilder.append((char)(Math.random() * 26 + 65));
            //Letra minuscula
            case 3 -> idBuilder.append((char)(Math.random() * 26 + 97));
        }
    }
}
