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

        System.out.println("itulos diferentes: "
                + livros.obterQuantidadeLivros());

        System.out.println("usuarios dif" + usuarios.obterQuantidadeUsuarios());

        System.out.println("terminou o programa");
    }
}

