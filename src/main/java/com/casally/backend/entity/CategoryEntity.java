package com.casally.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad JPA que representa una categoría de transacciones financieras en el sistema.
 * Permite clasificar los movimientos en diferentes conceptos (ej. Alimentación, Vivienda, Ocio).
 * <p>
 * Esta entidad soporta una arquitectura multi-tenant mediante el uso del campo tenantId,
 * garantizando que las categorías estén totalmente aisladas por cada hogar o suscripción familiar.
 * </p>
 *
 * @author Alberto Zapardiel Fernández
 * @version 1.0.0
 */
@Entity
@Table(name = "categories")
@Getter
@Setter
public class CategoryEntity {

  /**
   * Identificador único autoincremental de la categoría.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * Nombre descriptivo de la categoría (ej. "Mercadona", "Nómina").
   */
  @Column(nullable = false)
  private String name;

  /**
   * Tipo de categoría. Determina la naturaleza del flujo financiero.
   * Valores permitidos estrictos: INGRESO, GASTO.
   */
  @Column(nullable = false)
  private String type;

  /**
   * Identificador del inquilino (Tenant) o suscripción familiar.
   * Clave del aislamiento de datos en la arquitectura multi-tenant del SaaS.
   */
  @Column(name = "tenant_id", nullable = false)
  private String tenantId;

}
