import java.io.FileReader;
import java.io.IOException;

public class ConsultaUsuarios {

    private static void consultarLinha(char[] id, int tamanho, TabelaUsuarios usuarios) {
        System.out.print("Consultando User_id: ");
        for (int i = 0; i < tamanho; i++) {
            System.out.print(id[i]);
        }
        System.out.println();
        usuarios.mostrarUsuario(id, tamanho);
        System.out.println();
    }

    public static int consultarArquivo(String caminho, TabelaUsuarios usuarios) throws IOException {
        FileReader leitor = new FileReader(caminho);
        char[] id = new char[64];
        int capacidade = 64;
        int tamanho = 0;
        int consultas = 0;
        int codigo = leitor.read();

        while (codigo != -1) {
            if (codigo == '\n') {
                if (tamanho > 0) {
                    consultarLinha(id, tamanho, usuarios);
                    consultas++;
                }
                tamanho = 0;
            } else if (codigo != '\r') {
                if (tamanho == capacidade) {
                    capacidade = capacidade * 2;
                    char[] novo = new char[capacidade];
                    for (int i = 0; i < tamanho; i++) {
                        novo[i] = id[i];
                    }
                    id = novo;
                }
                id[tamanho] = (char) codigo;
                tamanho++;
            }
            codigo = leitor.read();
        }

        if (tamanho > 0) {
            consultarLinha(id, tamanho, usuarios);
            consultas++;
        }
        leitor.close();
        return consultas;
    }
}
