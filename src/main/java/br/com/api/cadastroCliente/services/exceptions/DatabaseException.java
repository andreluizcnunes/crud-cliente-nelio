package br.com.api.cadastroCliente.services.exceptions;

public class DatabaseException extends RuntimeException{

    public DatabaseException(String mensagem) {
        super(mensagem);
    }
}
