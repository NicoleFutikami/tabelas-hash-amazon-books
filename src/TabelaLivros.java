public class TabelaLivros{
    private TabelaHash tabela = new TabelaHash(1);

    public void adicionarAvaliacao(char[] titulo, int tamanho, double nota){
        tabela.registrarNotaLivro(titulo, tamanho, nota);
    }
    public NoLista encontrarLivro(char[] titulo, int tamanho){
        return tabela.encontrarNo(titulo,tamanho);
    }
    public int obterQuantidadeLivros(){
        return tabela.obterQuantidadeChaves();
    }
    public int obterCapacidade(){
        return tabela.obterCapacidade();
    }
    public NoLista obterInicioLista(int indice){
        return tabela.obterInicioLista(indice);
    }
}