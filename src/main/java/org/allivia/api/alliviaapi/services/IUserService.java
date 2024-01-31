package org.allivia.api.alliviaapi.services;

import com.voximplant.apiclient.ClientException;
import org.allivia.api.alliviaapi.entities.AppUsuarioEntity;
import org.allivia.api.alliviaapi.security.AlreadyExistsException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IUserService {
	AppUsuarioEntity save(AppUsuarioEntity userEntity) throws IOException, ClientException, AlreadyExistsException;
	AppUsuarioEntity saveOAUTH(AppUsuarioEntity userEntity) throws IOException, ClientException, AlreadyExistsException;
	Object findUserByEmailAndPassword(AppUsuarioEntity userEntity) throws Exception;
	AppUsuarioEntity update(AppUsuarioEntity userEntity);
	AppUsuarioEntity findById(long id);
	Object findByIdSuscripcion(long id) throws IOException;

	AppUsuarioEntity updatePassword(AppUsuarioEntity userEntity);

	List<AppUsuarioEntity> findByAllUser(String user);

	/*Add Gary*/
	List<AppUsuarioEntity> findByAllUserByNombre(String nombre);
	List<AppUsuarioEntity> findByAllUserByEmail(String email);
	Map<String, String> findTelefonoByEmail(String email);
	Long findIdUsuarioByIdPaciente(Long id);
	Long findIdUsuarioByIdDoctor(Long id);
	Long findIdPacienteByIdUsuario(Long id);
	Long findIdDoctorByIdUsuario(Long id);
	List<AppUsuarioEntity> findLastUsers();

	List<AppUsuarioEntity> findAll();

	Object getPrefijos();

	List<AppUsuarioEntity> findByNombreContains(String nombre);

	Map<String, String> sendSMS(String destinationNumber);

	Map<String, String> authenticationTelefono(String telefono, String codigo);

}
