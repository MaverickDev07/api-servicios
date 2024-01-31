package org.allivia.api.alliviaapi.services;

public interface IRespuestaService {
    Object findByRepuesta(long id);
    Object findByDiagnostico(String codigo);
}
