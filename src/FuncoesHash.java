public class FuncoesHash {

    public static int hashFnv1a(char[] chave, int tamanho){
        int h = 0x811C9DC5;

        for (int indice = 0; indice< tamanho; indice++){
            h = h ^ chave[indice];
            h = h * 16777619;
        }
        return h;
    }

    public static int hashPolinomial31(char [] chave, int tamanho){
        int h = 0;

        for (int indice = 0; indice < tamanho; indice ++){
            h = 31 * h + chave[indice];
        }
        return h;
    }

}
