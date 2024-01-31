package org.allivia.api.alliviaapi.repositories;



import org.allivia.api.alliviaapi.entities.AppAgendacitaEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface IAgendaCitaRepository extends CrudRepository<AppAgendacitaEntity,Long> {

    @Query(value = "select ag.* from app_agendacita ag INNER JOIN app_fichamedica af on ag.id = af.id_agendacita INNER JOIN app_fichamedicamentos afm ON af.id = afm.id_fichamedica WHERE ag.id_paciente = :id group by ag.id",nativeQuery = true)
    List<AppAgendacitaEntity> findConsultaPacienteRecetas(@Param("id") long id);

    @Query(value = "select ag.* from app_agendacita ag INNER JOIN app_fichamedica af on ag.id = af.id_agendacita INNER JOIN app_fichamedicamentos afm ON af.id = afm.id_fichamedica INNER JOIN app_doctor ad on ag.id_doctor= ad.id INNER JOIN app_usuario au on ad.usuario_id = au.usuario_id WHERE ag.id_paciente = :id AND au.nombre ilike %:nombreDoctor% group by ag.id ",nativeQuery = true)
    List<AppAgendacitaEntity> findConsultaPacienteRecetas(@Param("id") long id, @Param("nombreDoctor") String nombreDoctor);

    @Query(value = "select ag.* from app_agendacita ag WHERE ag.id_paciente = :id ORDER BY ag.fecha DESC",nativeQuery = true)
    List<AppAgendacitaEntity> findConsultaPaciente(@Param("id") long id);



    @Query(value = "select ag.* from app_agendacita ag WHERE ag.id_paciente=:id AND ag.fecha BETWEEN :fechaI and :fechaF ORDER BY ag.fecha DESC",nativeQuery = true)
    List<AppAgendacitaEntity> findConsultaPacienteFecha(@Param("id") long id, @Param("fechaI") String fechaI, @Param("fechaF") String fechaF);
    @Query(value = "select ag.* from app_agendacita ag WHERE ag.id_doctor=:id and ag.fecha BETWEEN :fechaI and :fechaF", nativeQuery = true)
    List<AppAgendacitaEntity> findConsultaDoctorFecha(@Param("id") long id, @Param("fechaI") String fechaI, @Param("fechaF") String fechaF);




    @Query(value = "select ag.* from app_agendacita ag WHERE ag.id_doctor = :id and fecha = :fecha",nativeQuery = true)
    List<AppAgendacitaEntity> validarHorarioAgendado(@Param("id") long id, @Param("fecha") String fecha);

//    @Query(value = "select ag.* from app_agendacita ag, app_pago p WHERE ag.id_pago=p.id and p.estado='Completado' and ag.id_paciente = :id",nativeQuery = true)
//    List<AppAgendacitaEntity> findConsultaPaciente(@Param("id") long id);


//    @Query(value = "select ag.* from app_agendacita ag, app_pago p WHERE ag.id_pago=p.id and p.estado='Completado' and ag.id_doctor = :id and fecha BETWEEN :fechaI and :fechaF",nativeQuery = true)
//    List<AppAgendacitaEntity> findConsultaDoctor(@Param("id") long id,@Param("fechaI") String fechaI, @Param("fechaF") String fechaF);

    @Query(value = "select ag.* from app_agendacita ag WHERE ag.id_doctor = :id and ag.fecha BETWEEN :fechaI and :fechaF",nativeQuery = true)
    List<AppAgendacitaEntity> findConsultaDoctor(@Param("id") long id,@Param("fechaI") String fechaI, @Param("fechaF") String fechaF);

//    @Query(value = "select ag.* from app_agendacita ag, app_pago p WHERE ag.id_pago=p.id and p.estado='Completado' and ag.id_doctor = :id and ag.estadoconsulta IN ('Pendiente','Confirmada')",nativeQuery = true)
//    List<AppAgendacitaEntity> findConsultaDoctor(@Param("id") long id);

    @Query(value = "select ag.* from app_agendacita ag WHERE ag.id_doctor = :id and ag.estadoconsulta IN ('Pendiente','Confirmada')",nativeQuery = true)
    List<AppAgendacitaEntity> findConsultaDoctor(@Param("id") long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE app_agendacita SET reconsulta=true WHERE id_paciente = :id",nativeQuery = true)
    void updateReconsultaDoctor(@Param("id") long id);

    @Query(value = "select aa.*  from app_agendacita aa inner join (select max(id) as id ,id_paciente   from app_agendacita aa  where aa.id_doctor = :id group by id_paciente) as mac on aa.id = mac.id and aa.reconsulta =false and aa.estadoconsulta = 'Finalizada' and aa.fecha BETWEEN :fechaI and :fechaF",nativeQuery = true)
    List<AppAgendacitaEntity> findConsultaDoctorReagendar(@Param("id") long id,@Param("fechaI") String fechaI, @Param("fechaF") String fechaF);

//    @Query(value = "select ag.fecha from app_agendacita ag, app_pago p WHERE ag.id_pago=p.id and p.estado='Completado' and ag.id_doctor = :id",nativeQuery = true)
//    List<String> findHorasAgendadasDoctor(@Param("id") long id);

    @Query(value = "select ag.fecha from app_agendacita ag WHERE ag.id_doctor = :id",nativeQuery = true)
    List<String> findHorasAgendadasDoctor(@Param("id") long id);

//    @Query(value = "select ag.* from app_agendacita ag, app_pago p WHERE ag.id_pago=p.id and p.estado='Completado' and ag.id_doctor = :id and ag.estadoconsulta IN ('Finalizada','Cancelada')",nativeQuery = true)
//    List<AppAgendacitaEntity> findHistorialConsultas(@Param("id") long id);

    @Query(value = "select ag.* from app_agendacita ag WHERE ag.id_doctor = :id and ag.estadoconsulta IN ('Finalizada','Cancelada','EnLlamada','PendienteLlenarFicha')",nativeQuery = true)
    List<AppAgendacitaEntity> findHistorialConsultas(@Param("id") long id);

    // Edit Gary: Obtener el último registro que de agendacita que no se completó de llenar
    @Query(value = "select ag.* from app_agendacita ag WHERE ag.id_doctor = :id and ag.estadoconsulta IN ('PendienteLlenarFicha') ORDER BY ag.id DESC LIMIT 1",nativeQuery = true)
    AppAgendacitaEntity findLastHistorialConsultas(@Param("id") long id);
    @Query(value = "select ag.* from app_agendacita ag WHERE ag.id = :id",nativeQuery = true)
    AppAgendacitaEntity findAgendaCitaById(@Param("id") long id);

    @Query(value = "select ag.* from app_agendacita ag WHERE ag.id_doctor = :id and ag.estadoconsulta IN ('En tratamiento','En programa')",nativeQuery = true)
    List<AppAgendacitaEntity> findAdminPacientes(@Param("id") long id);

//    @Query(value = "select ag.* from app_agendacita ag, app_pago p WHERE ag.id_pago=p.id and p.estado='Completado' and ag.id_doctor = :id and ag.estadoconsulta IN ('En tratamiento','En programa')",nativeQuery = true)
//    List<AppAgendacitaEntity> findAdminPacientes(@Param("id") long id);

//    @Query(value = "select ag.* from app_agendacita ag, app_pago p WHERE ag.id_pago=p.id and p.estado='Completado' and ag.id_doctor = :id and ag.estadoconsulta IN :estado",nativeQuery = true)
//    List<AppAgendacitaEntity> findHistorialAdminConsultas(@Param("id") long id, @Param("estado") List<String> estado);

    @Query(value = "select ag.* from app_agendacita ag WHERE ag.id_doctor = :id and ag.estadoconsulta IN :estado",nativeQuery = true)
    List<AppAgendacitaEntity> findHistorialAdminConsultas(@Param("id") long id, @Param("estado") List<String> estado);

//    @Query(value = "select ag.* from app_agendacita ag inner join app_doctor d on ag.id_doctor=d.id inner join app_usuario u on d.usuario_id = u.usuario_id inner join app_pago p on ag.id_pago = p.id  and p.estado='Completado' and ag.id_paciente = :id and u.nombre iLIKE %:nombre%",nativeQuery = true)
//    List<AppAgendacitaEntity> findConsultaPacienteDoctor(@Param("id") long id,@Param("nombre") String nombre);

    @Query(value = "select ag.* from app_agendacita ag inner join app_doctor d on ag.id_doctor=d.id inner join app_usuario u on d.usuario_id = u.usuario_id and ag.id_paciente = :id and u.nombre iLIKE %:nombre% ORDER BY ag.fecha DESC",nativeQuery = true)
    List<AppAgendacitaEntity> findConsultaPacienteDoctor(@Param("id") long id,@Param("nombre") String nombre);

    @Query(value = "select ag.* from app_agendacita ag inner join app_pago p on ag.id_pago=p.id inner join app_paciente ap on ag.id_paciente = ap.id inner join app_usuario au on ap.usuario_id = au.usuario_id and au.nombre ilike %:nombre% and ag.motivoconsulta ilike %:sintoma% and ag.id_doctor = :id", nativeQuery = true)
    List<AppAgendacitaEntity> findConsultaTodos(@Param("id") long id, @Param("sintoma") String sintoma, @Param("nombre") String nombre);


//    @Query(value = "select ag.* from app_agendacita ag, app_pago p WHERE ag.id_pago=p.id and p.estado='Completado' and ag.id_paciente = :id and ag.estadoconsulta IN :estado",nativeQuery = true)
//    List<AppAgendacitaEntity> findConsultasPaciente(@Param("id") long id, @Param("estado") List<String> estado);

    @Query(value = "select ag.* from app_agendacita ag WHERE ag.id_paciente = :id and ag.estadoconsulta IN :estado ORDER BY ag.fecha DESC",nativeQuery = true)
    List<AppAgendacitaEntity> findConsultasPaciente(@Param("id") long id, @Param("estado") List<String> estado);

//    @Query(value = "select ag.* from app_agendacita ag inner join app_doctor d on ag.id_doctor=d.id inner join app_usuario u on d.usuario_id = u.usuario_id inner join app_pago p on ag.id_pago = p.id  and p.estado='Completado' and ag.id_paciente = :id and u.nombre iLIKE %:nombre% and ag.estadoconsulta IN :estado", nativeQuery = true)
//    List<AppAgendacitaEntity> findConsultasPaciente(@Param("id") long id, @Param("estado") List<String> estado ,@Param("nombre") String nombre);

    @Query(value = "select ag.* from app_agendacita ag inner join app_doctor d on ag.id_doctor=d.id inner join app_usuario u on d.usuario_id = u.usuario_id and ag.id_paciente = :id and u.nombre iLIKE %:nombre% and ag.estadoconsulta IN :estado ORDER BY ag.fecha DESC", nativeQuery = true)
    List<AppAgendacitaEntity> findConsultasPaciente(@Param("id") long id, @Param("estado") List<String> estado ,@Param("nombre") String nombre);
    @Query(value = "select ag.* from app_agendacita ag WHERE ag.id_doctor = :idDoctor  and ag.estadoconsulta = 'Confirmada' and DATE( ag.fecha) = DATE(:p_fecha)", nativeQuery = true)
    List<AppAgendacitaEntity> findConsultaActual(@Param("idDoctor") long id,@Param("p_fecha") String fecha);
}
