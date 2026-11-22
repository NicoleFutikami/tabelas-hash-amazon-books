import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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

        if(usuario.melhorAvaliacao == null){
            System.out.println("melhor livro ainda n calculado ");
        }else{
            NoAvaliacao escolha = usuario.melhorAvaliacao;
            NoLista livro = escolha.livro;

            System.out.println("mlehor livro");
            for(int indice = 0; indice < livro.tamanho; indice++){
                System.out.print(livro.chave[indice]);
            }
            System.out.println();

            System.out.println("media global" + (livro.somaNotas / livro.quantidadeAvaliacoes));
            System.out.println("n do livro: " +livro.quantidadeAvaliacoes);
            System.out.println("nota do usuario: " + escolha.maiorNota);
        }
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
    private int avaliacaoMelhor(NoAvaliacao candidata, NoAvaliacao melhor){

        double produtoCandidata = candidata.livro.somaNotas * melhor.livro.quantidadeAvaliacoes;

        double produtoMelhor = melhor.livro.somaNotas * candidata.livro.quantidadeAvaliacoes;

        if(produtoCandidata > produtoMelhor){
            return 1;
        }
        if(produtoCandidata < produtoMelhor){
            return 0;
        }
        if(candidata.livro.quantidadeAvaliacoes> melhor.livro.quantidadeAvaliacoes){
            return 1;
        }
        if(candidata.livro.quantidadeAvaliacoes < melhor.livro.quantidadeAvaliacoes){
            return 0;
        }
        return tituloVemAntes(candidata.livro, melhor.livro);
    }

    public void calcularMelhoresLivros(){
        for(int indice = 0; indice < tabela.obterCapacidade(); indice++){
            NoLista usuario = tabela.obterInicioLista(indice);


            while (usuario != null){
                NoAvaliacao atual = usuario.primeiraAvaliacao;
                NoAvaliacao melhor = null;

                while (atual != null){
                    if(melhor == null || avaliacaoMelhor(atual, melhor) == 1){
                        melhor = atual;
                    }
                    atual = atual.proximo;
                }
                usuario.melhorAvaliacao = melhor;

                usuario = usuario.proximo;
            }
        }
    }
    private void escreverCampoCsv(PrintWriter arquivo,char[] texto, int tamanho) {
        arquivo.print('"');
        if (texto != null) {
            for (int indice = 0; indice < tamanho; indice++) {
                if (texto[indice] == '"') {
                    arquivo.print('"');
                }
                arquivo.print(texto[indice]);
            }
        }
        arquivo.print('"');
    }
    public int gerarArquivoCsv(String caminho) throws IOException{
        PrintWriter arquivo = new PrintWriter(new FileWriter(caminho));

        int quantidadeLinhas = 0;

        arquivo.println("User_id, profileName, Title, media_livro, N_livro, nota_usuario");

        for(int indice = 0; indice < tabela.obterCapacidade(); indice++){
            NoLista usuario = tabela.obterInicioLista(indice);

            while(usuario != null){
                if(usuario.melhorAvaliacao == null){
                    arquivo.close();
                    return -1;
                }
                NoAvaliacao avaliacao = usuario.melhorAvaliacao;
                NoLista livro = avaliacao.livro;

                escreverCampoCsv(arquivo, usuario.chave,usuario.tamanho);
                arquivo.print(',');
                escreverCampoCsv(arquivo, usuario.nomePerfil, usuario.tamanhoPerfil);
                arquivo.print(',');
                escreverCampoCsv(arquivo, livro.chave, livro.tamanho);
                arquivo.print(',');
                double media = livro.somaNotas / livro.quantidadeAvaliacoes;
                arquivo.print(media);
                arquivo.println(',');

                arquivo.print(livro.quantidadeAvaliacoes);
                arquivo.print(',');

                arquivo.print(avaliacao.maiorNota);
                quantidadeLinhas++;

                usuario = usuario.proximo;
            }
        }
        arquivo.close();
        return quantidadeLinhas;
    }
    public int obterCapacidade(){
        return tabela.obterCapacidade();
    }

    public NoLista obterInicioLista(int indice){
        return tabela.obterInicioLista(indice);
    }
}
