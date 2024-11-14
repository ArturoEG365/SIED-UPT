package com.sied.clients.repository.boardOfDirector;

import com.sied.clients.entity.boardOfDirector.BoardOfDirector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad BoardOfDirector.
 * Proporciona métodos CRUD básicos para interactuar con la base de datos a través de JpaRepository.
 * Este repositorio hereda las funcionalidades estándar de JpaRepository, como guardar, buscar, actualizar y eliminar registros.
 */
@Repository
public interface BoardOfDirectorRepository extends JpaRepository<BoardOfDirector, Long> {
}
