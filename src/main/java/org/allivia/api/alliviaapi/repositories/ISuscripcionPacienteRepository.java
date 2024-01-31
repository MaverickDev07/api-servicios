package org.allivia.api.alliviaapi.repositories;


import org.allivia.api.alliviaapi.entities.AppSuscripcionpacienteEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;




@Repository
public interface ISuscripcionPacienteRepository extends CrudRepository<AppSuscripcionpacienteEntity,Long> {

//    AppSuscripcionpacienteEntity findByIdPaciente(long idPaciente);

    @Query(value = "select ag.* from app_suscripcionpaciente ag WHERE estado = 'VIGENTE' AND id_paciente = :idPaciente",nativeQuery = true)
    AppSuscripcionpacienteEntity findByIdPacienteSuscripcion(@Param("idPaciente") long idPaciente);
}
