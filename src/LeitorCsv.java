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

        if (tamanhos [destino] == capacidades[destino]){ //ve se ta dentro do numero de carac
            int novaCapacidade = capacidades[destino] * 2; // dpbra capacidade se tiver maior
            char [] novoVetor = new char[novaCapacidade];

            for( int indice =0; indice < tamanhos [destino]; indice++){ //percorre pra atualizar
                novoVetor[indice] = campos [destino][indice];
            }
            campos [destino] = novoVetor;
            capacidades[destino] = novaCapacidade; //atualiza em si
        }
        campos[destino][tamanhos[destino]] = caractere; //guarda caractere na proxima posicao livre do vetor
        tamanhos[destino]++; //aumenta 1 pro vetor nao comer caractere
    }
    private static void mostrarCampo(int indiceCampo){ //indica qual campo vamos mostrar
        for(int indice = 0; indice < tamanhos[indiceCampo]; indice++){ //percorre os armazenados
            System.out.print(campos[indiceCampo][indice]); //imprime o campo e o indice, posicao
        }
    }
    private static void terminarRegistro(int numeroColunas){ //final do registro csv
        if (cabecalhoPendente == 1){
            cabecalhoPendente = 0; // nn precisa ignorar novamente
        }else{
            totalAvaliacoes++; //terminou de ler cabecalho e vamos fazer os ifs

            if (numeroColunas != 10){ //se colunas incorretas forem diferentes de 10, aumenta a contagem de colunas incorretas
                colunasIncorretas++;
            }
            if (totalAvaliacoes <= 2){ //TESTANDO
                System.out.print("avaliacao ");
                System.out.println(totalAvaliacoes);

                System.out.print("titulo");
                mostrarCampo(0);

                System.out.print("usuario");
                mostrarCampo(1);

                System.out.println("perfil");
                mostrarCampo(2);

                System.out.print("nota");
                mostrarCampo(3);
            }
        }
        for (int indice =0; indice<4; indice++){
            tamanhos[indice] =0; //esvazia pra proximo registro
        }
    }
    int coluna = 0;
    int estado = 0; //evita erro com virgula
    int inicioCampo = 1;
    int registroIniciado = 0;
    int pularQuebraLinha = 0;

    


}
