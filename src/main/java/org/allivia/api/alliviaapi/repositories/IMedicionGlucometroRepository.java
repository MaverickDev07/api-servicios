package org.allivia.api.alliviaapi.repositories;

import org.allivia.api.alliviaapi.entities.AppMedicionGlucometroEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMedicionGlucometroRepository extends IBaseRepository<AppMedicionGlucometroEntity, Long> {
  @Query(
    value = "SELECT mg.* FROM app_medicion_glucometro mg INNER JOIN app_paciente pc ON mg.id_paciente=pc.id WHERE mg.id_paciente=:id ORDER BY mg.id DESC limit 1",
    nativeQuery = true)
  Optional<AppMedicionGlucometroEntity> findLastMedicionGlucometroByPaciente(@Param("id") Long id);

  @Query(
    value = "SELECT mg.* FROM app_medicion_glucometro mg INNER JOIN app_paciente pc ON mg.id_paciente=pc.id WHERE mg.id_paciente=:id AND mg.fecha LIKE %:fecha% ORDER BY mg.fecha DESC",
    nativeQuery = true)
  List<AppMedicionGlucometroEntity> listMedicionGlucometroByPacienteAndFecha(@Param("id") Long id, @Param("fecha") String fecha);
}
