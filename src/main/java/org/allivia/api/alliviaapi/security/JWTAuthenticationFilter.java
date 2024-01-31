package org.allivia.api.alliviaapi.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.allivia.api.alliviaapi.entities.*;
import org.allivia.api.alliviaapi.repositories.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.w3c.dom.Text;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.allivia.api.alliviaapi.security.Constants.*;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final Logger logger = LogManager.getLogger(JWTAuthenticationFilter.class);
    private IPacienteRepository iPacienteRepository;
    private IUserRepository userRepository;
    private ISuscripcionRepository iSuscripcionRepository;
    private IControlSuscripcionServicioRepository iControlSuscripcionServicioRepository;
    private AuthenticationManager authenticationManager;
    private IDoctorRepository iDoctorRepository;
    private IEspecialidadDoctorRepository iEspecialidadDoctorRepository;
    private IEspecialidadesRepository iEspecialidadesRepository;
    private IAfiliacionesRepository iAfiliacionesRepository;
    private IAfiliacionesDoctorRepository iAfiliacionesDoctorRepository;
    private IHoraAtencionRepository iHoraAtencionRepository;
    private IDiasRepository iDiasRepository;
    private IHorasRepository iHorasRepository;
    private ISuscripcionPacienteRepository iSuscripcionPacienteRepository;


    private String idDevice;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, IUserRepository userRepository, ISuscripcionRepository iSuscripcionRepository, IControlSuscripcionServicioRepository iControlSuscripcionServicioRepository, IPacienteRepository iPacienteRepository, IDoctorRepository iDoctorRepository, IEspecialidadDoctorRepository iEspecialidadDoctorRepository, IEspecialidadesRepository iEspecialidadesRepository, IAfiliacionesRepository iAfiliacionesRepository, IAfiliacionesDoctorRepository iAfiliacionesDoctorRepository, IHoraAtencionRepository iHoraAtencionRepository, IDiasRepository iDiasRepository, IHorasRepository iHorasRepository, ISuscripcionPacienteRepository iSuscripcionPacienteRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.iSuscripcionRepository = iSuscripcionRepository;
        this.iControlSuscripcionServicioRepository = iControlSuscripcionServicioRepository;
        this.iPacienteRepository = iPacienteRepository;
        this.iDoctorRepository = iDoctorRepository;
        this.iEspecialidadDoctorRepository = iEspecialidadDoctorRepository;
        this.iEspecialidadesRepository = iEspecialidadesRepository;
        this.iAfiliacionesRepository = iAfiliacionesRepository;
        this.iAfiliacionesDoctorRepository = iAfiliacionesDoctorRepository;
        this.iHoraAtencionRepository = iHoraAtencionRepository;
        this.iDiasRepository = iDiasRepository;
        this.iHorasRepository = iHorasRepository;
        this.iSuscripcionPacienteRepository = iSuscripcionPacienteRepository;
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        logger.info("failed authentication while attempting to access " + failed.getClass());
        if (failed instanceof BadCredentialsException) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            HashMap<Object, Object> error = new HashMap<>();
            ObjectMapper mapper = new ObjectMapper();
            error.put("message", "credenciales invalidas");
            String data = mapper.writeValueAsString(error);
            response.getWriter().print(data);
            response.setHeader("Content-Type", "application/json");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //System.out.println("LOGIN JOO: " + request.getParameter("email") + " - " + request.getParameter("usuario") + " - " + request.getParameter("token") + " - " + request.getParameter("password"));
        AppUsuarioEntity appUsuarioEntity;
        String credential;
        if ( request.getParameter("token") != null ) {
            appUsuarioEntity = userRepository.findByValidateUserByToken(request.getParameter("token"));
            credential = request.getParameter("token");
        } else {
            appUsuarioEntity = userRepository.findByEmail(request.getParameter("email"));
            credential = request.getParameter("password");
        }
        String principal = appUsuarioEntity.getUsuario();
        idDevice = request.getParameter("idDevice");
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(principal, credential));
    }
//    public String responseBadCrendencial(){
//        HashMap<Object,Object> error = new HashMap<>();
//        ObjectMapper mapper = new ObjectMapper();
//        String data = "";
//        error.put("message", "credenciales invalidas");
//        try {
//            data = mapper.writeValueAsString(1);
//        } catch (JsonProcessingException jsonProcessingException) {
//            throw new AuthenticationServiceException(jsonProcessingException.getMessage());
//        }
//        try {
//            response.getWriter().print(data);
//        } catch (IOException ioException) {
//            throw new AuthenticationServiceException(ioException.getMessage());
//        }
//    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        try {
            User user = (User) auth.getPrincipal();
            /* Edit Gary*/
            System.out.println("LOGIN JOODD: " + user.toString());
            AppUsuarioEntity appUsuarioEntity = userRepository.findByUsuario(user.getUsername());
            String token = Constants.generateToken(((User) auth.getPrincipal()).getUsername());
            // appUsuarioEntity.setAccess_token(token);
            // AppUsuarioEntity appUsuarioEntity = userRepository.findByEmail(user.getUsername());

            ObjectMapper mapper = new ObjectMapper();
            String data;

            if (appUsuarioEntity.getEstado().equals("BLOQUEADO")) {
                HashMap<Object, Object> error = new HashMap<>();
                error.put("message", "El usuario se encuentra bloqueado");
                data = mapper.writeValueAsString(error);
                logger.error("El usuario se encuentra bloqueado" + appUsuarioEntity.getNombre());
            } else {
                if (idDevice != null && !idDevice.isEmpty()) {
                    if (appUsuarioEntity.getIdDevice() != null) {
                        if (!appUsuarioEntity.getIdDevice().equals(idDevice)) {
                            appUsuarioEntity.setIdDevice(idDevice);
                            userRepository.save(appUsuarioEntity);
                        }
                    } else {
                        appUsuarioEntity.setIdDevice(idDevice);
                        userRepository.save(appUsuarioEntity);
                    }
                }


                Boolean existeIdDevice = false;
                if (appUsuarioEntity.getDevices() != null) {
                    String[] devices = appUsuarioEntity.getDevices();
                    List<String> devicesList = new ArrayList<String>();
                    for (String device : devices) {
                        devicesList.add(device);
                        if ( device.equals(idDevice) ) {
                            existeIdDevice = true;
                        }
                    }
                    if( !existeIdDevice ) {
                        if (idDevice != null && !idDevice.isEmpty()) {
                            devicesList.add(idDevice);
                            String[] simpleArray = new String[ devicesList.size() ];
                            appUsuarioEntity.setDevices(devicesList.toArray(simpleArray));
                            userRepository.save(appUsuarioEntity);
                        }
                    }
                } else {
                    if (idDevice != null && !idDevice.isEmpty()) {
                        String[] devices = {idDevice};
                        appUsuarioEntity.setDevices(devices);
                        userRepository.save(appUsuarioEntity);
                    }
                }


                AppPacienteEntity appPacienteEntity = iPacienteRepository.findByUsuarioId(appUsuarioEntity.getUsuarioId());
                //System.out.println(appPacienteEntity.toString());

                if (appPacienteEntity != null) {

//                AppSuscripcionEntity appSuscripcionEntity = iSuscripcionRepository.findById(appPacienteEntity.getIdSuscripcion()).get();
//
//                List<AppControlsuscripcionservicioEntity> appControlsuscripcionservicioEntityList = iControlSuscripcionServicioRepository.findByIdPacienteSuscripcionActual(appPacienteEntity.getId());
//
                    appPacienteEntity.setPerfilPaciente(appUsuarioEntity);
                    AppSuscripcionpacienteEntity appSuscripcionpacienteEntity = iSuscripcionPacienteRepository.findByIdPacienteSuscripcion(appPacienteEntity.getId());
                    //System.out.println(appSuscripcionpacienteEntity.toString());
                    AppSuscripcionEntity appSuscripcionEntity = iSuscripcionRepository.findById(appSuscripcionpacienteEntity.getIdSuscripcion()).get();
                    //System.out.println(appSuscripcionEntity.toString());
                    appPacienteEntity.setSuscripcion(appSuscripcionEntity);
//                appPacienteEntity.setSuscripcion(appSuscripcionEntity);
//
//                appPacienteEntity.setServicios(appControlsuscripcionservicioEntityList);

                    data = mapper.writeValueAsString(appPacienteEntity);

                    logger.info("El paciente " + appUsuarioEntity.getNombre() + " inicio session exitosamente");

                } else {
                    AppDoctorEntity appDoctorEntity = iDoctorRepository.findByUsuarioId(appUsuarioEntity.getUsuarioId());

//                List<AppEspecialidadEntity> appEspecialidadEntities = new ArrayList<>();
//                List<AppAfiliacionesEntity> appAfiliacionesEntities = new ArrayList<>();
//                List<AppDoctoresespecialidadesEntity> listDoctorEspecialidad = iEspecialidadDoctorRepository.findByIdDoctor(appDoctorEntity.getId());

//                for (AppDoctoresespecialidadesEntity rowDetail : listDoctorEspecialidad) {
//                    AppEspecialidadEntity appEspecialidadEntity = iEspecialidadesRepository.findById(rowDetail.getIdEspecialidad()).get();
//                    appEspecialidadEntities.add(appEspecialidadEntity);
//                }

//                List<AppDoctorafiliacionesEntity> listAfiliaciones = iAfiliacionesDoctorRepository.findByIdDoctor(appDoctorEntity.getId());

//                for (AppDoctorafiliacionesEntity rowDetail : listAfiliaciones) {
//                    AppAfiliacionesEntity appAfiliacionesEntity = iAfiliacionesRepository.findById(rowDetail.getIdAfiliaciones()).get();
//                    appAfiliacionesEntities.add(appAfiliacionesEntity);
//                }


//                List<AppDiasEntity> appDiasEntityList = obtenerDiasHorasAtencion(appDoctorEntity.getId());

//                appDoctorEntity.setDiasAtencion(appDiasEntityList);
//                appDoctorEntity.setListEspecialidad(appEspecialidadEntities);
                    appDoctorEntity.setPerfilDoctor(appUsuarioEntity);
//                appDoctorEntity.setListAfiliaciones(appAfiliacionesEntities);
                    data = mapper.writeValueAsString(appDoctorEntity);
                    logger.info("El doctor " + appUsuarioEntity.getNombre() + " inicio session exitosamente");
                }
            }

            response.addHeader(HEADER_AUTHORIZACION_KEY, TOKEN_BEARER_PREFIX + " " + token);
            response.setHeader("Content-Type", "application/json");
            response.getWriter().print(data);

        } catch (Exception e) {
            logger.error("Error al intentar logearse", e);
        }
    }

//    public List<AppDiasEntity> obtenerDiasHorasAtencion(long id) {
//        List<AppDiasEntity> appDiasEntityList = new ArrayList<>();
//        List<Integer> diasDoctor = iHoraAtencionRepository.findByDiasIdDoctor(id);
//
//        for (Integer valor : diasDoctor) {
//            AppDiasEntity appDiasEntity = iDiasRepository.findById(Long.valueOf(valor)).get();
//            List<AppHorarioatencionEntity> appHorarioatencionEntityList = iHoraAtencionRepository.findByIdDias(valor);
//            List<AppHorasEntity> horasEntityList = new ArrayList<>();
//
//            for (AppHorarioatencionEntity horarioatencionEntity : appHorarioatencionEntityList) {
//                AppHorasEntity appHorasEntity = iHorasRepository.findById(horarioatencionEntity.getIdHoras()).get();
//                horasEntityList.add(appHorasEntity);
//            }
//            appDiasEntity.setHorasAtencion(horasEntityList);
//            appDiasEntityList.add(appDiasEntity);
//
//        }
//        return appDiasEntityList;
//    }
}
