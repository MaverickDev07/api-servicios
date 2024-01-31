package org.allivia.api.alliviaapi.repositories;

import org.allivia.api.alliviaapi.entities.AppMedicionTensiometroEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMedicionTensiometroRepository extends IBaseRepository<AppMedicionTensiometroEntity, Long> {
  @Query(
    value = "SELECT mt.* FROM app_medicion_tensiometro mt INNER JOIN app_paciente pc ON mt.id_paciente=pc.id WHERE mt.id_paciente=:id ORDER BY mt.id DESC limit 1",
    nativeQuery = true)
  Optional<AppMedicionTensiometroEntity> findLastMedicionTensiometroByPaciente(@Param("id") Long id);

  @Query(
    value = "SELECT mt.* FROM app_medicion_tensiometro mt INNER JOIN app_paciente pc ON mt.id_paciente=pc.id WHERE mt.id_paciente=:id AND mt.fecha LIKE %:fecha% ORDER BY mt.fecha DESC",
    nativeQuery = true)
  List<AppMedicionTensiometroEntity> listMedicionTensiometroByPacienteAndFecha(@Param("id") Long id, @Param("fecha") String fecha);
}
