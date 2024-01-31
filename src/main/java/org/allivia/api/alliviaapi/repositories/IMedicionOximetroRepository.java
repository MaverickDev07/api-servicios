package org.allivia.api.alliviaapi.repositories;

import org.allivia.api.alliviaapi.entities.AppMedicionOximetroEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMedicionOximetroRepository extends IBaseRepository<AppMedicionOximetroEntity, Long> {
  @Query(
    value = "SELECT mo.* FROM app_medicion_oximetro mo INNER JOIN app_paciente pc ON mo.id_paciente=pc.id WHERE mo.id_paciente=:id ORDER BY mo.id DESC limit 1",
    nativeQuery = true)
  Optional<AppMedicionOximetroEntity> findLastMedicionOximetroByPaciente(@Param("id") Long id);

  @Query(
    value = "SELECT mo.* FROM app_medicion_oximetro mo INNER JOIN app_paciente pc ON mo.id_paciente=pc.id WHERE mo.id_paciente=:id AND mo.fecha LIKE %:fecha% ORDER BY mo.fecha DESC",
    nativeQuery = true)
  List<AppMedicionOximetroEntity> listMedicionOximetroByPacienteAndFecha(@Param("id") Long id, @Param("fecha") String fecha);
}
