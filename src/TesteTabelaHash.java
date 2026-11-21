public class TesteTabelaHash {
    public static void main(String[] args){

        char[] ana = {'A', 'n', 'a'};

        for(int tipo = 1; tipo <= 3; tipo ++){

            TabelaHash tabela = new TabelaHash(tipo);

            System.out.println("funcao: " + tipo);

            System.out.println("busca antes: " + tabela.buscar(ana, 3));

            System.out.println("inserir ana" + tabela.inserir(ana , 3));

            System.out.println("busca depois" + tabela.buscar(ana , 3));

            System.out.println("inserir dnv" + tabela.inserir(ana,3 ));

            System.out.println("total " + tabela.obterQuantidadeChaves());
        }
        TabelaHash tabelaColisao = new TabelaHash(2);

        char[] aa = {'A', 'a'};
        char[] bb = {'B', 'b'};
        char[] ax = {'A', 'x'};

        System.out.println("posicao Aa: " + FuncoesHash.calcularPosicao(FuncoesHash.hashPolinomial31(aa,2)));
        System.out.println("posicao Bb " + FuncoesHash.calcularPosicao(FuncoesHash.hashPolinomial31(bb, 2)));
        System.out.println("inserir aa" + tabelaColisao.inserir(aa, 2));
        System.out.println("inserir bb: " + tabelaColisao.inserir(bb ,2 ));
        System.out.println("buscar aa: " + tabelaColisao.buscar(aa, 2));
        System.out.println("buscar bb " + tabelaColisao.buscar(bb, 2));
        System.out.println("buscar ax" + tabelaColisao.buscar(ax, 2));
        System.out.println("total apos colisao: "+ tabelaColisao.obterQuantidadeChaves());

        
    }
}
