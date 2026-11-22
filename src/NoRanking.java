public class NoRanking {
    NoLista livro;
    NoRanking proximo;

    public NoRanking(NoLista livroRecebido){
        livro = livroRecebido;
        proximo = null;
    }
}
