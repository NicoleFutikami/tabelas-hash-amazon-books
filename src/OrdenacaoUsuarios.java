import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class OrdenacaoUsuarios {

    private NoUsuarioOrdenado primeiro;
    private int quantidade;

    public void carregarUsuarios(TabelaUsuarios usuarios) throws IOException {
        primeiro = null;
        quantidade = 0;

        for (int i = 0; i < usuarios.obterCapacidade(); i++) {
            NoLista usuario = usuarios.obterInicioLista(i);
            while (usuario != null) {
                if (usuario.melhorAvaliacao == null) {
                    throw new IOException("Melhor livro nao calculado");
                }
                NoUsuarioOrdenado novo = new NoUsuarioOrdenado(usuario);
                novo.proximo = primeiro;
                primeiro = novo;
                quantidade++;
                usuario = usuario.proximo;
            }
        }
    }

    private int vemAntes(NoLista a, NoLista b) {
        NoLista livroA = a.melhorAvaliacao.livro;
        NoLista livroB = b.melhorAvaliacao.livro;
        double mediaA = livroA.somaNotas * livroB.quantidadeAvaliacoes;
        double mediaB = livroB.somaNotas * livroA.quantidadeAvaliacoes;

        if (mediaA > mediaB) return 1;
        if (mediaA < mediaB) return 0;

        if (livroA.quantidadeAvaliacoes > livroB.quantidadeAvaliacoes) return 1;
        if (livroA.quantidadeAvaliacoes < livroB.quantidadeAvaliacoes) return 0;

        int i = 0;
        while (i < a.tamanho && i < b.tamanho) {
            if (a.chave[i] < b.chave[i]) return 1;
            if (a.chave[i] > b.chave[i]) return 0;
            i++;
        }
        if (a.tamanho < b.tamanho) return 1;
        return 0;
    }

    private NoUsuarioOrdenado juntar(NoUsuarioOrdenado a, NoUsuarioOrdenado b) {
        NoUsuarioOrdenado inicio = null;
        NoUsuarioOrdenado ultimo = null;

        while (a != null && b != null) {
            NoUsuarioOrdenado escolhido;
            if (vemAntes(a.usuario, b.usuario) == 1) {
                escolhido = a;
                a = a.proximo;
            } else {
                escolhido = b;
                b = b.proximo;
            }

            if (inicio == null) {
                inicio = escolhido;
            } else {
                ultimo.proximo = escolhido;
            }
            ultimo = escolhido;
        }

        if (a != null) {
            ultimo.proximo = a;
        } else {
            ultimo.proximo = b;
        }
        return inicio;
    }

    private NoUsuarioOrdenado ordenarLista(NoUsuarioOrdenado inicio) {
        if (inicio == null || inicio.proximo == null) return inicio;

        int total = 0;
        NoUsuarioOrdenado atual = inicio;
        while (atual != null) {
            total++;
            atual = atual.proximo;
        }

        atual = inicio;
        for (int i = 1; i < total / 2; i++) {
            atual = atual.proximo;
        }
        NoUsuarioOrdenado segunda = atual.proximo;
        atual.proximo = null;

        NoUsuarioOrdenado esquerda = ordenarLista(inicio);
        NoUsuarioOrdenado direita = ordenarLista(segunda);
        return juntar(esquerda, direita);
    }

    public void ordenar() {
        primeiro = ordenarLista(primeiro);
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

    public int gerarArquivoOrdenado(String caminho) throws IOException {
        PrintWriter arquivo = new PrintWriter(new FileWriter(caminho));
        arquivo.println("User_id,profileName,Title,media_livro,N_livro,nota_usuario");
        int linhas = 0;

        NoUsuarioOrdenado atual = primeiro;
        while (atual != null) {
            NoLista usuario = atual.usuario;
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
            atual = atual.proximo;
        }
        arquivo.close();
        return linhas;
    }

    public int obterQuantidade() {
        return quantidade;
    }
}


