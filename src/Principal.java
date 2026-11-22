import java.io.IOException;

public class Principal
import java.io.IOException;

public class Principal {

    public static void main(String[] argumentos)
            throws IOException {

        TabelaLivros livros = new TabelaLivros();
        TabelaUsuarios usuarios = new TabelaUsuarios();

        // Le a base completa
        LeitorCsv.lerArquivo(
                "/Users/mimi/Desktop/JAVARA3/Books_rating.csv",
                livros,
                usuarios
        );

        // JAVA-09: calcula os melhores livros
        usuarios.calcularMelhoresLivros();

        // JAVA-12: gera o ranking top 10
        RankingLivros ranking = new RankingLivros();

        ranking.gerarRanking(livros);
        ranking.mostrarRanking();

        // JAVA-10: gera o primeiro CSV
        int linhasGravadas = usuarios.gerarArquivoCsv(
                "usuario_melhor_livro.csv"
        );

        // JAVA-13: gera o CSV ordenado
        OrdenacaoUsuarios ordenacao = new OrdenacaoUsuarios();

        ordenacao.carregarUsuarios(usuarios);

        double inicioOrdenacao = System.nanoTime();

        ordenacao.ordenar();

        double fimOrdenacao = System.nanoTime();

        double tempoOrdenacao =
                (fimOrdenacao - inicioOrdenacao) / 1000000.0;

        int linhasOrdenadas = ordenacao.gerarArquivoOrdenado(
                "usuario_melhor_livro_ordenado.csv"
        );

        System.out.println("Usuarios no CSV ordenado: "
                + linhasOrdenadas);

        System.out.println("Tempo de ordenacao (ms): "
                + tempoOrdenacao);

        // JAVA-11: consulta os usuarios
        int totalConsultas = ConsultaUsuarios.consultarArquivo(
                "user_id.txt", usuarios
        );

        System.out.println("Consultas realizadas: "
                + totalConsultas);

        System.out.println("Usuarios gravados no CSV: "
                + linhasGravadas);

        System.out.println("Titulos diferentes: "
                + livros.obterQuantidadeLivros());

        System.out.println("Usuarios diferentes: "
                + usuarios.obterQuantidadeUsuarios());

        // JAVA-14: executa os seis experimentos hash
        ExperimentosHash.executar(livros, usuarios);

        System.out.println("Terminou o programa!");
    }
}


