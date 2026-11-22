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
    private int chavesIguais(char[] primeira, int tamanhoPrimeira,char[] segunda, int tamanhoSegunda) {
        if (tamanhoPrimeira != tamanhoSegunda) {
            return 0;
        }
        for (int indice = 0; indice < tamanhoPrimeira; indice++) {
            if (primeira[indice] != segunda[indice]) {
                return 0;
            }
        }
        return 1;
    }
    public int inserir(char[] chave, int tamanho){
        int indice = calcularIndice(chave, tamanho);

        NoLista atual = listas[indice];

        while(atual != null){
            if(chavesIguais(atual.chave, atual.tamanho, chave, tamanho) ==1 ){
                return 0;
            }
            atual = atual.proximo;
        }
        if(listas[indice] != null){
            colisoes++;
        }
        NoLista novoNo = new NoLista(chave, tamanho);

        novoNo.proximo = listas[indice];
        listas[indice] = novoNo;

        quantidadeChaves ++;

        return 1;
    }
    public int buscar(char[] chave, int tamanho){
        int indice = calcularIndice(chave, tamanho);
        NoLista atual = listas[indice];

        while (atual != null){
            if(chavesIguais(atual.chave, atual.tamanho, chave, tamanho) ==1 ){
                return 1;
            }
            atual = atual.proximo;
        }
        return 0;
    }
    public int obterQuantidadeChaves() {
        return quantidadeChaves;
    }
    public int obterColisoes(){
        return colisoes;
    }

    public NoLista encontrarNo(char [] chave, int tamanho){
        int indice = calcularIndice(chave, tamanho);
        NoLista atual = listas[indice];

        while(atual != null){
            if(chavesIguais(atual.chave, atual.tamanho, chave, tamanho) == 1){
                return atual;
            }
            atual = atual.proximo;
        }
        return null;
    }
    public void registrarNotaLivro(char[] titulo, int tamanho, double nota){
        int indice = calcularIndice(titulo, tamanho);
        NoLista atual = listas[indice];

        while(atual != null ){
            if(chavesIguais(atual.chave, atual.tamanho,titulo, tamanho) == 1){

                atual.somaNotas = atual.somaNotas + nota;
                atual.quantidadeAvaliacoes ++;

                return;
            }
            atual = atual.proximo;
        }
        if (listas[indice] != null){
            colisoes++;
        }
        NoLista novo = new NoLista(titulo, tamanho);

        novo.somaNotas = nota;
        novo.quantidadeAvaliacoes = 1;

        novo.proximo = listas[indice];
        listas[indice] = novo;

        quantidadeChaves++;

    }
    public int obterCapacidade(){ return 1000003;}
    public NoLista obterInicioLista(int indice){
        return listas[indice];
    }
}
