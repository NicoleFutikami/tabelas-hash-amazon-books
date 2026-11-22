
public class RankingLivros {

    private NoLista[] melhores = new NoLista[10];
    private int quantidade;

    private int livroVemAntes(NoLista a, NoLista b) {
        double valorA = a.somaNotas * b.quantidadeAvaliacoes;
        double valorB = b.somaNotas * a.quantidadeAvaliacoes;

        if (valorA > valorB) return 1;
        if (valorA < valorB) return 0;

        if (a.quantidadeAvaliacoes > b.quantidadeAvaliacoes) return 1;
        if (a.quantidadeAvaliacoes < b.quantidadeAvaliacoes) return 0;

        int i = 0;
        while (i < a.tamanho && i < b.tamanho) {
            if (a.chave[i] < b.chave[i]) return 1;
            if (a.chave[i] > b.chave[i]) return 0;
            i++;
        }
        if (a.tamanho < b.tamanho) return 1;
        return 0;
    }

    private void considerarLivro(NoLista livro) {
        if (livro.quantidadeAvaliacoes < 100) return;

        int posicao = 0;
        while (posicao < quantidade &&
                livroVemAntes(livro, melhores[posicao]) == 0) {
            posicao++;
        }

        if (posicao == 10) return;

        int ultimo = quantidade;
        if (ultimo == 10) ultimo = 9;

        for (int i = ultimo; i > posicao; i--) {
            melhores[i] = melhores[i - 1];
        }
        melhores[posicao] = livro;
        if (quantidade < 10) quantidade++;
    }

    public void gerarRanking(TabelaLivros livros) {
        quantidade = 0;
        for (int i = 0; i < 10; i++) {
            melhores[i] = null;
        }

        for (int i = 0; i < livros.obterCapacidade(); i++) {
            NoLista livro = livros.obterInicioLista(i);
            while (livro != null) {
                considerarLivro(livro);
                livro = livro.proximo;
            }
        }
    }

    public void mostrarRanking() {
        System.out.println("=== TOP 10 LIVROS (MINIMO 100 AVALIACOES) ===");
        if (quantidade == 0) {
            System.out.println("Nenhum livro possui 100 avaliacoes.");
            return;
        }

        for (int i = 0; i < quantidade; i++) {
            NoLista livro = melhores[i];
            System.out.print((i + 1) + ". ");
            for (int j = 0; j < livro.tamanho; j++) {
                System.out.print(livro.chave[j]);
            }
            System.out.println(" | Media: " +
                    (livro.somaNotas / livro.quantidadeAvaliacoes) +
                    " | N: " + livro.quantidadeAvaliacoes);
        }
    }
}
