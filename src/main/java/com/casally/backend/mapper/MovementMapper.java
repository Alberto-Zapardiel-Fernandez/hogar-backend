package com.casally.backend.mapper;

import com.casally.backend.dto.MovementDTO;
import com.casally.backend.entity.MovementEntity;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Interfaz de MapStruct encargada del mapeo bidireccional entre {@link MovementDTO} y {@link MovementEntity}.
 * Resuelve la jerarquía de relaciones complejas traduciendo objetos relacionados a IDs planos de transporte.
 *
 * @author Alberto Zapardiel Fernández
 * @version 1.0.0
 */
@Mapper(componentModel = "spring")
public interface MovementMapper {

  /**
   * Convierte una entidad de movimiento en un DTO, extrayendo el ID numérico de la categoría relacionada.
   *
   * @param entity Entidad origen {@link MovementEntity}.
   * @return Objeto de transferencia {@link MovementDTO}.
   */
  @Mapping(source = "category.id", target = "categoryId")
  MovementDTO toDto(MovementEntity entity);

  /**
   * Convierte un DTO de entrada en una entidad JPA utilizable por Hibernate.
   * Mapea el identificador plano categoryId a la estructura de la entidad CategoryEntity.
   *
   * @param dto Objeto origen {@link MovementDTO}.
   * @return Entidad destino {@link MovementEntity}.
   */
  @Mapping(target = "category.id", source = "categoryId")
  @Mapping(target = "tenantId", ignore = true)
  MovementEntity toEntity(MovementDTO dto);

  /**
   * Convierte una lista de entidades de movimientos en una lista de DTOs para la API.
   *
   * @param entities Lista de entidades origen.
   * @return Lista de DTOs destino.
   */
  List<MovementDTO> toDtoList(List<MovementEntity> entities);
}