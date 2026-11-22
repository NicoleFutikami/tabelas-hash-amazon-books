import java.io.FileReader;
import java.io.IOException;

public class LeitorCsv {
    private static char[][] campos = {
            new char[64], new char[64], new char[64], new char[64]
    };
    private static int[] tamanhos = {0, 0, 0, 0};
    private static int[] capacidades = {64, 64, 64, 64};

    private static int totalAvaliacoes;
    private static int avaliacoesValidas;
    private static int semUsuario;
    private static int semTitulo;
    private static int notaInvalida;
    private static int colunasIncorretas;
    private static int cabecalhoPendente;

    private static int campoNecessario(int coluna) {
        if (coluna == 1) return 0;
        if (coluna == 3) return 1;
        if (coluna == 4) return 2;
        if (coluna == 6) return 3;
        return -1;
    }

    private static void guardarCaractere(int coluna, char caractere) {
        int destino = campoNecessario(coluna);
        if (destino == -1) return;

        if (tamanhos[destino] == capacidades[destino]) {
            capacidades[destino] = capacidades[destino] * 2;
            char[] novo = new char[capacidades[destino]];
            for (int i = 0; i < tamanhos[destino]; i++) {
                novo[i] = campos[destino][i];
            }
            campos[destino] = novo;
        }
        campos[destino][tamanhos[destino]] = caractere;
        tamanhos[destino]++;
    }

    private static int campoVazio(int indice) {
        for (int i = 0; i < tamanhos[indice]; i++) {
            char c = campos[indice][i];
            if (c != ' ' && c != '\t' && c != '\n' && c != '\r') {
                return 0;
            }
        }
        return 1;
    }

    private static double converterNota() {
        if (tamanhos[3] == 0) return -1;

        double nota = 0;
        double decimal = 0.1;
        int ponto = 0;

        for (int i = 0; i < tamanhos[3]; i++) {
            char c = campos[3][i];
            if (c == '.') {
                if (ponto == 1 || i == 0 || i == tamanhos[3] - 1) return -1;
                ponto = 1;
            } else if (c >= '0' && c <= '9') {
                int digito = c - '0';
                if (ponto == 0) {
                    nota = nota * 10 + digito;
                } else {
                    nota = nota + digito * decimal;
                    decimal = decimal / 10;
                }
                if (nota > 5) return -1;
            } else {
                return -1;
            }
        }
        if (nota < 1 || nota > 5) return -1;
        return nota;
    }

    private static void terminarRegistro(int colunas, TabelaLivros livros,TabelaUsuarios usuarios) {
        if (cabecalhoPendente == 1) {
            cabecalhoPendente = 0;
        } else {
            totalAvaliacoes++;
            if (colunas != 10) {
                colunasIncorretas++;
            } else if (campoVazio(1) == 1) {
                semUsuario++;
            } else if (campoVazio(0) == 1) {
                semTitulo++;
            } else {
                double nota = converterNota();
                if (nota < 1) {
                    notaInvalida++;
                } else {
                    avaliacoesValidas++;
                    livros.adicionarAvaliacao(campos[0], tamanhos[0], nota);
                    usuarios.adicionarUsuario(campos[1], tamanhos[1],
                            campos[2], tamanhos[2]);
                    NoLista livro = livros.encontrarLivro(campos[0], tamanhos[0]);
                    usuarios.registrarLivroAvaliado(campos[1], tamanhos[1], livro, nota);
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            tamanhos[i] = 0;
        }
    }

    public static void lerArquivo(String caminho, TabelaLivros livros,
                                  TabelaUsuarios usuarios) throws IOException {
        totalAvaliacoes = 0;
        avaliacoesValidas = 0;
        semUsuario = 0;
        semTitulo = 0;
        notaInvalida = 0;
        colunasIncorretas = 0;
        cabecalhoPendente = 1;
        for (int i = 0; i < 4; i++) tamanhos[i] = 0;

        int coluna = 0;
        int dentroAspas = 0;
        int aspasFechadas = 0;
        int inicioCampo = 1;
        int registroIniciado = 0;

        FileReader leitor = new FileReader(caminho);
        int codigo = leitor.read();

        while (codigo != -1) {
            char c = (char) codigo;

            if (dentroAspas == 1) {
                if (c == '"') {
                    dentroAspas = 0;
                    aspasFechadas = 1;
                } else {
                    guardarCaractere(coluna, c);
                }
            } else if (aspasFechadas == 1 && c == '"') {
                guardarCaractere(coluna, '"');
                dentroAspas = 1;
                aspasFechadas = 0;
            } else if (c == ',') {
                coluna++;
                inicioCampo = 1;
                aspasFechadas = 0;
                registroIniciado = 1;
            } else if (c == '\n' || c == '\r') {
                if (registroIniciado == 1) {
                    terminarRegistro(coluna + 1, livros, usuarios);
                }
                coluna = 0;
                inicioCampo = 1;
                aspasFechadas = 0;
                registroIniciado = 0;
            } else if (c == '"' && inicioCampo == 1) {
                dentroAspas = 1;
                inicioCampo = 0;
                registroIniciado = 1;
            } else {
                guardarCaractere(coluna, c);
                inicioCampo = 0;
                aspasFechadas = 0;
                registroIniciado = 1;
            }
            codigo = leitor.read();
        }
        leitor.close();
        if (registroIniciado == 1) {
            terminarRegistro(coluna + 1, livros, usuarios);
        }

        System.out.println("Avaliacoes lidas: " + totalAvaliacoes);
        System.out.println("Registros com colunas incorretas: " + colunasIncorretas);
        System.out.println("Avaliacoes validas: " + avaliacoesValidas);
        System.out.println("Sem usuario: " + semUsuario);
        System.out.println("Sem titulo: " + semTitulo);
        System.out.println("Notas invalidas: " + notaInvalida);
        System.out.println("Avaliacoes descartadas: " + (totalAvaliacoes - avaliacoesValidas));
    }
}