package com.empresa.catalogo.dto;

/**
 * DTO de salida: datos que la API retorna al cliente.
 * Principio SRP: solo transporta datos de respuesta.
 * No expone el campo "activo" ni detalles internos de la entidad.
 *
 * @author Andres Felipe Jimenez Ramirez
 */
public class ProductoResponseDTO {

    private Long id;
    private String nombre;
    private Double precio;
    private String categoria;

    public ProductoResponseDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}
