public class NoLista {
    char[] chave;
    int tamanho;
    NoLista proximo;

    double somaNotas;
    int quantidadeAvaliacoes;

    char[] nomePerfil;
    int tamanhoPerfil;

    char[]melhorLivro;
    int tamanhoMelhorLivro;

    public NoLista(char[] chaveRecebida, int tamanhoRecebido){
        tamanho = tamanhoRecebido;
        chave = new char[tamanho];

        for(int indice = 0; indice < tamanho; indice ++){
            chave[indice] = chaveRecebida[indice];
        }
        proximo = null;

        somaNotas = 0.0;
        quantidadeAvaliacoes = 0;

        nomePerfil = null;
        tamanhoPerfil = 0;

        melhorLivro = null;
        tamanhoMelhorLivro = 0;
    }
}
