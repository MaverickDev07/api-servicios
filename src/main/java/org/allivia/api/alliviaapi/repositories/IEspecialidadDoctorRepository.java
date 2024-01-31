package org.allivia.api.alliviaapi.repositories;



import org.allivia.api.alliviaapi.entities.AppDoctoresespecialidadesEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IEspecialidadDoctorRepository extends CrudRepository<AppDoctoresespecialidadesEntity,Long> {
        //@Query(value = "select ade.* from app_doctor d, app_usuario u, app_doctoresespecialidades ade, app_especialidad e, app_horarioatencion ah  WHERE d.usuario_id = u.usuario_id AND ade.id_doctor = d.id AND ade.id_especialidad= e.id and ah.id_doctor = ade.id_doctor and ah.estado = true and DATE(ah.fecha) >= CURRENT_DATE AND u.estado= 'HABILITADO' AND ade.id_especialidad = :idEspecialidad GROUP by ade.id",nativeQuery = true)
        @Query(value = "select ade.* from app_doctor d, app_usuario u, app_doctoresespecialidades ade, app_especialidad e WHERE d.usuario_id = u.usuario_id AND ade.id_doctor = d.id AND ade.id_especialidad= e.id AND u.estado= 'HABILITADO' AND u.revision=false AND ade.id_especialidad = :idEspecialidad GROUP by ade.id",nativeQuery = true)
        List<AppDoctoresespecialidadesEntity> findByIdEspecialidad(@Param("idEspecialidad") long idEspecialidad);
        List<AppDoctoresespecialidadesEntity> findByIdDoctor(long idDoctor);
        void deleteByIdDoctor(long idDoctor);

}
