import java.io.IOException;

public class Principal {

    public static void main(String[] argumentos)
            throws IOException {

        LeitorCsv.lerArquivo(
                "/Users/mimi/Desktop/JAVARA3/Books_rating.csv"
        );
        System.out.println("terminou o programa"); //ver se ta indp

    }
}
