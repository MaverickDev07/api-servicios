package org.allivia.api.alliviaapi.repositories;

import org.allivia.api.alliviaapi.entities.AppUsuarioEntity;
import org.allivia.api.alliviaapi.entities.AppPacienteEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
public interface IUserRepository extends CrudRepository<AppUsuarioEntity,Long> {

    @Query(
            value = "select * from app_usuario where email = :email or usuario = :usuario",
            nativeQuery = true)
    AppUsuarioEntity findByValidateUserEmail(@Param("usuario") String usuario, @Param("email") String email);
    AppUsuarioEntity findByUsuario(String usuario);

    /* Add Gary */
    @Query(
            value = "select * from app_usuario where email = :email",
            nativeQuery = true)
    AppUsuarioEntity findByValidateUserByEmail(@Param("email") String email);
    AppUsuarioEntity findByEmail(String email);
    @Query(
            value = "select * from app_usuario where token = :token",
            nativeQuery = true)
    AppUsuarioEntity findByValidateUserByToken(@Param("token") String token);

    @Query(
            value = "select usuario.* FROM app_usuario usuario, app_doctor doctor WHERE usuario.usuario_id = doctor.usuario_id and usuario.nombre iLIKE %:usuario% AND usuario.estado='HABILITADO'",
            nativeQuery = true)
    List<AppUsuarioEntity> findByAllUsuario(@Param("usuario") String usuario);

    /* Add Gary */
    @Query(
            value = "select usuario.* FROM app_usuario usuario, app_doctor doctor WHERE usuario.usuario_id = doctor.usuario_id and usuario.email iLIKE %:email% AND usuario.estado='HABILITADO'",
            nativeQuery = true)
    List<AppUsuarioEntity> findByAllEmail(@Param("email") String email);
    @Query(
            value = "select usuario.* FROM app_usuario usuario, app_doctor doctor WHERE usuario.usuario_id = doctor.usuario_id and usuario.nombre iLIKE %:nombre% AND usuario.estado='HABILITADO'",
            nativeQuery = true)
    List<AppUsuarioEntity> findByAllNombre(@Param("nombre") String nombre);
    @Query(
            value = "select usuario.usuario_id, usuario.telefono FROM app_usuario usuario WHERE usuario.email = :email",
            nativeQuery = true)
    Map<String, String> findTelefonoByEmail(@Param("email") String email);
    @Query(
            value = "select usuario.usuario_id FROM app_usuario usuario, app_paciente paciente WHERE paciente.id = :id and paciente.usuario_id=usuario.usuario_id",
            nativeQuery = true)
    Long findIdUsuarioByIdPaciente(@Param("id") Long id);
    @Query(
            value = "select usuario.usuario_id FROM app_usuario usuario, app_doctor doctor WHERE doctor.id = :id and doctor.usuario_id=usuario.usuario_id",
            nativeQuery = true)
    Long findIdUsuarioByIdDoctor(@Param("id") Long id);
    @Query(
            value = "select pc.id FROM app_usuario us, app_paciente pc WHERE pc.usuario_id=us.usuario_id and us.usuario_id = :id",
            nativeQuery = true)
    Long findIdPacienteByIdUsuario(@Param("id") Long id);
    @Query(
            value = "select dc.id FROM app_usuario us, app_doctor dc WHERE dc.usuario_id=us.usuario_id and us.usuario_id = :id",
            nativeQuery = true)
    Long findIdDoctorByIdUsuario(@Param("id") Long id);
    @Query(
            value = "select us.* FROM app_usuario us INNER JOIN app_paciente pc on us.usuario_id = pc.usuario_id ORDER BY us.id DESC LIMIT 5",
            nativeQuery = true)
    List<AppUsuarioEntity> findLastUsers();


    @Query(
            value = "select usuario.* FROM app_usuario usuario, app_doctor doctor, app_doctoresespecialidades de WHERE usuario.usuario_id = doctor.usuario_id and doctor.id = de.id_doctor and de.id_especialidad = :idEspecialidad  and usuario.nombre iLIKE %:usuario% AND usuario.estado='HABILITADO'",
            nativeQuery = true)
    List<AppUsuarioEntity> findByAllUsuario(@Param("idEspecialidad") long idEspecialidad, @Param("usuario") String usuario);

    @Query(
            value = "select crypt(CAST(:password AS TEXT), gen_salt('bf', 8))",
            nativeQuery = true)
    String passwordCript(@Param("password") String password);

    @Query(
            value = "select pin_password = crypt(CAST(:password AS TEXT), pin_password) as authentication FROM app_usuario WHERE email=:email",
            nativeQuery = true)
    Boolean passwordAuthentication(@Param("email") String email, @Param("password") String password);

    AppUsuarioEntity findByUsuarioId(long id);
    AppUsuarioEntity findByNombrearchivo(String nombreArchivo);
    List<AppUsuarioEntity> findByNombreContains(String nombre);
}
