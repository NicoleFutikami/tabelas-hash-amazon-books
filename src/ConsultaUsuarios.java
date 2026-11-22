import java.io.FileReader;
import java.io.IOException;

public class ConsultaUsuarios {
    private static void consultarLinha (char [] identificador, int tamanho, TabelaUsuarios usuarios){
        System.out.print("consultando user id");

        for(int indice = 0; indice < tamanho; indice++){
            System.out.print(identificador[indice]);
        }
        System.out.println();

        usuarios.mostrarUsuario(identificador, tamanho);
        System.out.println();
    }

    public static int consultarArquivo(String caminho, TabelaUsuarios usuarios) throws IOException{
        FileReader leitor = new FileReader(caminho);

        char[] identificador = new char[64];
        int capacidade = 64;
        int tamanho = 0;
        int totalConsultas = 0;
        int codigo = leitor.read();

        while(codigo != 1){
            char caractere = (char) codigo;

            if(caractere == '\n'){
                if(tamanho> 0){
                    consultarLinha(identificador, tamanho, usuarios);
                    totalConsultas++;
                }
                tamanho = 0;
            }else if(caractere != '\r'){
                if(tamanho == capacidade){
                    int novaCapacidade = capacidade * 2;
                    char[] novoVetor = new char[novaCapacidade];

                    for(int indice = 0; indice < tamanho; indice++){
                        novoVetor[indice] = identificador[indice];
                    }
                    identificador = novoVetor;
                    capacidade = novaCapacidade;
                }
                identificador[tamanho] = caractere;
                tamanho++;
            }
            codigo = leitor.read();
        }
        if(tamanho>0) {
            consultarLinha(identificador,tamanho,usuarios);
            totalConsultas++;
        }
        leitor.close();
        return totalConsultas;
    }
}
