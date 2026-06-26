package com.casally.backend.controller;

import com.casally.backend.dto.CategoryDTO;
import com.casally.backend.service.CategoryService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST que implementa el contrato de la API de OpenAPI para la gestión de categorías.
 * Intercepta las solicitudes web y delega de manera directa en la capa de servicio empresarial.
 *
 * @author Alberto Zapardiel Fernández
 * @version 1.0.0
 */
@RestController
public class CategoriesController implements CategoriesApi {

  /**
   * Servicio de lógica de negocio encargado de la gestión integral de categorías.
   */
  private final CategoryService categoryService;

  /**
   * Constructor encargado de acoplar el servicio de negocio de manera inmutable.
   *
   * @param categoryService Servicio de categorías de la aplicación.
   */
  public CategoriesController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  /**
   * Endpoint para la creación de una nueva categoría en el sistema familiar.
   *
   * @param categoryDTO Estructura de datos validada de la categoría entrante.
   * @return {@link ResponseEntity} con la categoría creada y estado 201 (Created).
   */
  @Override
  public ResponseEntity<CategoryDTO> createCategory(CategoryDTO categoryDTO) {
    CategoryDTO created = categoryService.createCategory(categoryDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }

  /**
   * Endpoint para obtener el listado completo de las categorías financieras del hogar.
   *
   * @return {@link ResponseEntity} con la lista de categorías obtenida y estado 200 (OK).
   */
  @Override
  public ResponseEntity<List<CategoryDTO>> getCategories() {
    List<CategoryDTO> categories = categoryService.getCategories();
    return ResponseEntity.ok(categories);
  }
}