package org.allivia.api.alliviaapi.repositories;


import org.allivia.api.alliviaapi.entities.AppDoctorEntity;
import org.allivia.api.alliviaapi.entities.AppPacienteEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IDoctorRepository extends CrudRepository<AppDoctorEntity,Long> {
    AppDoctorEntity findByUsuarioId( long usuarioId);

    /*@Query(value = "select d.* from app_doctor d, app_usuario u, app_doctoresespecialidades ade, app_especialidad e, app_horarioatencion ah  WHERE d.usuario_id = u.usuario_id AND ade.id_doctor = d.id AND ade.id_especialidad= e.id and ah.id_doctor = ade.id_doctor and ah.estado = true and DATE(ah.fecha) >= CURRENT_DATE AND u.estado= 'HABILITADO' GROUP by d.id",nativeQuery = true)
    List<AppDoctorEntity> getDoctoresActivos();


    @Query(value = "select d.* from app_doctor d, app_usuario u, app_doctoresespecialidades ade, app_especialidad e, app_horarioatencion ah  WHERE d.usuario_id = u.usuario_id AND ade.id_doctor = d.id AND ade.id_especialidad= e.id and ah.id_doctor = ade.id_doctor and ah.estado = true and DATE(ah.fecha) >= CURRENT_DATE AND e.descripcion iLIKE '%MEDICO GENERAL%' AND u.estado= 'HABILITADO' GROUP by d.id",nativeQuery = true)
    List<AppDoctorEntity> findAllMedicosGeneral();*/

    @Query(value = "select d.* from app_doctor d, app_usuario u, app_doctoresespecialidades ade, app_especialidad e WHERE d.usuario_id = u.usuario_id AND ade.id_doctor = d.id AND ade.id_especialidad= e.id AND u.estado= 'HABILITADO' AND u.revision=false GROUP by d.id",nativeQuery = true)
    List<AppDoctorEntity> getDoctoresActivos();

    @Query(value = "select d.* from app_doctor d, app_usuario u, app_doctoresespecialidades ade, app_especialidad e WHERE d.usuario_id = u.usuario_id AND ade.id_doctor = d.id AND ade.id_especialidad= e.id AND u.estado= 'HABILITADO' AND u.revision=false AND d.tipo_doctor=:tipo GROUP by d.id",nativeQuery = true)
    List<AppDoctorEntity> getDoctoresActivosTipo(String tipo);

    /*@Query(value = "select d.* from app_doctor d, app_usuario u, app_doctoresespecialidades ade, app_especialidad e WHERE d.usuario_id = u.usuario_id AND ade.id_doctor = d.id AND ade.id_especialidad= e.id AND e.descripcion iLIKE '%MEDICO GENERAL%' AND u.estado= 'HABILITADO' GROUP by d.id",nativeQuery = true)
    List<AppDoctorEntity> findAllMedicosGeneral();*/

    @Query(value = "select d.* from app_doctor d, app_usuario u, app_doctoresespecialidades ade, app_especialidad e, app_horarioatencion ah  WHERE d.usuario_id = u.usuario_id AND ade.id_doctor = d.id AND ade.id_especialidad= e.id and ah.id_doctor = ade.id_doctor and ah.estado = true and DATE(ah.fecha) >= CURRENT_DATE AND e.descripcion iLIKE '%MEDICO GENERAL%' AND u.estado= 'HABILITADO' AND u.revision=false GROUP by d.id",nativeQuery = true)
    List<AppDoctorEntity> findAllMedicosGeneral();

    @Query(value = "select d.* from app_doctor d, app_usuario u, app_doctoresespecialidades ade, app_especialidad e, app_horarioatencion ah  WHERE d.usuario_id = u.usuario_id AND ade.id_doctor = d.id AND ade.id_especialidad= e.id and ah.id_doctor = ade.id_doctor AND e.descripcion NOT iLIKE '%MEDICO GENERAL%' AND e.descripcion NOT iLIKE '%mg%' AND u.estado='HABILITADO' GROUP by d.id", nativeQuery = true)
    List<AppDoctorEntity> findAllMedicosEspecialistas();

    AppDoctorEntity findByNombrearchivo(String nombreArchivo);

}
