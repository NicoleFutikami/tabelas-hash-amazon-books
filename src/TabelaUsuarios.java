public class TabelaUsuarios {

    private TabelaHash tabela = new TabelaHash(1);

    private int perfilComConteudo(char[] perfil, int tamanho){
        for(int indice = 0; indice < tamanho; indice ++){
            char caractere = perfil[indice];

            if(caractere != ' ' && caractere != '\t' && caractere != '\n' && caractere != '\r'){
                return 1;
            }
        }
        return 0;
    }

    public void adicionarUsuario(char[] identificador, int tamanhoIdentificador, char[] perfil, int tamanhoPerfil){
        tabela.inserir(identificador, tamanhoIdentificador);

        NoLista usuario = tabela.encontrarNo(identificador, tamanhoIdentificador);
    }
}
