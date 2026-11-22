public class ExperimentosHash {

    private static void mostrarFuncao(int tipo){
        if(tipo == 1){
            System.out.println("funcao fnv1a");
        } else if(tipo == 2){
            System.out.println("funcao polinomial 31");
        }else{
            System.out.println("funcao djb2");
        }
    }
    private static void testarUsuarios(TabelaUsuarios usuarios, int tipo){
        TabelaHash teste = new TabelaHash(tipo);

        System.out.println("\nconjunto usuarios");
        mostrarFuncao(tipo);

        for (int indice = 0; indice< usuarios.obterCapacidade(); indice ++){

            NoLista atual = usuarios.obterInicioLista(indice);

            while(atual != null){
                teste.inserir(atual.chave, atual.tamanho);
                atual = atual.proximo;
            }
        }
        System.out.println("chaves originais" + usuarios.obterQuantidadeUsuarios());
        System.out.println("chaves inseridas" + teste.obterQuantidadeChaves());
        System.out.println("colisoes" + teste.obterColisoes());

        if(teste.obterQuantidadeChaves() == usuarios.obterQuantidadeUsuarios()){
            System.out.println("confere");
        } else{
            System.out.println("erro");
        }
    }
    private static void testarLivros(TabelaLivros livros, int tipo){
        TabelaHash teste = new TabelaHash(tipo);

        System.out.println("\n conjunto livros");
        mostrarFuncao(tipo);

        for (int indice = 0; indice < livros.obterCapacidade(); indice++){
            NoLista atual = livros.obterInicioLista(indice);

            while (atual != null){
                teste.inserir(atual.chave, atual.tamanho);
                atual = atual.proximo;

            }
        }
        System.out.println("chaves oroginais" + livros.obterQuantidadeLivros());
        System.out.println("chaves inseridas" + teste.obterQuantidadeChaves());
        System.out.println("colisoes "+ teste.obterColisoes());

        if(teste.obterQuantidadeChaves()== livros.obterQuantidadeLivros()){
            System.out.println("ok");
        }else{
            System.out.println("erro");
        }
    }
    public static void executar(TabelaLivros livros, TabelaUsuarios usuarios){
        System.out.println("teste");
        for(int tipo =1 ; tipo <= 3; tipo++){
            testarUsuarios(usuarios, tipo);
        }
        for(int tipo = 1; tipo <=3; tipo++){
            testarLivros(livros, tipo);
        }
    }
}
