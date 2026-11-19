import java.io.BufferedReader; //buffer pra ler melhor sem travar
import java.io.FileInputStream; //converte bit pra utf
import java.io.FileInputStream; //abre arquivo csv
import java.io.IOException; //avisa erros

public class LeitorCsv {

    private static char[][] campos={ //campos eh o nome da variavel
        new char[64], //titulo do livro
        new char[64], //pessoa
        new char[64], //perfil
        new char[64]    //nota
    };
    private static int[] tamanhos = new int[4];//vetor com os 4 campos ali de cima
    private static int[] capacidades = {64, 64, 64, 64};// guarda num de caract que cabe


}
