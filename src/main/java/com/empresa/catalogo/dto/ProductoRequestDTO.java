package com.empresa.catalogo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/**
 * DTO de entrada: datos que el cliente envía hacia la API.
 * Principio SRP: solo transporta y valida datos de entrada.
 * No expone la entidad interna ni campos sensibles.
 *
 * @author Andres Felipe Jimenez Ramirez
 */
public class ProductoRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Positive(message = "El precio debe ser mayor a cero")
    private Double precio;

    private String categoria;

    public ProductoRequestDTO() {}

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}
