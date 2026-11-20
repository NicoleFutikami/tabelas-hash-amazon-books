public class NoLista {
    char[] chave;
    int tamanho;
    NoLista proximo;

    public NoLista(char[] chaveRecebida, int tamanhoRecebido){
        tamanho = tamanhoRecebido;
        chave = new char[tamanho];

        for(int indice = 0; indice < tamanho; indice ++){
            chave[indice] = chaveRecebida[indice];
        }
        proximo = null;
    }
}
