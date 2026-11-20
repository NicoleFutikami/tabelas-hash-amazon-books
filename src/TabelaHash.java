public class TabelaHash {

    private NoLista[] listas = new NoLista[1000003];

    private int tipoFuncao;
    private int quantidadeChaves = 0;
    private int colisoes = 0;

    public TabelaHash(int funcaoEscolhida){
        tipoFuncao = funcaoEscolhida;
    }

    private int calcularIndice(char[] chave, int tamanho) {
        int valorHash;

        if (tipoFuncao == 1) {
            valorHash = FuncoesHash.hashFnv1a(chave, tamanho);
        } else if (tipoFuncao == 2) {
            valorHash = FuncoesHash.hashPolinomial31(chave, tamanho);
        } else {
            valorHash = FuncoesHash.hashDjb2(chave, tamanho);
        }
        return FuncoesHash.calcularPosicao(valorHash);
    }
}
