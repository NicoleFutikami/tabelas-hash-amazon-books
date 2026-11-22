public class NoUsuarioOrdenado {

    NoLista usuario;
    NoUsuarioOrdenado proximo;

    public NoUsuarioOrdenado(NoLista usuarioRecebido) {
        usuario = usuarioRecebido;
        proximo = null;
    }
}
