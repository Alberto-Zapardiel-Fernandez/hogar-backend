package com.casally.backend.service;

import com.casally.backend.dto.CategoryDTO;
import com.casally.backend.entity.CategoryEntity;
import com.casally.backend.mapper.CategoryMapper;
import com.casally.backend.repository.CategoryRepository;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio que gestiona la lógica de negocio asociada a las categorías financieras del SaaS.
 * Coordina las transformaciones de datos y las operaciones de persistencia bajo aislamiento multi-tenant.
 *
 * @author Alberto Zapardiel Fernández
 * @version 1.0.0
 */
@Service
public class CategoryService {

  /**
   * Identificador de inquilino (Tenant ID) simulado temporalmente para el desarrollo del flujo.
   */
  private static final String MOCK_TENANT_ID = "fede8214-home-4321-SaaS-tenant";

  /**
   * Repositorio para el acceso a los datos de las categorías en la base de datos.
   */
  private final CategoryRepository categoryRepository;

  /**
   * Componente MapStruct para la transformación bidireccional entre DTOs y entidades.
   */
  private final CategoryMapper categoryMapper;

  /**
   * Constructor único para la inyección de dependencias de la capa de persistencia y mapeo.
   *
   * @param categoryRepository Repositorio de categorías.
   * @param categoryMapper     Mapper de categorías.
   */
  public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
    this.categoryRepository = categoryRepository;
    this.categoryMapper = categoryMapper;
  }

  /**
   * Procesa la creación de una nueva categoría asignándole de forma segura el tenant correspondiente.
   *
   * @param categoryDTO Datos de la categoría provenientes de la API.
   * @return {@link CategoryDTO} con la categoría persistida e identificada.
   */
  @Transactional
  public CategoryDTO createCategory(CategoryDTO categoryDTO) {
    CategoryEntity entity = categoryMapper.toEntity(categoryDTO);
    entity.setTenantId(MOCK_TENANT_ID);

    CategoryEntity savedEntity = categoryRepository.save(entity);
    return categoryMapper.toDto(savedEntity);
  }

  /**
   * Recupera el listado completo de categorías financieras asociadas al hogar del inquilino actual.
   *
   * @return Lista de {@link CategoryDTO} listas para el transporte.
   */
  @Transactional(readOnly = true)
  public List<CategoryDTO> getCategories() {
    List<CategoryEntity> entities = categoryRepository.findByTenantId(MOCK_TENANT_ID);
    return categoryMapper.toDtoList(entities);
  }
}