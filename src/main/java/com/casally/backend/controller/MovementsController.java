package com.casally.backend.controller;

import com.casally.backend.dto.MovementDTO;
import com.casally.backend.service.MovementService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST que expone los endpoints definidos por OpenAPI para el flujo transaccional de movimientos.
 * Delega el procesamiento analítico y las reglas de negocio a la capa de servicio dedicada.
 *
 * @author Alberto Zapardiel Fernández
 * @version 1.0.0
 */
@RestController
public class MovementsController implements MovementsApi {

  /**
   * Servicio de lógica de negocio encargado de procesar los ingresos y gastos de la plataforma.
   */
  private final MovementService movementService;

  /**
   * Constructor para la inyección del servicio de transacciones financieras.
   *
   * @param movementService Servicio de movimientos de la aplicación.
   */
  public MovementsController(MovementService movementService) {
    this.movementService = movementService;
  }

  /**
   * Endpoint para registrar un nuevo flujo financiero monetario en la cuenta familiar.
   *
   * @param movementDTO Estructura de transferencia de datos con el movimiento validado.
   * @return {@link ResponseEntity} con el registro finalizado y estado 201 (Created).
   */
  @Override
  public ResponseEntity<MovementDTO> createMovement(MovementDTO movementDTO) {
    MovementDTO created = movementService.createMovement(movementDTO);
    return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(created);
  }

  /**
   * Endpoint para consultar el histórico acumulado de ingresos y gastos de la cuenta del inquilino.
   *
   * @return {@link ResponseEntity} con el listado completo de transacciones y estado 200 (OK).
   */
  @Override
  public ResponseEntity<List<MovementDTO>> getMovements() {
    List<MovementDTO> movements = movementService.getMovements();
    return ResponseEntity.ok(movements);
  }
}