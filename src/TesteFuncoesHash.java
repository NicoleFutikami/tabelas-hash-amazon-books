public class TesteFuncoesHash {

    public static void main(String[] args){
        char[] chave = {'A', 'n', 'a'};
        int tamanho = 3;

        int fnv1a = FuncoesHash.hashFnv1a(chave, tamanho);
        int polinomial = FuncoesHash.hashPolinomial31(chave, tamanho);
        int djb2 = FuncoesHash.hashDjb2(chave, tamanho);

        System.out.println("chave  eh ana");
        System.out.println(fnv1a);
        System.out.println(polinomial);
        System.out.println(djb2);

        System.out.println(FuncoesHash.calcularPosicao(fnv1a));
        System.out.println(FuncoesHash.calcularPosicao(polinomial));
        System.out.println(FuncoesHash.calcularPosicao(djb2));
    }
}
