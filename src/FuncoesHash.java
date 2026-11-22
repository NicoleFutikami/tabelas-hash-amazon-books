public class FuncoesHash {
    public static int hashFnv1a(char[] chave, int tamanho){
        int h = 0x811C9DC5;
        for (int i = 0; i< tamanho; i++){
            h = h ^ chave[i];
            h = h * 16777619;
        }
        return h;
    }
    public static int hashPolinomial31(char [] chave, int tamanho){
        int h = 0;
        for (int i = 0; i < tamanho; i ++){
            h = 31 * h + chave[i];
        }
        return h;
    }
    public static int hashDjb2(char[] chave, int tamanho){
        int h = 5381;

        for (int i = 0; i< tamanho; i ++){
            h = 33 * h + chave [i];
        }
        return h;
    }
    public static int calcularPosicao(int valorHash){
        return(valorHash & 0x7FFFFFFF) % 1000003;
    }


}
