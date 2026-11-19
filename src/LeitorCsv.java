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

    private static int totalAvaliacoes = 0; //conta num de avaliacoes ue
    private static int colunasIncorretas = 0; // registross com qntd diferentes q 10 colunas
    private static int cabecalhoPendente =1; //primeira linha co csv eh ignorada

    private static int campoNecessario(int coluna){ //tem 10 colunas mas andrey pediu so 4
        if(coluna == 1) return 0; //titulo
        if(coluna == 3) return 1; //usuario
        if(coluna == 4) return 2; //perfil
        if(coluna == 6) return 3; //nota
        return -1; //coluna NAO EH uma das 4 (eh uma das outras 6)
    }
    private static void guardarCaractere(int coluna, char caractere){ //guarda carac lido no csv
        int destino = campoNecessario(coluna);
        if(destino == -1) return; //se a coluna nao for uma das quatro, ignora

        if (tamanhos [destino] == capacidades[destino]){
            
        }
    }
}
