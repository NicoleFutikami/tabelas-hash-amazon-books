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

        if (usuario.tamanhoPerfil == 0 && perfilComConteudo(perfil, tamanhoPerfil) == 1){
            usuario.nomePerfil = new char[tamanhoPerfil];

            for(int indice = 0; indice< tamanhoPerfil; indice ++){
                usuario.nomePerfil[indice] = perfil[indice];
            }
            usuario.tamanhoPerfil = tamanhoPerfil;
        }
    }
    public int obterQuantidadeUsuarios(){
        return tabela.obterQuantidadeChaves();
    }
    public void mostrarUsuario(char[] identificador, int tamanho){
        NoLista usuario = tabela.encontrarNo(identificador, tamanho);

        if (usuario == null){
            System.out.println("Usuario nao encontrado");
            return;
        }
        System.out.println("user id: ");
        for (int indice = 0; indice < usuario.tamanho; indice++){
            System.out.print(usuario.chave[indice]);
        }
        System.out.println();
        System.out.print("nome do perfil ");

        for(int indice = 0; indice < usuario.tamanhoPerfil; indice++){
            System.out.print(usuario.nomePerfil[indice]);
        }
        System.out.println();
    }
    public void registrarLivroAvaliado(char[] identificador, int tamanhoIdentificador, NoLista livro, double nota){
        NoLista usuario = tabela.encontrarNo(identificador, tamanhoIdentificador);

        if(usuario == null || livro == null){
            return;
        }
        NoAvaliacao atual = usuario.primeiraAvaliacao;

        while(atual != null){
            if(atual.livro == livro){
                if(nota > atual.maiorNota){
                    atual.maiorNota = nota;
                }
                return;
            }
            atual = atual.proximo;
        }
        NoAvaliacao nova = new NoAvaliacao(livro, nota);

        nova.proximo = usuario.primeiraAvaliacao;
        usuario.primeiraAvaliacao = nova;

    }
    public void mostrarLivrosAvalidados(char[] identificador, int tamanho){
        NoLista usuario = tabela.encontrarNo(identificador, tamanho);

        if(usuario == null){
            System.out.println("usuario nao encontrado");
            return;
        }
        System.out.print("livros de ");
        for(int indice = 0; indice < usuario.tamanho; indice ++){
            System.out.print(usuario.chave[indice]);
        }
        System.out.println(":");

        NoAvaliacao atual = usuario.primeiraAvaliacao;

        while (atual != null){
            System.out.print("titulo");
            for(int indice = 0; indice < atual.livro.tamanho; indice ++){
                System.out.print(atual.livro.chave[indice]);
            }
            System.out.println("maior nota do usuario" + atual.maiorNota);

            atual = atual.proximo;
        }
    }
    private int tituloVemAntes(NoLista primeiro, NoLista segundo){
        int indice = 0;

        while(indice < primeiro.tamanho && indice< segundo.tamanho){

            if(primeiro.chave[indice]< segundo.chave[indice]){
                return 1;
            }
            if(primeiro.chave[indice] > segundo.chave[indice]){
                return 0;
            }
            indice++;
        }
        if(primeiro.tamanho < segundo.tamanho){
            return 1;
        }
        return 0;
    }

}
