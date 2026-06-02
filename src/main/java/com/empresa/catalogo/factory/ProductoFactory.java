package com.empresa.catalogo.factory;

import com.empresa.catalogo.dto.ProductoRequestDTO;
import com.empresa.catalogo.dto.ProductoResponseDTO;
import com.empresa.catalogo.entity.Producto;
import org.springframework.stereotype.Component;

/**
 * Factory para la construcción y conversión de objetos Producto.
 * Principio SRP: única responsabilidad de mapear entre capas.
 * Principio DIP: el servicio depende de este componente inyectado,
 *               no construye los objetos directamente.
 *
 * @author Andres Felipe Jimenez Ramirez
 */
@Component
public class ProductoFactory {

    /**
     * Convierte un ProductoRequestDTO a entidad Producto.
     * Usado al crear o actualizar un producto.
     */
    public Producto toEntity(ProductoRequestDTO dto) {
        Producto p = new Producto();
        p.setNombre(dto.getNombre());
        p.setPrecio(dto.getPrecio());
        p.setCategoria(dto.getCategoria());
        return p;
    }

    /**
     * Convierte una entidad Producto a ProductoResponseDTO.
     * Garantiza que solo se exponen los campos públicos de la API.
     */
    public ProductoResponseDTO toResponseDTO(Producto p) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(p.getId());
        dto.setNombre(p.getNombre());
        dto.setPrecio(p.getPrecio());
        dto.setCategoria(p.getCategoria());
        return dto;
    }
}
