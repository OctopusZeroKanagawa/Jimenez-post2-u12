package com.empresa.catalogo.repository;

import com.empresa.catalogo.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio DAO para la entidad Producto.
 * Patrón DAO: abstrae el acceso a la base de datos.
 * El servicio nunca accede directamente a la BD, solo a través de esta interfaz.
 *
 * @author Andres Felipe Jimenez Ramirez
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    /** Consulta derivada: retorna solo productos con activo = true */
    List<Producto> findByActivoTrue();

    /** Consulta derivada: busca por categoría ignorando mayúsculas */
    List<Producto> findByCategoriaIgnoreCase(String categoria);
}
