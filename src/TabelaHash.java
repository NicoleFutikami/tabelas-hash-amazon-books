public class TabelaHash{
    private NoLista[] listas = new NoLista[1000003];
    private int tipoFuncao;
    private int quantidadeChaves;
    private int colisoes;
    private int ultimaSondagem;
    private int piorCaso;

    public TabelaHash(int tipo){
        tipoFuncao = tipo;
    }
    private int calcularIndice(char[] chave, int tamanho){
        int h;

        if(tipoFuncao == 1){
            h = FuncoesHash.hashFnv1a(chave, tamanho);
        }else if( tipoFuncao ==2 ){
            h = FuncoesHash.hashPolinomial31(chave, tamanho);
        }else{
            h = FuncoesHash.hashDjb2(chave, tamanho);
        }
        return FuncoesHash.calcularPosicao(h);
    }
    private int chavesIguais(char[] primeira, int tamanhoPrimeira, char[] segunda, int tamanhoSegunda){
        if(tamanhoPrimeira != tamanhoSegunda){
            return 0;
        }
        for(int i = 0; i < tamanhoPrimeira; i++){
            if(primeira[i] != segunda[i]){
                return 0;
            }
        }
        return 1;
    }
    public NoLista encontrarNo(char[] chave, int tamanho){
        int indice = calcularIndice(chave, tamanho);
        NoLista atual = listas[indice];
        ultimaSondagem = 0;

        while(atual != null){
            ultimaSondagem++;
            if(chavesIguais(atual.chave, atual.tamanho, chave,tamanho)== 1){
                break;
            }
            atual = atual.proximo;
        }
        if (ultimaSondagem > piorCaso){
            piorCaso = ultimaSondagem;
        }
        return atual;
    }
    private NoLista adicionarNovo(char[] chave, int tamanho){
        int indice = calcularIndice(chave, tamanho);

        if (listas[indice] != null){
            colisoes++;
        }
        NoLista novo = new NoLista(chave, tamanho);
        novo.proximo = listas[indice];
        listas[indice] = novo;
        quantidadeChaves++;
        return novo;
    }
    public int inserir(char[] chave, int tamanho){
        if(encontrarNo(chave, tamanho) != null){
            return 0;
        }
        adicionarNovo(chave, tamanho);
        return 1;
    }
    public int buscar(char[] chave, int tamanho){
        if(encontrarNo(chave, tamanho) != null){
            return 1;
        }
        return 0;
    }
    public void registrarNotaLivro(char[] titulo, int tamanho, double nota){
        NoLista livro = encontrarNo(titulo, tamanho);

        if (livro == null){
            livro = adicionarNovo(titulo , tamanho);
        }
        livro.somaNotas = livro.somaNotas + nota;
        livro.quantidadeAvaliacoes++;
    }
    public int obterUltimaSondagem(){
        return ultimaSondagem;
    }
    public int obterPiorCaso(){
        return piorCaso;
    }
    public int obterQuantidadeChaves(){
        return quantidadeChaves;
    }
    public int obterColisoes(){
        return colisoes;
    }
    public int obterCapacidade(){
        return 1000003;
    }
    public NoLista obterInicioLista(int indice){
        return listas[indice];
    }
}