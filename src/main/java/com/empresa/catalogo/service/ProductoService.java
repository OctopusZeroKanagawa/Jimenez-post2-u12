package com.empresa.catalogo.service;

import com.empresa.catalogo.dto.ProductoRequestDTO;
import com.empresa.catalogo.dto.ProductoResponseDTO;

import java.util.List;

/**
 * Interfaz del servicio de Producto.
 * Principio DIP: el controlador depende de esta abstracción,
 * no de la implementación concreta ProductoServiceImpl.
 * Esto permite cambiar la implementación sin modificar el controlador.
 *
 * @author Andres Felipe Jimenez Ramirez
 */
public interface ProductoService {

    /** Crea un nuevo producto y retorna su DTO de respuesta */
    ProductoResponseDTO crear(ProductoRequestDTO dto);

    /** Busca un producto por ID. Lanza RecursoNoEncontradoException si no existe */
    ProductoResponseDTO buscarPorId(Long id);

    /** Lista todos los productos activos */
    List<ProductoResponseDTO> listarActivos();

    /** Actualiza un producto existente */
    ProductoResponseDTO actualizar(Long id, ProductoRequestDTO dto);

    /** Elimina (desactiva) un producto por ID */
    void eliminar(Long id);
}
