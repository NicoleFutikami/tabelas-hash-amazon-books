public class TesteFuncoesHash {

    public static void main(String[] argumentos) {
        char[] ana = {'A', 'n', 'a'};
        int fnv = FuncoesHash.hashFnv1a(ana, 3);

        System.out.println("FNV1a (Ana): " + fnv);
        System.out.println("Polinomial 31 (Ana): " + FuncoesHash.hashPolinomial31(ana, 3));
        System.out.println("djb2 (Ana): " + FuncoesHash.hashDjb2(ana, 3));
        System.out.println("Posicao FNV1a: " + FuncoesHash.calcularPosicao(fnv));

        char[] aa = {'A', 'a'};
        char[] bb = {'B', 'B'};
        System.out.println("Polinomial 31 (Aa): " + FuncoesHash.hashPolinomial31(aa, 2));
        System.out.println("Polinomial 31 (BB): " + FuncoesHash.hashPolinomial31(bb, 2));
    }
}

