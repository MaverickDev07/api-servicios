package org.allivia.api.alliviaapi.repositories;

import org.allivia.api.alliviaapi.entities.AppFichamedicaEntity;
import org.allivia.api.alliviaapi.entities.AppUsuarioEstadoViewEntity; 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

public interface IUsuarioEstadoViewRepository extends CrudRepository<AppUsuarioEstadoViewEntity, Long> {

    // String queryPrimary = "select * from app_view_usuario avu inner join app_paciente ap on avu.usuario_id = ap.id ";
    // String querySecond = "inner join app_agendacita aa on ap.id = aa.id_paciente and aa.id_doctor = :doctorID " + 
    //                      "inner join app_fichamedica af on aa.id = af.id_agendacita " +
    //                      "inner join app_fichamedicamentos af2 on af.id = af2.id_fichamedica ";
    // String queryOrder = " order by avu.nombre asc";

    // // @Query(value = queryPrimary + "order by avu.nombre asc", nativeQuery = true)
    // @Query(value = queryPrimary + querySecond + queryOrder, nativeQuery = true)
    // List<AppUsuarioEstadoViewEntity>findAllUserView(@Param("doctorID") long doctorID);
    
    // // @Query(value = queryPrimary + "where avu.nombre iLIKE %:usuario%", nativeQuery = true)
    // @Query(value = queryPrimary + querySecond + "where avu.nombre iLIKE %:usuario%" + queryOrder, nativeQuery = true)
    // List<AppUsuarioEstadoViewEntity> findByNombre(@Param("usuario") String usuario, @Param("doctorID") long doctorID);

    // @Query(value = queryPrimary + querySecond + "where af.id_programafase = 1" + queryOrder, nativeQuery = true)
    // List<AppUsuarioEstadoViewEntity> findByEstadoTratamiento(@Param("doctorID") long doctorID);

    // @Query(value = queryPrimary + querySecond + "where af.id_programafase > 1" + queryOrder, nativeQuery = true)
    // List<AppUsuarioEstadoViewEntity> findByEstadoPrograma(@Param("doctorID") long doctorID);

    // @Query(value = queryPrimary + querySecond + "where af.id_programafase > 0" + queryOrder, nativeQuery = true)
    // List<AppUsuarioEstadoViewEntity> findByEstadoTratamientoPrograma(@Param("doctorID") long doctorID);

    // @Query(value = queryPrimary + querySecond + "where avu.nombre iLIKE %:usuario% and af.id_programafase = 1" + queryOrder, nativeQuery = true)
    // List<AppUsuarioEstadoViewEntity> findByNombreAndEstadoTratamiento(@Param("usuario") String usuario, @Param("doctorID") long doctorID);

    // @Query(value = queryPrimary + querySecond + "where avu.nombre iLIKE %:usuario% and af.id_programafase > 1" + queryOrder, nativeQuery = true)
    // List<AppUsuarioEstadoViewEntity> findByNombreAndEstadoPrograma(@Param("usuario") String usuario, @Param("doctorID") long doctorID);

    // @Query(value = queryPrimary + querySecond + "where avu.nombre iLIKE %:usuario% and af.id_programafase > 0" + queryOrder, nativeQuery = true)
    // List<AppUsuarioEstadoViewEntity> findByNombreAndEstadoTratamientoPrograma(@Param("usuario") String usuario, @Param("doctorID") long doctorID);

    // @Query(value = "select fn_usuario_tratamiento(:id_usuario, :id_programafase)", nativeQuery = true)
    // String getTypeTreatment(@Param("id_usuario") int id_usuario, @Param("id_programafase") int id_programafase);

    @Query(value = "select a.* ,row_number() over (order by a.nombre) as id  from pacientes(:id_usuario, :nombrePaciente) a", nativeQuery = true)
    List<AppUsuarioEstadoViewEntity> getType(@Param("id_usuario") int id_usuario, @Param("nombrePaciente") String nombrePaciente);

}