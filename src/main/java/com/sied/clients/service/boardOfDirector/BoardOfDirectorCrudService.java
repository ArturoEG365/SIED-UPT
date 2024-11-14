package com.sied.clients.service.boardOfDirector;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.boardOfDirector.request.BoardOfDirectorCrudRequestDto;
import com.sied.clients.dto.boardOfDirector.request.BoardOfDirectorCrudUpdateRequestDto;
import com.sied.clients.dto.boardOfDirector.response.BoardOfDirectorCrudResponseDto;
import java.util.concurrent.CompletableFuture;

/**
 * Interfaz para el servicio CRUD de la entidad BoardOfDirector.
 * Define los métodos para crear, obtener, actualizar y eliminar registros de miembros de la junta directiva.
 * Todas las operaciones se ejecutan de manera asíncrona mediante CompletableFuture.
 */
public interface BoardOfDirectorCrudService {

    /**
     * Crea un nuevo registro de miembro de la junta directiva.
     *
     * @param request El DTO que contiene la información del miembro de la junta directiva a crear.
     * @return Un CompletableFuture que contiene el DTO de respuesta con los detalles del miembro creado.
     */
    CompletableFuture<BoardOfDirectorCrudResponseDto> create(BoardOfDirectorCrudRequestDto request);

    /**
     * Obtiene una lista paginada de todos los miembros de la junta directiva.
     *
     * @param offset El índice de inicio para la paginación.
     * @param limit La cantidad máxima de registros a devolver.
     * @return Un CompletableFuture que contiene la respuesta paginada con los DTOs de los miembros de la junta.
     */
    CompletableFuture<PaginatedResponse<BoardOfDirectorCrudResponseDto>> getAll(int offset, int limit);

    /**
     * Obtiene los detalles de un miembro de la junta directiva específico por su ID.
     *
     * @param id El ID del miembro de la junta directiva a obtener.
     * @return Un CompletableFuture que contiene el DTO de respuesta con los detalles del miembro.
     */
    CompletableFuture<BoardOfDirectorCrudResponseDto> get(Long id);

    /**
     * Actualiza los datos de un miembro de la junta directiva.
     *
     * @param request El DTO que contiene la información actualizada del miembro de la junta.
     * @return Un CompletableFuture que contiene el DTO de respuesta con los detalles del miembro actualizado.
     */
    CompletableFuture<BoardOfDirectorCrudResponseDto> update(BoardOfDirectorCrudUpdateRequestDto request);

    /**
     * Elimina un miembro de la junta directiva especificado por su ID.
     *
     * @param id El ID del miembro de la junta directiva a eliminar.
     * @return Un CompletableFuture que representa la finalización de la operación de eliminación.
     */
    CompletableFuture<Void> delete(Long id);
}
