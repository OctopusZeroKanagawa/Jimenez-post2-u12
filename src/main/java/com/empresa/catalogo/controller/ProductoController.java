package com.empresa.catalogo.controller;

import com.empresa.catalogo.dto.ProductoRequestDTO;
import com.empresa.catalogo.dto.ProductoResponseDTO;
import com.empresa.catalogo.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para el catálogo de productos.
 *
 * Principios SOLID aplicados:
 * - SRP: solo maneja el mapeo HTTP ↔ servicio, sin lógica de negocio.
 * - DIP: depende de ProductoService (interfaz), no de ProductoServiceImpl.
 *
 * El manejo de excepciones está centralizado en GlobalExceptionHandler —
 * este controlador NO tiene try/catch.
 *
 * @author Andres Felipe Jimenez Ramirez
 */
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService service;

    // DIP: inyecta la interfaz, Spring resuelve la implementación concreta
    public ProductoController(ProductoService service) {
        this.service = service;
    }

    /** GET /api/productos → lista todos los productos activos */
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarActivos());
    }

    /** GET /api/productos/{id} → busca producto por ID, 404 si no existe */
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    /** POST /api/productos → crea producto, 201 Created con DTO de respuesta */
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(
            @Valid @RequestBody ProductoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    /** PUT /api/productos/{id} → actualiza producto existente */
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProductoRequestDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    /** DELETE /api/productos/{id} → elimina producto, 204 No Content */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
