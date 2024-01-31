package org.allivia.api.alliviaapi.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.voximplant.apiclient.ClientException;
import com.voximplant.apiclient.VoximplantAPIClient;
import com.voximplant.apiclient.request.*;
import com.voximplant.apiclient.response.AddUserResponse;
import com.voximplant.apiclient.response.GetUsersResponse;
import com.voximplant.apiclient.response.SendSmsMessageResponse;
import com.voximplant.apiclient.util.MultiArgument;
import org.allivia.api.alliviaapi.entities.*;
import org.allivia.api.alliviaapi.repositories.*;
import org.allivia.api.alliviaapi.security.AlreadyExistsException;
import org.allivia.api.alliviaapi.security.Constants;
import org.allivia.api.alliviaapi.security.JWTAuthenticationFilter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.lang.StringBuilder;

import static org.allivia.api.alliviaapi.security.Constants.HEADER_AUTHORIZACION_KEY;
import static org.allivia.api.alliviaapi.security.Constants.TOKEN_BEARER_PREFIX;

@Component
public class UserServiceImpl implements IUserService {
	public static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    /*@Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;*/

	@Autowired
	private IUserRepository userRepository;

    @Autowired
    private IPacienteRepository iPacienteRepository;

    @Autowired
    private ISuscripcionRepository iSuscripcionRepository;

    @Autowired
    private IControlSuscripcionServicioRepository iControlSuscripcionServicioRepository;

	@Autowired
	private IprefijoRepository iprefijoRepository;
	@Autowired
	private VoximplantAPIClient voximplantAPIClient;
	@Autowired
	private IAutenticacionTelefono autenticacionTelfRepository;
    @Autowired
    private ISuscripcionPacienteRepository iSuscripcionPacienteRepository;
    @Autowired
    private IDoctorRepository iDoctorRepository;

	@Value("${voximplant.application.id}")
	private long applicationId;
	@Value("${voximplant.sms.source.number}")
	private String smsSourceNumber;

    public AppUsuarioEntity save(AppUsuarioEntity userEntity) throws IOException, ClientException, AlreadyExistsException {
        // Edit Gary
        // AppUsuarioEntity usuarioEntityFind = userRepository.findByValidateUserEmail(userEntity.getUsuario(), userEntity.getEmail());
        AppUsuarioEntity usuarioEntityFind = userRepository.findByValidateUserByEmail(userEntity.getEmail());
        String username = userEntity.getEmail();
        username = username.replace("@", "");
        username = username.replace(".", "");
        // String username = userEntity.getEmail().split("@")[0];

        if (usuarioEntityFind == null) {
            AddUserRequest addUserRequest=new AddUserRequest();
            /*String[] arr = {"prueba"};
            userEntity.setDevices(arr);*/

            // Edit Gary
            userEntity.setUsuario(username);
            addUserRequest.setUserName(username);
            addUserRequest.setUserDisplayName(userEntity.getUsuario() + " " + userEntity.getUsuario());

            addUserRequest.setUserPassword(userEntity.getPinPassword());
            addUserRequest.setApplicationId(applicationId);

            String passwordCript = userRepository.passwordCript(userEntity.getPinPassword());
            userEntity.setCreado(new Timestamp(new Date().getTime()));
            userEntity.setEstado("BLOQUEADO");
//            userEntity.setGrupoId(2);
//            userEntity.setDescricpion("");
            userEntity.setTipoAutenticacionId(2);
            userEntity.setCreadoPor("APP ALLIVIA");
            userEntity.setPinPassword(passwordCript);

            AddUserResponse res = voximplantAPIClient.addUser(addUserRequest);
           // return userRepository.save(userEntity);
            if(!res.hasError()){
               logger.log(Level.INFO, "Guardado " + userEntity.toString());
               return userRepository.save(userEntity);
           }else{
                long code=res.getError().getCode();
                logger.log(Level.INFO, "VOXIMPLANT CODE: " + code+",MSG:"+res.getError().getMsg());
                if(code==118){
                   throw  new ClientException("El usuario ya esta siendo utilizado");
                }
                throw  new ClientException("Ocurrio un error interno. No se puede crear al usuario");
           }
        } else {
            // Edit Gary
            // throw  new AlreadyExistsException("El usuario o el email ya esta siendo utilizado");
            if (usuarioEntityFind.getDescricpion() == null) {
                return updatePassword(usuarioEntityFind);
            } else {
                throw  new AlreadyExistsException("El email ya esta siendo utilizado");
            }
        }
    }

    public AppUsuarioEntity saveOAUTH(AppUsuarioEntity userEntity) throws IOException, ClientException, AlreadyExistsException {
        // AppUsuarioEntity usuarioEntityFind = userRepository.findByValidateUserByToken(userEntity.getToken());
        AppUsuarioEntity usuarioEntityFind = userRepository.findByUsuario(userEntity.getUsuario());
        String username = userEntity.getUsuario();
        username = username.replace("@", "");
        username = username.replace(".", "");

        if (usuarioEntityFind == null) {
            AddUserRequest addUserRequest=new AddUserRequest();

            // Edit Gary
            userEntity.setUsuario(username);
            addUserRequest.setUserName(username);
            addUserRequest.setUserDisplayName(userEntity.getUsuario() + " " + userEntity.getUsuario());

            addUserRequest.setUserPassword(userEntity.getToken());
            addUserRequest.setApplicationId(applicationId);

            String passwordCript = userRepository.passwordCript(userEntity.getToken());
            userEntity.setCreado(new Timestamp(new Date().getTime()));
            userEntity.setEstado("BLOQUEADO");
            // 2: Facebook, 3: Google
            //userEntity.getTipoAutenticacionId()
            userEntity.setCreadoPor("APP ALLIVIA");
            userEntity.setPinPassword(passwordCript);

            // working
            GetUsersRequest req = new GetUsersRequest();
            req.setApplicationId(applicationId);
            req.setUserName(username);
            GetUsersResponse ress = voximplantAPIClient.getUsers(req);
            if (ress != null) {
                SetUserInfoRequest setUserInfoRequest = new SetUserInfoRequest();
                setUserInfoRequest.setUserName(username);
                setUserInfoRequest.setUserDisplayName(userEntity.getUsuario() + " " + userEntity.getUsuario());
                setUserInfoRequest.setApplicationId(applicationId);
                setUserInfoRequest.setUserPassword(userEntity.getToken());
                voximplantAPIClient.setUserInfo(setUserInfoRequest);

                /*DelUserRequest delUser = new DelUserRequest();
                MultiArgument<String> _username = MultiArgument<String>("dd");
                delUser.setUserName(immutableList);
                voximplantAPIClient.delUser()*/
                return userRepository.save(userEntity);
            } else {
                AddUserResponse res = voximplantAPIClient.addUser(addUserRequest);
                if(!res.hasError()) {
                    logger.log(Level.INFO, "Guardado " + userEntity.toString());
                    return userRepository.save(userEntity);
                } else {
                    long code=res.getError().getCode();
                    logger.log(Level.INFO, "VOXIMPLANT CODE: " + code+",MSG:"+res.getError().getMsg());
                    if(code == 118) {
                        throw  new ClientException("El usuario ya esta siendo utilizado");
                    }
                    throw  new ClientException("Ocurrio un error interno. No se puede crear al usuario");
                }
            }
        } else {
            if (usuarioEntityFind.getDescricpion() == null) {
                return usuarioEntityFind;
            } else {
                String passwordCript = userRepository.passwordCript(userEntity.getToken());
                usuarioEntityFind.setPinPassword(passwordCript);

                // working
                GetUsersRequest req = new GetUsersRequest();
                req.setApplicationId(applicationId);
                req.setUserName(username);
                GetUsersResponse ress = voximplantAPIClient.getUsers(req);
                if (ress != null) {
                    SetUserInfoRequest setUserInfoRequest = new SetUserInfoRequest();
                    setUserInfoRequest.setUserName(username);
                    setUserInfoRequest.setUserDisplayName(userEntity.getUsuario() + " " + userEntity.getUsuario());
                    setUserInfoRequest.setApplicationId(applicationId);
                    setUserInfoRequest.setUserPassword(userEntity.getToken());
                    voximplantAPIClient.setUserInfo(setUserInfoRequest);
                    return userRepository.save(usuarioEntityFind);
                }

                return userRepository.save(usuarioEntityFind);

            }
        }
    }

    public Object findUserByEmailAndPassword(AppUsuarioEntity userEntity) throws Exception {
        logger.log(Level.INFO, "Usuario " + userEntity.toString());
        AppUsuarioEntity usuarioEntityFind = userRepository.findByValidateUserByEmail(userEntity.getEmail());
        HashMap<Object, Object> error;
        HashMap<Object, Object> success = new HashMap<>();
        success.put("message", "Usuario encontrado correctamente");
        success.put("find", true);

        if (usuarioEntityFind != null) {
            if (usuarioEntityFind.getEstado().equals("BLOQUEADO")) {
                error = new HashMap<>();
                error.put("message", "El usuario se encuentra bloqueado");
                error.put("find", false);
                return error;
            } else {
                usuarioEntityFind.setPinPassword(userEntity.getPinPassword());
                if ( userRepository.passwordAuthentication(usuarioEntityFind.getEmail(), usuarioEntityFind.getPinPassword()) )
                    return success;
                else {
                    error = new HashMap<>();
                    error.put("message", "Error de Autenticación");
                    error.put("find", false);
                    return error;
                }
            }
        } else {
            error = new HashMap<>();
            error.put("message", "Usuario no encontrado");
            error.put("find", false);
            return error;
        }
    }

    public AppUsuarioEntity update(AppUsuarioEntity userEntity) {
        AppUsuarioEntity userEntityFind = userRepository.findById(userEntity.getUsuarioId()).get();
        userEntityFind.setNombre(userEntity.getNombre());
        userEntityFind.setApellido(userEntity.getApellido());
        userEntityFind.setCarnet(userEntity.getCarnet());
        userEntityFind.setFechaNacimiento(userEntity.getFechaNacimiento());
        // userEntityFind.setEmail(userEntity.getEmail());
        userEntityFind.setGenero(userEntity.getGenero());
        userEntityFind.setDireccion(userEntity.getDireccion());
        userEntityFind.setTelefono(userEntity.getTelefono());
        userEntityFind.setPath(userEntity.getPath());
        userEntityFind.setNombrearchivo(userEntity.getNombrearchivo());
        logger.log(Level.INFO, "Actualizacion del usuario  " + userEntityFind.toString());
        return userRepository.save(userEntityFind);
    }

    public AppUsuarioEntity findById(long id) {
        logger.log(Level.INFO, "Obtener el usuario por el id  " + id);
        AppUsuarioEntity appUsuarioEntity = userRepository.findById(id).get();
        logger.log(Level.INFO, "Resultado  " + appUsuarioEntity.toString());
        return appUsuarioEntity;
    }

    public Object findByIdSuscripcion(long id) throws IOException {
        logger.log(Level.INFO, "Obtener el usuario por el id: " + id + ", si aún cuenta con suscripción");
        AppUsuarioEntity appUsuarioEntity = userRepository.findById(id).get();
        String token = Constants.generateToken(appUsuarioEntity.getUsuario());
        appUsuarioEntity.setAccess_token(token);

        AppPacienteEntity appPacienteEntity = iPacienteRepository.findByUsuarioId(appUsuarioEntity.getUsuarioId());

        if (appPacienteEntity != null) {
            List<AppControlsuscripcionservicioEntity> appControlsuscripcionservicioEntityList = iControlSuscripcionServicioRepository.findByIdPacienteSuscripcionActual(appPacienteEntity.getId());
            AppSuscripcionpacienteEntity appSuscripcionpacienteEntity = iSuscripcionPacienteRepository.findByIdPacienteSuscripcion(appPacienteEntity.getId());
            AppSuscripcionEntity appSuscripcionEntity = iSuscripcionRepository.findById(appSuscripcionpacienteEntity.getIdSuscripcion()).get();
            appPacienteEntity.setPerfilPaciente(appUsuarioEntity);
            appPacienteEntity.setSuscripcion(appSuscripcionEntity);
            appPacienteEntity.setServicios(appControlsuscripcionservicioEntityList);
            appPacienteEntity.setSuscripcionPaciente(appSuscripcionpacienteEntity);

            return appPacienteEntity;
        } else {
            AppDoctorEntity appDoctorEntity = iDoctorRepository.findByUsuarioId(appUsuarioEntity.getUsuarioId());
            List<AppAfiliacionesEntity> appAfiliacionesEntities = new ArrayList<>();
            appDoctorEntity.setPerfilDoctor(appUsuarioEntity);
            appDoctorEntity.setListAfiliaciones(appAfiliacionesEntities);

            return appDoctorEntity;
        }
    }

    public List<AppUsuarioEntity> findByAllUser(String user){
        logger.log(Level.INFO, "Filtrado de busqueda por nombre usuario  " + user);
        List<AppUsuarioEntity> appUsuarioEntityList = userRepository.findByAllUsuario(user);
        logger.log(Level.INFO, "Resultado " + appUsuarioEntityList.toString());
        return appUsuarioEntityList;
    }

    // Add Gary
    public List<AppUsuarioEntity> findByAllUserByNombre(String nombre){
        logger.log(Level.INFO, "Filtrado de busqueda por nombre del usuario  " + nombre);
        List<AppUsuarioEntity> appUsuarioEntityList = userRepository.findByAllNombre(nombre);
        logger.log(Level.INFO, "Resultado " + appUsuarioEntityList.toString());
        return appUsuarioEntityList;
    }
    public List<AppUsuarioEntity> findByAllUserByEmail(String email){
        logger.log(Level.INFO, "Filtrado de busqueda por email del usuario  " + email);
        List<AppUsuarioEntity> appUsuarioEntityList = userRepository.findByAllEmail(email);
        logger.log(Level.INFO, "Resultado " + appUsuarioEntityList.toString());
        return appUsuarioEntityList;
    }
    public Map<String, String> findTelefonoByEmail(String email){
        Assert.notNull(email, "¡El email no puede ser nulo!");
        Map<String, String> appUsuarioEntity = userRepository.findTelefonoByEmail(email);

        logger.log(Level.INFO, "Filtrado de telefono busqueda por email del usuario  " + email);
        logger.log(Level.INFO, "Resultado " + appUsuarioEntity.toString());
        return appUsuarioEntity;
    }
    public Long findIdUsuarioByIdPaciente(Long id) {
        Long result = userRepository.findIdUsuarioByIdPaciente(id);

        logger.log(Level.INFO, "Filtrado de usuario busqueda por id de paciente  " + id);
        logger.log(Level.INFO, "Resultado " + result);
        return result;
    }
    public Long findIdUsuarioByIdDoctor(Long id) {
        Long result = userRepository.findIdUsuarioByIdDoctor(id);

        logger.log(Level.INFO, "Filtrado de usuario busqueda por id de doctor  " + id);
        logger.log(Level.INFO, "Resultado " + result);
        return result;
    }
    public Long findIdPacienteByIdUsuario(Long id) {
        Long result = userRepository.findIdPacienteByIdUsuario(id);

        logger.log(Level.INFO, "Filtrado de pacienteId busqueda por id de usuario " + id);
        logger.log(Level.INFO, "Resultado " + result);
        return result;
    }
    public Long findIdDoctorByIdUsuario(Long id) {
        Long result = userRepository.findIdDoctorByIdUsuario(id);

        logger.log(Level.INFO, "Filtrado de doctorId busqueda por id de usuario " + id);
        logger.log(Level.INFO, "Resultado " + result);
        return result;
    }
    public List<AppUsuarioEntity> findLastUsers() {
        logger.log(Level.INFO, "Obtener los últimos usuarios");
        List<AppUsuarioEntity> list = userRepository.findLastUsers();

        logger.log(Level.INFO, "Resultado findLastUsers: " + list.toString());
        return list;
    }



    public AppUsuarioEntity updatePassword(AppUsuarioEntity appUsuarioEntity) {
        AppUsuarioEntity usuarioEntityFind = userRepository.findByEmail(appUsuarioEntity.getEmail());
        AppUsuarioEntity findUsuario = userRepository.findById(usuarioEntityFind.getUsuarioId()).get();
        String passwordCript = userRepository.passwordCript(appUsuarioEntity.getPinPassword());
        findUsuario.setPinPassword(passwordCript);

        logger.log(Level.INFO, "Actualizacion de password nuevo " + appUsuarioEntity+", actual "+ findUsuario.toString());
        return userRepository.save(findUsuario);
    }


    public List<AppUsuarioEntity> findAll() {
        List<AppUsuarioEntity> result = new ArrayList<>();
        userRepository.findAll().forEach(result::add);
        logger.log(Level.INFO, "Obtener listado de usuarios " + result.toString());
        return result;
    }

    @Override
    public List<AppUsuarioEntity> findByNombreContains(String nombre) {
        logger.log(Level.INFO, "Obtener listado de usuarios que contenga el nombre " + nombre);
        List<AppUsuarioEntity> appUsuarioEntityList = userRepository.findByNombreContains(nombre);

        return  appUsuarioEntityList;
    }

    @Override
    public Map<String, String> sendSMS(String destinationNumber) {
        Map<String, String> result = new HashMap<>();
        try {
            destinationNumber = destinationNumber.trim();
            int randomNum = new Random().nextInt((9999 - 1000) + 1) + 1000;
            String codigo = String.valueOf(randomNum);
            logger.log(Level.INFO, "Send SMS source number: " + smsSourceNumber + ", destination number: "
                    + destinationNumber + ", sms: " + randomNum);
            SendSmsMessageResponse sendSmsMessageResponse = voximplantAPIClient
                    .sendSmsMessage(new SendSmsMessageRequest().setSource(smsSourceNumber)
                            .setDestination(destinationNumber).setSmsBody(codigo));
            if (sendSmsMessageResponse.hasResult()) {
                long resultSMS = sendSmsMessageResponse.getResult();
                logger.log(Level.INFO, "Result Send SMS: " + resultSMS);
                AppAutenticacionTelefonoEntity authTelf = autenticacionTelfRepository
                        .findByNroTelefono(destinationNumber);
                if (authTelf != null && authTelf.getId() > 0) {
                    authTelf.setCodigo(codigo);
                    authTelf.setFechaModificacion(new Timestamp(System.currentTimeMillis()));
                } else {
                    authTelf = AppAutenticacionTelefonoEntity.getEntity(Long.valueOf(0), destinationNumber, codigo);
                }
                autenticacionTelfRepository.save(authTelf);
                result.put("result", "OK");
            } else {
//				throw new IllegalArgumentException(sendSmsMessageResponse.getError().getMsg());
                logger.log(Level.WARN,
                        "No se pudo enviar SMS a numero: " + destinationNumber + ", ERRROR: "
                                + sendSmsMessageResponse.getError().getCode() + " "
                                + sendSmsMessageResponse.getError().getMsg());
                result.put("result", "ERROR");
            }
        } catch (IOException | ClientException e) {
            logger.log(Level.ERROR, "No se pudo enviar SMS al numero: " + destinationNumber);
            result.put("result", "ERROR");
        }
        return result;
    }

    @Override
    public Map<String, String> authenticationTelefono(String telefono, String codigo) {
        telefono = telefono.replace("+", "");
        logger.log(Level.INFO, "Autentiacion de telefono: " + telefono + ", codigo: " + codigo);
        AppAutenticacionTelefonoEntity authTelf = autenticacionTelfRepository.findByNroTelefono(telefono);
        Map<String, String> resultAuth = new HashMap<>();
        if (authTelf != null && authTelf.getId() > 0) {
            if (authTelf.getCodigo() != null && authTelf.getCodigo().equals(codigo)) {
                resultAuth.put("result", "OK");
            } else {
                resultAuth.put("result", "ERROR");
                resultAuth.put("message", "Codigo invalido");
            }
        } else {
            resultAuth.put("result", "ERROR");
            resultAuth.put("message", "Numero de telefono no valido");
        }
        return resultAuth;
    }


    public Object getPrefijos(){

        List<AppPrefijoEntity> appPrefijoEntityList = (List<AppPrefijoEntity>) iprefijoRepository.findAll();
        logger.log(Level.INFO, "Listado de prefijos " + appPrefijoEntityList.toString());
        return appPrefijoEntityList;
    }


}

