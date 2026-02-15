package br.mycontacts.exceptions;

public class ContatoNaoEncontradoException extends RuntimeException {
    public ContatoNaoEncontradoException(String message) {
        super(message);
    }
}
