import java.io.IOException;
public class Principal {

    public static void main(String[] argumentos)
            throws IOException {

        TabelaLivros livros = new TabelaLivros();
        TabelaUsuarios usuarios = new TabelaUsuarios();

        
        LeitorCsv.lerArquivo(
                "/Users/mimi/Desktop/JAVARA3/Books_rating.csv",
                livros,
                usuarios
        );


        usuarios.calcularMelhoresLivros();


        RankingLivros ranking = new RankingLivros();

        ranking.gerarRanking(livros);
        ranking.mostrarRanking();
        int linhasGravadas = usuarios.gerarArquivoCsv(
                "usuario_melhor_livro.csv"
        );

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

        ExperimentosHash.executar(livros, usuarios);

        System.out.println("Terminou o programa!");
    }
}


