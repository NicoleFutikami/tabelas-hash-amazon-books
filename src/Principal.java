import java.io.IOException;

public class Principal {

    public static void main(String[] args)
            throws IOException {

        TabelaLivros livros = new TabelaLivros();

        LeitorCsv.lerArquivo(
                "/Users/mimi/Desktop/JAVARA3/Books_rating.csv",
                livros
        );

        System.out.println("itulos diferentes: "
                + livros.obterQuantidadeLivros());

        System.out.println("terminou o programa");
    }
}

