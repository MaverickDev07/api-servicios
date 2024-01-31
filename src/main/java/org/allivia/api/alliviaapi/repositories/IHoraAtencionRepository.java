package org.allivia.api.alliviaapi.repositories;



import org.allivia.api.alliviaapi.entities.AppHorarioatencionEntity;
import org.allivia.api.alliviaapi.entities.AppPaisEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface IHoraAtencionRepository extends CrudRepository<AppHorarioatencionEntity,Long> {

    @Query(value = "SELECT * FROM app_horarioatencion WHERE id_doctor=:idDoctor  AND estado=TRUE AND DATE(fecha) >= CURRENT_DATE",nativeQuery = true)
    List<AppHorarioatencionEntity> findByIdDoctor(@Param("idDoctor") long idDoctor);

    @Query(value = "SELECT id_dias FROM app_horarioatencion WHERE id_doctor=:idDoctor AND estado= TRUE GROUP BY id_dias ORDER BY id_dias ",nativeQuery = true)
    List<Integer> findByDiasIdDoctor(@Param("idDoctor") long idDoctor);

    @Query(value = "SELECT * FROM app_horarioatencion WHERE id_dias=:idDias AND id_doctor=:idDoctor and estado= TRUE AND DATE(fecha) >= CURRENT_DATE ORDER BY id_dias  ",nativeQuery = true)
    List<AppHorarioatencionEntity> findHorario(@Param("idDias") long idDias, @Param("idDoctor") long idDoctor);


    @Query(value = "SELECT * FROM app_horarioatencion WHERE id_doctor=:idDoctor AND fecha=:fecha AND estado=TRUE",nativeQuery = true)
    List<AppHorarioatencionEntity> findByFecha(@Param("idDoctor") long idDoctor, @Param("fecha") String fecha);


    @Query(value = "SELECT * FROM app_horarioatencion WHERE id_doctor=:idDoctor AND estado=TRUE",nativeQuery = true)
    List<AppHorarioatencionEntity> findByFecha(@Param("idDoctor") long idDoctor);



    AppHorarioatencionEntity findByFechaAndIdHorasAndIdDoctor(String fecha, long idHoras, long idDoctor);
}
