package com.casally.backend.repository;

import com.casally.backend.entity.MovementEntity;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio Spring Data JPA para la gestión de la persistencia de la entidad {@link MovementEntity}.
 * Maneja transacciones financieras identificadas mediante claves primarias de tipo {@link UUID}.
 *
 * @author Alberto Zapardiel Fernández
 * @version 1.0.0
 */
@Repository
public interface MovementRepository extends JpaRepository<MovementEntity, UUID> {

  /**
   * Recupera el histórico completo de movimientos financieros de un hogar específico.
   *
   * @param tenantId Identificador único del inquilino/hogar.
   * @return Lista de entidades {@link MovementEntity} ordenadas implícitamente por su persistencia.
   */
  List<MovementEntity> findByTenantId(String tenantId);
}