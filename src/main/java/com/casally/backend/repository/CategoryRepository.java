package com.casally.backend.repository;

import com.casally.backend.entity.CategoryEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio Spring Data JPA para la gestión de la persistencia de la entidad {@link CategoryEntity}.
 * Proporciona las operaciones CRUD estándar y consultas personalizadas filtradas por inquilino.
 *
 * @author Alberto Zapardiel Fernández
 * @version 1.0.0
 */
@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

  /**
   * Recupera todas las categorías asociadas a una suscripción familiar específica.
   * Garantiza el aislamiento de datos en el entorno multi-tenant.
   *
   * @param tenantId Identificador único del inquilino/hogar.
   * @return Lista de entidades de tipo {@link CategoryEntity} que pertenecen al inquilino.
   */
  List<CategoryEntity> findByTenantId(String tenantId);
}