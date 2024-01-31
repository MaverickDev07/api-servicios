package org.allivia.api.alliviaapi.repositories;

import org.allivia.api.alliviaapi.entities.AppMedicionBalanzaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMedicionBalanzaRepository extends IBaseRepository<AppMedicionBalanzaEntity, Long> {
  //@Query(value = "SELECT mb FROM AppMedicionBalanzaEntity mb INNER JOIN AppPacienteEntity p ON mb.paciente.id=p.id WHERE mb.paciente.id=:id ORDER BY mb.id DESC limit 1")
  @Query(
    value = "SELECT mb.* FROM app_medicion_balanza mb INNER JOIN app_paciente pc ON mb.id_paciente=pc.id WHERE mb.id_paciente=:id ORDER BY mb.id DESC limit 1",
    nativeQuery = true)
  Optional<AppMedicionBalanzaEntity> findLastMedicionBalanzaByPaciente(@Param("id") Long id);

  @Query(
    value = "SELECT mb.* FROM app_medicion_balanza mb INNER JOIN app_paciente pc ON mb.id_paciente=pc.id WHERE mb.id_paciente=:id AND mb.fecha LIKE %:fecha% ORDER BY mb.fecha DESC",
    nativeQuery = true)
  List<AppMedicionBalanzaEntity> listMedicionBalanzaByPacienteAndFecha(@Param("id") Long id, @Param("fecha") String fecha);
}
