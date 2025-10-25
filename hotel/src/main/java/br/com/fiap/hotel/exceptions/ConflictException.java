package br.com.fiap.hotel.exceptions;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}