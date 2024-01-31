package org.allivia.api.alliviaapi.repositories;

import org.allivia.api.alliviaapi.entities.AppAutenticacionTelefonoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IAutenticacionTelefono extends JpaRepository<AppAutenticacionTelefonoEntity, Long> {

	AppAutenticacionTelefonoEntity findByNroTelefono(@Param("nroTelefono") String nroTelefono);

}
