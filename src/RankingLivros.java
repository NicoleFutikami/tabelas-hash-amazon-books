public class RankingLivros {
    private NoRanking primeiro = null;
    private int quantidade = 0;
    private int livroVemAntes(NoLista candidato, NoLista outro) {

        double produtoCandidato = candidato.somaNotas * outro.quantidadeAvaliacoes;

        double produtoOutro =outro.somaNotas * candidato.quantidadeAvaliacoes;


        if (produtoCandidato > produtoOutro) {
            return 1;
        }
        if (produtoCandidato < produtoOutro) {
            return 0;
        }
        if (candidato.quantidadeAvaliacoes >
                outro.quantidadeAvaliacoes) {
            return 1;
        }
        if (candidato.quantidadeAvaliacoes <
                outro.quantidadeAvaliacoes) {
            return 0;
        }
        int indice = 0;

        while (indice < candidato.tamanho &&
                indice < outro.tamanho) {
            if (candidato.chave[indice] < outro.chave[indice]) {
                return 1;
            }
            if (candidato.chave[indice] > outro.chave[indice]) {
                return 0;
            }
            indice++;
        }
        if (candidato.tamanho < outro.tamanho) {
            return 1;
        }
        return 0;
    }
    private void considerarLivro(NoLista livro) {
        if (livro.quantidadeAvaliacoes < 100) {
            return;
        }
        NoRanking anterior = null;
        NoRanking atual = primeiro;
        int posicao = 0;
        while (atual != null &&
                livroVemAntes(livro, atual.livro) == 0) {
            anterior = atual;
            atual = atual.proximo;
            posicao++;
        }
        if (posicao >= 10) {
            return;
        }
        NoRanking novo = new NoRanking(livro);
        novo.proximo = atual;
        if (anterior == null) {
            primeiro = novo;
        } else {
            anterior.proximo = novo;
        }
        if (quantidade < 10) {
            quantidade++;
        } else {
            NoRanking ultimo = primeiro;
            int contador = 1;

            while (contador < 10) {
                ultimo = ultimo.proximo;
                contador++;
            }

            ultimo.proximo = null;
        }
    }
    public void gerarRanking(TabelaLivros livros) {
        primeiro = null;
        quantidade = 0;

        for (int indice = 0;
             indice < livros.obterCapacidade(); indice++) {

            NoLista livro = livros.obterInicioLista(indice);

            while (livro != null) {
                considerarLivro(livro);
                livro = livro.proximo;
            }
        }
    }
    public void mostrarRanking() {

        System.out.println(
                "top 10 livros min 100 avl"
        );
        if (primeiro == null) {
            System.out.println(
                    "Nenhum livro possui 100 avaliacoes"
            );
            return;
        }
        NoRanking atual = primeiro;
        int posicao = 1;
        while (atual != null) {
            NoLista livro = atual.livro;
            System.out.print(posicao + ". ");
            for (int indice = 0; indice < livro.tamanho; indice++) {
                System.out.print(livro.chave[indice]);
            }
            System.out.println("Media" +(livro.somaNotas / livro.quantidadeAvaliacoes) +"n " + livro.quantidadeAvaliacoes
            );
            atual = atual.proximo;
            posicao++;
        }
    }
}
