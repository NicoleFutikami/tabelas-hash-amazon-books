public class NoLista {
    char[] chave;
    int tamanho;
    NoLista proximo;

    double somaNotas;
    int quantidadeAvaliacoes;

    char[] nomePerfil;
    int tamanhoPerfil;

    NoAvaliacao primeiraAvaliacao;
    NoAvaliacao melhorAvaliacao;

    public NoLista(char[] chaveRecebida, int tamanhoRecebido){
        tamanho = tamanhoRecebido;
        chave = new char[tamanho];

        for (int i = 0; i < tamanho; i ++){
            chave[i] = chaveRecebida[i];}
    }
}
