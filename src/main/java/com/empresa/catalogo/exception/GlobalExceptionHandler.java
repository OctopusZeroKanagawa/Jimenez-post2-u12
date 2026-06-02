package com.empresa.catalogo.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * Manejador global de excepciones con @RestControllerAdvice.
 * Principio SRP: única responsabilidad de transformar excepciones en respuestas HTTP.
 * Centraliza el manejo de errores — ningún controlador necesita try/catch propio.
 *
 * Handlers implementados:
 * - RecursoNoEncontradoException  → 404 Not Found
 * - MethodArgumentNotValidException → 400 Bad Request (validaciones @Valid)
 * - Exception (genérica)          → 500 Internal Server Error
 *
 * @author Andres Felipe Jimenez Ramirez
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Captura RecursoNoEncontradoException → 404 Not Found.
     * Ejemplo: GET /api/productos/999 cuando el producto no existe.
     */
    @ExceptionHandler(RecursoNoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFound(RecursoNoEncontradoException ex,
                                   HttpServletRequest req) {
        return new ApiError(
                404,
                "Not Found",
                ex.getMessage(),
                req.getRequestURI()
        );
    }

    /**
     * Captura MethodArgumentNotValidException → 400 Bad Request.
     * Ejemplo: POST /api/productos con nombre vacío o precio negativo.
     * Concatena todos los errores de validación en un solo mensaje.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidation(MethodArgumentNotValidException ex,
                                     HttpServletRequest req) {
        String errores = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return new ApiError(
                400,
                "Bad Request",
                errores,
                req.getRequestURI()
        );
    }

    /**
     * Captura IllegalArgumentException → 400 Bad Request.
     * Ejemplo: lógica de negocio que rechaza un argumento inválido.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleIllegalArgument(IllegalArgumentException ex,
                                          HttpServletRequest req) {
        return new ApiError(
                400,
                "Bad Request",
                ex.getMessage(),
                req.getRequestURI()
        );
    }

    /**
     * Captura cualquier excepción no controlada → 500 Internal Server Error.
     * Mensaje genérico — no expone detalles internos al cliente.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleGeneral(Exception ex, HttpServletRequest req) {
        return new ApiError(
                500,
                "Internal Server Error",
                "Error inesperado. Contactar soporte.",
                req.getRequestURI()
        );
    }
}
