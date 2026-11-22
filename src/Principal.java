import java.io.IOException;

public class Principal {

    public static void main(String[] args)
            throws IOException {

        TabelaLivros livros = new TabelaLivros();
        TabelaUsuarios usuarios = new TabelaUsuarios();

        LeitorCsv.lerArquivo(
                "/Users/mimi/Desktop/JAVARA3/Books_rating.csv",
                livros, usuarios
        );
        usuarios.calcularMelhoresLivros();

        RankingLivros ranking = new RankingLivros();

        ranking.gerarRanking(livros);
        ranking.mostrarRanking();

        int linhasGravadas = usuarios.gerarArquivoCsv("usuario_melhor_livro.csv");

        int totalConsultas = ConsultaUsuarios.consultarArquivo("user_id.txt", usuarios);

        System.out.println("consultas realizadas: "+ totalConsultas);

        System.out.println("usuarios gravados no csv" + linhasGravadas);

        System.out.println("titulos diferentes: "
                + livros.obterQuantidadeLivros());

        System.out.println("usuarios dif" + usuarios.obterQuantidadeUsuarios());

        System.out.println("terminou o programa");
    }
}

