package com.sied.clients.service.reference;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.reference.request.ReferenceCrudRequestDto;
import com.sied.clients.dto.reference.request.ReferenceCrudUpdateRequestDto;
import com.sied.clients.dto.reference.response.ReferenceCrudResponseDto;

/**
 * Interfaz que define los métodos para realizar operaciones CRUD sobre las referencias.
 * Los métodos de esta interfaz se encargan de la creación, obtención, actualización, eliminación y listado de referencias.
 */
public interface ReferenceCrudService {

 /**
  * Crea una nueva referencia.
  *
  * @param request DTO que contiene los datos necesarios para crear la referencia.
  * @return Un objeto {@link ReferenceCrudResponseDto} con los datos de la referencia creada.
  */
 ReferenceCrudResponseDto create(ReferenceCrudRequestDto request);

 /**
  * Obtiene todas las referencias con paginación.
  *
  * @param offset El número de página para la paginación (por defecto 0).
  * @param limit El número máximo de resultados por página (por defecto 10).
  * @return Un objeto {@link PaginatedResponse} que contiene una lista de {@link ReferenceCrudResponseDto} con las referencias solicitadas.
  */
 PaginatedResponse<ReferenceCrudResponseDto> getAll(int offset, int limit);

 /**
  * Obtiene una referencia por su ID.
  *
  * @param id El ID de la referencia a obtener.
  * @return Un objeto {@link ReferenceCrudResponseDto} con los datos de la referencia solicitada.
  */
 ReferenceCrudResponseDto get(Long id);

 /**
  * Actualiza una referencia existente.
  *
  * @param request DTO que contiene los datos necesarios para actualizar la referencia.
  * @return Un objeto {@link ReferenceCrudResponseDto} con los datos de la referencia actualizada.
  */
 ReferenceCrudResponseDto update(ReferenceCrudUpdateRequestDto request);

 /**
  * Elimina una referencia por su ID.
  *
  * @param id El ID de la referencia a eliminar.
  */
 void delete(Long id);
}
