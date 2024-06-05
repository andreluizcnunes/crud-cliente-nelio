package br.com.api.cadastroCliente.services.exceptions;

public class ResourceNotFoundExcepetion extends RuntimeException {

    public ResourceNotFoundExcepetion(String message) {
        super(message);
    }
}
