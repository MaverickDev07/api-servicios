package org.allivia.api.alliviaapi.repositories;



import org.allivia.api.alliviaapi.entities.AppControlconsultaservicioEntity;
import org.allivia.api.alliviaapi.entities.AppControlsuscripcionservicioEntity;
import org.allivia.api.alliviaapi.entities.AppEducacionEntity;
import org.allivia.api.alliviaapi.entities.AppSuscripcionservicioEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IControlSuscripcionServicioRepository extends CrudRepository<AppControlsuscripcionservicioEntity,Long> {

        @Query(value = "SELECT id_dias FROM app_controlsuscripcionservicio WHERE id_paciente=:idPaciente and id_servicio = :idServicio",nativeQuery = true)
        AppControlsuscripcionservicioEntity find(@Param("idPaciente") long idPaciente, @Param("idServicio") long idServicio);

        List<AppControlsuscripcionservicioEntity> findByIdPaciente(long idPaciente);

        @Query(value = "SELECT * FROM app_controlsuscripcionservicio WHERE id_paciente=:idPaciente and estado=TRUE",nativeQuery = true)
        List<AppControlsuscripcionservicioEntity> findByIdPacienteSuscripcionActual(@Param("idPaciente") long idPaciente);

        AppControlsuscripcionservicioEntity findByIdServicioAndIdPaciente(long idServicio, long idPaciente);


        @Query(value = "SELECT ap.* FROM app_controlsuscripcionservicio ap INNER JOIN  app_servicio w on ap.id_servicio = w.id WHERE ap.id_paciente = :idPaciente  and ap.estado = true and w.codigo = :tipo",nativeQuery = true)
        AppControlsuscripcionservicioEntity getControlSuscripcion(@Param("tipo") String tipo,@Param("idPaciente")  long idPaciente);

}
