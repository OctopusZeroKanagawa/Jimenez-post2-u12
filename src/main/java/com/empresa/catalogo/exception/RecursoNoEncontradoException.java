package com.empresa.catalogo.exception;

/**
 * Excepción lanzada cuando un recurso no es encontrado en la BD.
 * Capturada por GlobalExceptionHandler → retorna 404 Not Found.
 *
 * @author Andres Felipe Jimenez Ramirez
 */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String recurso, Long id) {
        super(recurso + " con id " + id + " no encontrado.");
    }

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
