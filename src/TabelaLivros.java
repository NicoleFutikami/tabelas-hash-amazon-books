public class TabelaLivros {

    private TabelaHash tabela = new TabelaHash(1);

    public void adicionarAvaliacao(char[] titulo, int tamanho, double nota){
        tabela.registrarNotaLivro(titulo, tamanho, nota);
    }
    public int obterQuantidadeLivros(){
        return tabela.obterQuantidadeChaves();
    }
    public void mostrarLivro(char[] titulo, int tamanho){
        NoLista livro = tabela.encontrarNo(titulo, tamanho);

        if(livro == null ){
            System.out.println("Livro nao encontrado");
            return;
        }

        System.out.print("livro: ");
        for (int indice = 0; indice < livro.tamanho; indice++ ){
            System.out.println(livro.chave[indice]);
        }
        System.out.println();
        System.out.println("soma das notas: " + livro.somaNotas);
        System.out.println("quantidade de avaliacoes: " + livro.quantidadeAvaliacoes);
        System.out.println("media global: " + (livro.somaNotas / livro.quantidadeAvaliacoes));
    }
}
