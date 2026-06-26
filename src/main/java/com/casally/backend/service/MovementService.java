package com.casally.backend.service;

import com.casally.backend.dto.MovementDTO;
import com.casally.backend.entity.CategoryEntity;
import com.casally.backend.entity.MovementEntity;
import com.casally.backend.mapper.MovementMapper;
import com.casally.backend.repository.CategoryRepository;
import com.casally.backend.repository.MovementRepository;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * Servicio que gestiona la lógica de negocio para el registro e histórico de movimientos financieros.
 * Asegura la integridad de las relaciones con las categorías y el aislamiento de datos por familia.
 *
 * @author Alberto Zapardiel Fernández
 * @version 1.0.0
 */
@Service
public class MovementService {

  /**
   * Identificador de inquilino (Tenant ID) simulado para el particionamiento de datos en desarrollo.
   */
  private static final String MOCK_TENANT_ID = "fede8214-home-4321-SaaS-tenant";

  /**
   * Identificador de usuario físico simulado que registra las transacciones del hogar.
   */
  private static final UUID MOCK_USER_ID = UUID.fromString("11111111-2222-3333-4444-555555555555");

  /**
   * Repositorio para las operaciones de base de datos de movimientos.
   */
  private final MovementRepository movementRepository;

  /**
   * Repositorio de categorías requerido para validar los enlaces de transacciones.
   */
  private final CategoryRepository categoryRepository;

  /**
   * Componente MapStruct para la conversión de datos financieros.
   */
  private final MovementMapper movementMapper;

  /**
   * Constructor para la inyección de los componentes de persistencia y conversión requeridos.
   *
   * @param movementRepository Repositorio de movimientos.
   * @param categoryRepository Repositorio de categorías.
   * @param movementMapper     Mapper de movimientos.
   */
  public MovementService(MovementRepository movementRepository,
                         CategoryRepository categoryRepository,
                         MovementMapper movementMapper) {
    this.movementRepository = movementRepository;
    this.categoryRepository = categoryRepository;
    this.movementMapper = movementMapper;
  }

  /**
   * Registra un nuevo movimiento de ingreso o gasto garantizando la preexistencia de su categoría.
   *
   * @param movementDTO Datos de la transacción de entrada.
   * @return {@link MovementDTO} guardado con su identificador UUID generado.
   * @throws ResponseStatusException Si el identificador de la categoría no corresponde a ningún registro.
   */
  @Transactional
  public MovementDTO createMovement(MovementDTO movementDTO) {
    CategoryEntity category = categoryRepository.findById(movementDTO.getCategoryId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La categoría especificada no existe"));

    MovementEntity entity = movementMapper.toEntity(movementDTO);
    entity.setCategory(category);
    entity.setTenantId(MOCK_TENANT_ID);
    entity.setUserId(MOCK_USER_ID);

    MovementEntity savedEntity = movementRepository.save(entity);
    return movementMapper.toDto(savedEntity);
  }

  /**
   * Recupera el histórico transaccional completo perteneciente en exclusiva a la familia activa.
   *
   * @return Lista de {@link MovementDTO} ordenada por el motor de persistencia.
   */
  @Transactional(readOnly = true)
  public List<MovementDTO> getMovements() {
    List<MovementEntity> entities = movementRepository.findByTenantId(MOCK_TENANT_ID);
    return movementMapper.toDtoList(entities);
  }
}