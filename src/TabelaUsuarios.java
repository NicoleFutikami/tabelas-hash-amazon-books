import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TabelaUsuarios {
    private TabelaHash tabela = new TabelaHash(1);
    private int perfilComConteudo(char[] perfil, int tamanho) {
        for (int i = 0; i < tamanho; i++) {
            if (perfil[i] != ' ' && perfil[i] != '\t' &&
                    perfil[i] != '\n' && perfil[i] != '\r') {
                return 1;
            }
        }
        return 0;
    }
    public void adicionarUsuario(char[] id, int tamanhoId, char[] perfil, int tamanhoPerfil) {
        NoLista usuario = tabela.encontrarNo(id, tamanhoId);

        if (usuario == null) {
            tabela.inserir(id, tamanhoId);
            usuario = tabela.encontrarNo(id, tamanhoId);
        }

        if (usuario.tamanhoPerfil == 0 && perfilComConteudo(perfil, tamanhoPerfil) == 1) {
            usuario.nomePerfil = new char[tamanhoPerfil];
            for (int i = 0; i < tamanhoPerfil; i++) {
                usuario.nomePerfil[i] = perfil[i];
            }
            usuario.tamanhoPerfil = tamanhoPerfil;
        }
    }

    public void registrarLivroAvaliado(char[] id, int tamanhoId, NoLista livro, double nota) {
        NoLista usuario = tabela.encontrarNo(id, tamanhoId);
        if (usuario == null || livro == null) {
            return;
        }

        NoAvaliacao atual = usuario.primeiraAvaliacao;
        while (atual != null) {
            if (atual.livro == livro) {
                if (nota > atual.maiorNota) {
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

    private int tituloVemAntes(NoLista a, NoLista b) {
        int i = 0;
        while (i < a.tamanho && i < b.tamanho) {
            if (a.chave[i] < b.chave[i]) return 1;
            if (a.chave[i] > b.chave[i]) return 0;
            i++;
        }
        if (a.tamanho < b.tamanho) return 1;
        return 0;
    }

    private int avaliacaoMelhor(NoAvaliacao a, NoAvaliacao b) {
        double produtoA = a.livro.somaNotas * b.livro.quantidadeAvaliacoes;
        double produtoB = b.livro.somaNotas * a.livro.quantidadeAvaliacoes;

        if (produtoA > produtoB) return 1;
        if (produtoA < produtoB) return 0;

        if (a.livro.quantidadeAvaliacoes > b.livro.quantidadeAvaliacoes) return 1;
        if (a.livro.quantidadeAvaliacoes < b.livro.quantidadeAvaliacoes) return 0;

        return tituloVemAntes(a.livro, b.livro);
    }

    public void calcularMelhoresLivros() {
        for (int i = 0; i < tabela.obterCapacidade(); i++) {
            NoLista usuario = tabela.obterInicioLista(i);
            while (usuario != null) {
                NoAvaliacao atual = usuario.primeiraAvaliacao;
                NoAvaliacao melhor = null;

                while (atual != null) {
                    if (melhor == null || avaliacaoMelhor(atual, melhor) == 1) {
                        melhor = atual;
                    }
                    atual = atual.proximo;
                }

                usuario.melhorAvaliacao = melhor;
                usuario = usuario.proximo;
            }
        }
    }

    private void imprimirTexto(char[] texto, int tamanho) {
        for (int i = 0; i < tamanho; i++) {
            System.out.print(texto[i]);
        }
    }

    public void mostrarUsuario(char[] id, int tamanhoId) {
        NoLista usuario = tabela.encontrarNo(id, tamanhoId);
        if (usuario == null) {
            System.out.println("Usuario nao encontrado");
            return;
        }

        System.out.print("User_id: ");
        imprimirTexto(usuario.chave, usuario.tamanho);
        System.out.print("\nPerfil: ");
        imprimirTexto(usuario.nomePerfil, usuario.tamanhoPerfil);
        System.out.println();

        if (usuario.melhorAvaliacao == null) {
            System.out.println("Melhor livro ainda nao calculado");
            return;
        }

        NoAvaliacao avaliacao = usuario.melhorAvaliacao;
        NoLista livro = avaliacao.livro;
        System.out.print("Melhor livro: ");
        imprimirTexto(livro.chave, livro.tamanho);
        System.out.println("\nMedia global: " + livro.somaNotas / livro.quantidadeAvaliacoes);
        System.out.println("Avaliacoes do livro: " + livro.quantidadeAvaliacoes);
        System.out.println("Nota do usuario: " + avaliacao.maiorNota);
    }

    private void escreverCampoCsv(PrintWriter arquivo, char[] texto, int tamanho) {
        arquivo.print('"');
        if (texto != null) {
            for (int i = 0; i < tamanho; i++) {
                if (texto[i] == '"') arquivo.print('"');
                arquivo.print(texto[i]);
            }
        }
        arquivo.print('"');
    }

    public int gerarArquivoCsv(String caminho) throws IOException {
        PrintWriter arquivo = new PrintWriter(new FileWriter(caminho));
        arquivo.println("User_id,profileName,Title,media_livro,N_livro,nota_usuario");
        int linhas = 0;

        for (int i = 0; i < tabela.obterCapacidade(); i++) {
            NoLista usuario = tabela.obterInicioLista(i);
            while (usuario != null) {
                if (usuario.melhorAvaliacao == null) {
                    arquivo.close();
                    return -1;
                }

                NoAvaliacao avaliacao = usuario.melhorAvaliacao;
                NoLista livro = avaliacao.livro;
                escreverCampoCsv(arquivo, usuario.chave, usuario.tamanho);
                arquivo.print(',');
                escreverCampoCsv(arquivo, usuario.nomePerfil, usuario.tamanhoPerfil);
                arquivo.print(',');
                escreverCampoCsv(arquivo, livro.chave, livro.tamanho);
                arquivo.print(',');
                arquivo.print(livro.somaNotas / livro.quantidadeAvaliacoes);
                arquivo.print(',');
                arquivo.print(livro.quantidadeAvaliacoes);
                arquivo.print(',');
                arquivo.println(avaliacao.maiorNota);

                linhas++;
                usuario = usuario.proximo;
            }
        }
        arquivo.close();
        return linhas;
    }

    public int obterQuantidadeUsuarios() {
        return tabela.obterQuantidadeChaves();
    }

    public int obterCapacidade() {
        return tabela.obterCapacidade();
    }

    public NoLista obterInicioLista(int indice) {
        return tabela.obterInicioLista(indice);
    }
}
