package org.allivia.api.alliviaapi.services;





public interface IEspecialidadesService {
    Object findAll();
    Object findById(long id);
    Object findByNombre(String descripcion);
}
