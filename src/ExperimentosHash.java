public class ExperimentosHash {

    // Busca as chaves na tabela original de usuarios ou livros.
    private static NoLista obterInicio(int conjunto, int indice,
                                       TabelaLivros livros, TabelaUsuarios usuarios) {
        if (conjunto == 1) {
            return usuarios.obterInicioLista(indice);
        }
        return livros.obterInicioLista(indice);
    }

    private static void testarConfiguracao(TabelaLivros livros, TabelaUsuarios usuarios,
                                           int conjunto, int tipo) {
        int quantidade;
        int capacidade;

        if (conjunto == 1) {
            quantidade = usuarios.obterQuantidadeUsuarios();
            capacidade = usuarios.obterCapacidade();
            System.out.println("\nConjunto: USUARIOS");
        } else {
            quantidade = livros.obterQuantidadeLivros();
            capacidade = livros.obterCapacidade();
            System.out.println("\nConjunto: TITULOS");
        }

        if (tipo == 1) System.out.println("Funcao: FNV1a");
        else if (tipo == 2) System.out.println("Funcao: Polinomial 31");
        else System.out.println("Funcao: djb2");

        double totalInsercao = 0;
        double totalBusca = 0;
        double mediaSucesso = 0;
        double mediaFalha = 0;
        int colisoes = 0;
        int piorCaso = 0;

        // Cada configuracao e repetida tres vezes com uma tabela vazia.
        for (int repeticao = 1; repeticao <= 3; repeticao++) {
            TabelaHash teste = new TabelaHash(tipo);

            double inicio = System.nanoTime();
            for (int i = 0; i < capacidade; i++) {
                NoLista atual = obterInicio(conjunto, i, livros, usuarios);
                while (atual != null) {
                    teste.inserir(atual.chave, atual.tamanho);
                    atual = atual.proximo;
                }
            }
            double tempoInsercao = (System.nanoTime() - inicio) / 1000000.0;
            totalInsercao += tempoInsercao;

            double sondagensSucesso = 0;
            inicio = System.nanoTime();
            for (int i = 0; i < capacidade; i++) {
                NoLista atual = obterInicio(conjunto, i, livros, usuarios);
                while (atual != null) {
                    teste.buscar(atual.chave, atual.tamanho);
                    sondagensSucesso += teste.obterUltimaSondagem();
                    atual = atual.proximo;
                }
            }
            double tempoBusca = (System.nanoTime() - inicio) / 1000000.0;
            totalBusca += tempoBusca;

            // As contagens sao iguais nas tres repeticoes.
            if (repeticao == 1) {
                double sondagensFalha = 0;
                for (int i = 0; i < capacidade; i++) {
                    NoLista atual = obterInicio(conjunto, i, livros, usuarios);
                    while (atual != null) {
                        char[] inexistente = new char[atual.tamanho + 1];
                        for (int j = 0; j < atual.tamanho; j++) {
                            inexistente[j] = atual.chave[j];
                        }
                        inexistente[atual.tamanho] = (char) 1;

                        teste.buscar(inexistente, atual.tamanho + 1);
                        sondagensFalha += teste.obterUltimaSondagem();
                        atual = atual.proximo;
                    }
                }

                colisoes = teste.obterColisoes();
                piorCaso = teste.obterPiorCaso();
                if (quantidade > 0) {
                    mediaSucesso = sondagensSucesso / quantidade;
                    mediaFalha = sondagensFalha / quantidade;
                }
            }

            System.out.println("Repeticao " + repeticao +
                    " | insercao: " + tempoInsercao + " ms" +
                    " | busca: " + tempoBusca + " ms");
        }

        System.out.println("Quantidade de chaves: " + quantidade);
        System.out.println("Colisoes: " + colisoes);
        System.out.println("Media sondagens sucesso: " + mediaSucesso);
        System.out.println("Media sondagens falha: " + mediaFalha);
        System.out.println("Pior caso: " + piorCaso);
        System.out.println("Tempo medio insercao (ms): " + totalInsercao / 3);
        System.out.println("Tempo medio busca (ms): " + totalBusca / 3);
    }

    public static void executar(TabelaLivros livros, TabelaUsuarios usuarios) {
        System.out.println("\n=== EXPERIMENTOS HASH ===");
        for (int conjunto = 1; conjunto <= 2; conjunto++) {
            for (int tipo = 1; tipo <= 3; tipo++) {
                testarConfiguracao(livros, usuarios, conjunto, tipo);
            }
        }
    }
}
