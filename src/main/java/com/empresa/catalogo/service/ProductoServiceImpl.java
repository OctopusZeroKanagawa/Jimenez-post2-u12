package com.empresa.catalogo.service;

import com.empresa.catalogo.dto.ProductoRequestDTO;
import com.empresa.catalogo.dto.ProductoResponseDTO;
import com.empresa.catalogo.entity.Producto;
import com.empresa.catalogo.exception.RecursoNoEncontradoException;
import com.empresa.catalogo.factory.ProductoFactory;
import com.empresa.catalogo.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación de ProductoService.
 *
 * Principios SOLID aplicados:
 * - SRP: solo contiene lógica de negocio, sin acceso directo a BD ni construcción de objetos.
 * - DIP: depende de ProductoRepository (interfaz DAO) y ProductoFactory (componente),
 *        ambos inyectados por constructor, nunca instanciados directamente.
 *
 * @author Andres Felipe Jimenez Ramirez
 */
@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository repo;
    private final ProductoFactory factory;

    // Inyección por constructor (buena práctica, facilita testing)
    public ProductoServiceImpl(ProductoRepository repo, ProductoFactory factory) {
        this.repo = repo;
        this.factory = factory;
    }

    @Override
    @Transactional
    public ProductoResponseDTO crear(ProductoRequestDTO dto) {
        Producto p = factory.toEntity(dto);
        return factory.toResponseDTO(repo.save(p));
    }

    @Override
    public ProductoResponseDTO buscarPorId(Long id) {
        Producto p = repo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto", id));
        return factory.toResponseDTO(p);
    }

    @Override
    public List<ProductoResponseDTO> listarActivos() {
        return repo.findByActivoTrue().stream()
                .map(factory::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public ProductoResponseDTO actualizar(Long id, ProductoRequestDTO dto) {
        Producto existente = repo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto", id));

        existente.setNombre(dto.getNombre());
        existente.setPrecio(dto.getPrecio());
        existente.setCategoria(dto.getCategoria());

        return factory.toResponseDTO(repo.save(existente));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        buscarPorId(id); // verifica existencia antes de eliminar
        repo.deleteById(id);
    }
}
