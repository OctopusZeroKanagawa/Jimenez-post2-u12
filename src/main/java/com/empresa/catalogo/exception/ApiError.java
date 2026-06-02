package com.empresa.catalogo.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Estructura estandarizada para las respuestas de error de la API.
 * Todos los errores retornan este mismo formato JSON.
 *
 * Ejemplo de respuesta 404:
 * {
 *   "status": 404,
 *   "error": "Not Found",
 *   "mensaje": "Producto con id 999 no encontrado.",
 *   "timestamp": "2026-05-31T16:00:00",
 *   "path": "/api/productos/999"
 * }
 *
 * @author Andres Felipe Jimenez Ramirez
 */
public class ApiError {

    private int status;
    private String error;
    private String mensaje;
    private String timestamp;
    private String path;

    public ApiError(int status, String error, String mensaje, String path) {
        this.status = status;
        this.error = error;
        this.mensaje = mensaje;
        this.path = path;
        this.timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }

    public int getStatus() { return status; }
    public String getError() { return error; }
    public String getMensaje() { return mensaje; }
    public String getTimestamp() { return timestamp; }
    public String getPath() { return path; }
}
