package com.casally.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

/**
 * Entidad JPA que representa un movimiento financiero (un ingreso o un gasto) dentro del SaaS.
 * <p>
 * Utiliza identificadores de tipo {@link UUID} para mitigar riesgos de seguridad por enumeración
 * en los endpoints públicos y emplea {@link BigDecimal} para salvaguardar la precisión exacta
 * de los importes monetarios, evitando los errores de redondeo de los tipos flotantes nativos.
 * </p>
 *
 * @author Alberto Zapardiel Fernández
 * @version 1.0.0
 */
@Entity
@Table(name = "movements")
@Getter
@Setter
public class MovementEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Identificador único del movimiento generado mediante estrategia UUID.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  /**
   * Descripción o concepto específico de la transacción (ej. "Compra semanal").
   */
  @Column(nullable = false)
  private String description;

  /**
   * Importe monetario de la operación. Se define con precisión de 10 dígitos y 2 decimales.
   */
  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal amount;

  /**
   * Tipo de movimiento financiero. Valores permitidos: INGRESO, GASTO.
   */
  @Column(nullable = false)
  private String type;

  /**
   * Fecha en la que se ejecutó o registró la transacción.
   */
  @Column(nullable = false)
  private LocalDate date;

  /**
   * Categoría a la que pertenece este movimiento.
   * Se configura con carga perezosa (LAZY) para optimizar el rendimiento de las consultas.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  private CategoryEntity category;

  /**
   * Identificador del usuario físico dentro del hogar que registró el movimiento.
   */
  @Column(name = "user_id", nullable = false)
  private UUID userId;

  /**
   * Identificador del inquilino (Tenant) para garantizar el aislamiento de datos.
   */
  @Column(name = "tenant_id", nullable = false)
  private String tenantId;

}
