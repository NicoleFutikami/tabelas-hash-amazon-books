public class TesteTabelaHash {
    public static void main(String[] args){

        char[] ana = {'A', 'n', 'a'};

        for(int tipo = 1; tipo <= 3; tipo ++){

            TabelaHash tabela = new TabelaHash(tipo);

            System.out.println("funcao" + tipo);

            System.out.println("busca antes " + tabela.buscar(ana, 3));

            System.out.println("inserir ana" + tabela.inserir(ana , 3));

            System.out.println("busca depois" + tabela.buscar(ana , 3));

            System.out.println("inserir dnv" + tabela.inserir(ana,3 ));

            System.out.println("total " + tabela.obterQuantidadeChaves());
        }
    }
}
