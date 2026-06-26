package com.casally.backend.mapper;

import com.casally.backend.dto.CategoryDTO;
import com.casally.backend.entity.CategoryEntity;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Interfaz de MapStruct encargada de la transformación de datos entre el DTO de la API
 * {@link CategoryDTO} y la entidad de persistencia {@link CategoryEntity}.
 * <p>
 * Configurado como un componente de Spring para permitir su inyección directa de dependencias.
 * </p>
 *
 * @author Alberto Zapardiel Fernández
 * @version 1.0.0
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper {

  /**
   * Transforma una entidad de base de datos en su correspondiente DTO de transporte para la API.
   *
   * @param entity Entidad origen {@link CategoryEntity}.
   * @return Objeto destino {@link CategoryDTO}.
   */
  CategoryDTO toDto(CategoryEntity entity);

  /**
   * Transforma un DTO de la API en una entidad JPA para su posterior persistencia.
   * <p>
   * Se ignora explícitamente el campo tenantId en el mapeo inverso ya que este se asigna
   * de manera segura en la capa de negocio mediante el contexto de seguridad JWT.
   * </p>
   *
   * @param dto Objeto origen {@link CategoryDTO}.
   * @return Entidad destino {@link CategoryEntity}.
   */
  @Mapping(target = "tenantId", ignore = true)
  CategoryEntity toEntity(CategoryDTO dto);

  /**
   * Convierte una lista de entidades en una lista de DTOs listos para ser enviados por la red.
   *
   * @param entities Lista de entidades JPA.
   * @return Lista de DTOs de OpenAPI.
   */
  List<CategoryDTO> toDtoList(List<CategoryEntity> entities);
}