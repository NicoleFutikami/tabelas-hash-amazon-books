
import java.io.IOException;

public class Principal {

    public static void main(String[] argumentos) throws IOException {
        TabelaLivros livros = new TabelaLivros();
        TabelaUsuarios usuarios = new TabelaUsuarios();
        double inicio;


        inicio = System.nanoTime();
        LeitorCsv.lerArquivo("Books_rating.csv", livros, usuarios);
        System.out.println("Leitura e indexacao (ms): " +
                (System.nanoTime() - inicio) / 1000000.0);


        inicio = System.nanoTime();
        usuarios.calcularMelhoresLivros();
        System.out.println("Calculo dos melhores livros (ms): " +
                (System.nanoTime() - inicio) / 1000000.0);


        RankingLivros ranking = new RankingLivros();
        inicio = System.nanoTime();
        ranking.gerarRanking(livros);
        System.out.println("Ranking (ms): " +
                (System.nanoTime() - inicio) / 1000000.0);
        ranking.mostrarRanking();


        inicio = System.nanoTime();
        int linhas = usuarios.gerarArquivoCsv("usuario_melhor_livro.csv");
        System.out.println("Gravacao do CSV (ms): " +
                (System.nanoTime() - inicio) / 1000000.0);


        OrdenacaoUsuarios ordenacao = new OrdenacaoUsuarios();
        inicio = System.nanoTime();
        ordenacao.carregarUsuarios(usuarios);
        System.out.println("Preparacao da lista (ms): " +
                (System.nanoTime() - inicio) / 1000000.0);

        inicio = System.nanoTime();
        ordenacao.ordenar();
        System.out.println("Ordenacao (ms): " +
                (System.nanoTime() - inicio) / 1000000.0);


        inicio = System.nanoTime();
        int linhasOrdenadas = ordenacao.gerarArquivoOrdenado(
                "usuario_melhor_livro_ordenado.csv");
        System.out.println("Gravacao do CSV ordenado (ms): " +
                (System.nanoTime() - inicio) / 1000000.0);


        inicio = System.nanoTime();
        int consultas = ConsultaUsuarios.consultarArquivo("user_id.txt", usuarios);
        System.out.println("Consultas (ms): " +
                (System.nanoTime() - inicio) / 1000000.0);

        System.out.println("\n=== RESUMO ===");
        System.out.println("Livros diferentes: " + livros.obterQuantidadeLivros());
        System.out.println("Usuarios diferentes: " + usuarios.obterQuantidadeUsuarios());
        System.out.println("Linhas no CSV: " + linhas);
        System.out.println("Linhas no CSV ordenado: " + linhasOrdenadas);
        System.out.println("Consultas realizadas: " + consultas);


        ExperimentosHash.executar(livros, usuarios);
        System.out.println("\nPrograma finalizado!");
    }
}

