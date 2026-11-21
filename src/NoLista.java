public class NoLista {
    char[] chave;
    int tamanho;
    NoLista proximo;

    double somaNotas;
    int quantidadeAvaliacoes;

    public NoLista(char[] chaveRecebida, int tamanhoRecebido){
        tamanho = tamanhoRecebido;
        chave = new char[tamanho];

        for(int indice = 0; indice < tamanho; indice ++){
            chave[indice] = chaveRecebida[indice];
        }
        proximo = null;

        somaNotas = 0.0;
        quantidadeAvaliacoes = 0;
    }
}
