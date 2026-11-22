import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class OrdenacaoUsuarios {
    private NoUsuarioOrdenado primeiro = null;
    private int quantidade = 0;

    public void carregarUsuarios(TabelaUsuarios usuarios)throws IOException {
        primeiro = null;
        quantidade = 0;
        for (int indice = 0; indice < usuarios.obterCapacidade(); indice++) {
            NoLista usuario = usuarios.obterInicioLista(indice);

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
    private int usuarioVemAntes(NoLista usuarioA, NoLista usuarioB) {

        NoLista livroA = usuarioA.melhorAvaliacao.livro;
        NoLista livroB = usuarioB.melhorAvaliacao.livro;
        double valorA = livroA.somaNotas * livroB.quantidadeAvaliacoes;
        double valorB = livroB.somaNotas * livroA.quantidadeAvaliacoes;
        if (valorA > valorB) { //maior media
            return 1;
        }
        if (valorA < valorB) {
            return 0;
        }
        if (livroA.quantidadeAvaliacoes > livroB.quantidadeAvaliacoes) {
            return 1;
        }
        if (livroA.quantidadeAvaliacoes < livroB.quantidadeAvaliacoes) {
            return 0;
        }
        int indice = 0;
        while (indice < usuarioA.tamanho && indice < usuarioB.tamanho) {
            if (usuarioA.chave[indice] < usuarioB.chave[indice]) {
                return 1;
            }
            if (usuarioA.chave[indice] > usuarioB.chave[indice]) {
                return 0;
            }
            indice++;
        }
        if (usuarioA.tamanho < usuarioB.tamanho) {
            return 1;
        }
        return 0;
    }
    private NoUsuarioOrdenado juntar(NoUsuarioOrdenado esquerda,NoUsuarioOrdenado direita) {
        NoUsuarioOrdenado inicio = null;
        NoUsuarioOrdenado ultimo = null;
        while (esquerda != null && direita != null) {
            NoUsuarioOrdenado escolhido;
            if (usuarioVemAntes(esquerda.usuario, direita.usuario) == 1) {
                escolhido = esquerda;
                esquerda = esquerda.proximo;
            } else {
                escolhido = direita;
                direita = direita.proximo;
            }

            if (inicio == null) {
                inicio = escolhido;
            } else {
                ultimo.proximo = escolhido;
            }

            ultimo = escolhido;
        }
        if (esquerda != null) {
            ultimo.proximo = esquerda;
        } else {
            ultimo.proximo = direita;
        }
        return inicio;
    }
    private NoUsuarioOrdenado ordenarLista(NoUsuarioOrdenado inicio) {

        if (inicio == null || inicio.proximo == null) {
            return inicio;
        }
        int total = 0;
        NoUsuarioOrdenado atual = inicio;
        while (atual != null) {
            total++;
            atual = atual.proximo;
        }
        int metade = total / 2;
        atual = inicio;
        for (int indice = 1; indice < metade; indice++) {
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
    private void escreverCampoCsv(PrintWriter arquivo,char[] texto, int tamanho) {
        arquivo.print('"');
        if (texto != null) {
            for (int indice = 0; indice < tamanho; indice++) {
                if (texto[indice] == '"') {
                    arquivo.print('"');}
                arquivo.print(texto[indice]);}
        }
        arquivo.print('"');
    }
    public int gerarArquivoOrdenado(String caminho)
            throws IOException {
        PrintWriter arquivo = new PrintWriter(new FileWriter(caminho));
        int linhas = 0;
        arquivo.println("User_id,profileName,Title,media_livro,N_livro,nota_usuario");
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

            double media = livro.somaNotas / livro.quantidadeAvaliacoes;
            arquivo.print(media);
            arquivo.print(',');

            arquivo.print(livro.quantidadeAvaliacoes);
            arquivo.print(',');

            arquivo.println(avaliacao.maiorNota);

            linhas++;
            atual = atual.proximo;
        }
        arquivo.close();
        if (arquivo.checkError()) {
            throw new IOException("Erro ao gravar o CSV");
        }

        return linhas;
    }

    public int obterQuantidade() {
        return quantidade;
    }
}

