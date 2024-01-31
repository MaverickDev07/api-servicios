package org.allivia.api.alliviaapi.controllers;

import static org.allivia.api.alliviaapi.security.Constants.HEADER_AUTHORIZACION_KEY;
import static org.allivia.api.alliviaapi.security.Constants.TOKEN_BEARER_PREFIX;

import java.io.IOException;
import java.util.*;

import org.allivia.api.alliviaapi.entities.AppMailEntity;
import org.allivia.api.alliviaapi.entities.AppUsuarioEntity;
import org.allivia.api.alliviaapi.entities.AppPacienteEntity;
import org.allivia.api.alliviaapi.security.AlreadyExistsException;
import org.allivia.api.alliviaapi.security.Constants;
import org.allivia.api.alliviaapi.services.IUserService;
import org.allivia.api.alliviaapi.services.impl.MailServiceImpl;
import org.allivia.api.alliviaapi.services.impl.SendMailServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.voximplant.apiclient.ClientException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class UserController {
    public static final Logger logger = LogManager.getLogger(UserController.class);
    @Autowired
    private IUserService userService;
//	@Autowired
//	private VoximplantAPIClient voximplantAPIClient;

    @Autowired
    private SendMailServiceImpl mailService;

    @Autowired
    private MailServiceImpl emailServiceImpl;

    @GetMapping("/users")
    public ResponseEntity<List> users() {
        try {
            List result = userService.findAll();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping("/users/update")
    public ResponseEntity<Object> update(@RequestBody AppUsuarioEntity userEntity) {
        try {
            AppUsuarioEntity user = userService.update(userEntity);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            logger.error("Error al actualizar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PostMapping("/users/add")
    public ResponseEntity<Object> create(@RequestBody AppUsuarioEntity userEntity,
                                         @RequestHeader(value = "create_account", defaultValue = "false") boolean create_account)
            throws IOException, ClientException {
        HttpHeaders responseHeaders = new HttpHeaders();
        try {
            AppUsuarioEntity user = userService.save(userEntity);
            AppMailEntity email = new AppMailEntity();
            /* Edit Gary */
            String token = Constants.generateToken(user.getUsuario());
            // String token = Constants.generateToken(user.getEmail());

            email.setPara(user.getEmail());
            email.setMensaje("Bienvenido a Allivia, su cuenta fue creada exitosamente");
            email.setAsunto("Cuenta - Allivia");
            mailService.sendRegisterEmail(email.getPara(), email.getMensaje(), email.getAsunto());
            emailServiceImpl.save(email);

            responseHeaders.set(HEADER_AUTHORIZACION_KEY, TOKEN_BEARER_PREFIX + " " + token);
            return ResponseEntity.ok().headers(responseHeaders).body(user);
        } catch (AlreadyExistsException e) {
            logger.log(Level.ERROR, e);
            HashMap<String, Object> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (ClientException e) {
            logger.log(Level.ERROR, e);
            HashMap<String, Object> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            logger.log(Level.ERROR, e);
            HashMap<String, Object> error = new HashMap<>();
            error.put("mensaje", "Error interno");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @PostMapping("/users/add/oauth")
    public ResponseEntity<Object> createOAUTH(@RequestBody AppUsuarioEntity userEntity,
                                         @RequestHeader(value = "create_account", defaultValue = "false") boolean create_account)
            throws IOException, ClientException {
        HttpHeaders responseHeaders = new HttpHeaders();
        try {
            AppUsuarioEntity user = userService.saveOAUTH(userEntity);
            AppMailEntity email = new AppMailEntity();
            String token = Constants.generateToken(user.getUsuario());

            if (user.getEmail() != null) {
                email.setPara(user.getEmail());
                email.setMensaje("Bienvenido a Allivia, su cuenta fue creada exitosamente");
                email.setAsunto("Cuenta - Allivia");
                mailService.sendRegisterEmail(email.getPara(), email.getMensaje(), email.getAsunto());
                emailServiceImpl.save(email);
            }
            responseHeaders.set(HEADER_AUTHORIZACION_KEY, TOKEN_BEARER_PREFIX + " " + token);
            return ResponseEntity.ok().headers(responseHeaders).body(user);
        } catch (AlreadyExistsException e) {
            logger.log(Level.ERROR, e);
            HashMap<String, Object> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (ClientException e) {
            logger.log(Level.ERROR, e);
            HashMap<String, Object> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            logger.log(Level.ERROR, e);
            HashMap<String, Object> error = new HashMap<>();
            error.put("mensaje", "Error interno");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @PostMapping("/users/find")
    public ResponseEntity<Object> findUserByEmailAndPassword(@RequestBody AppUsuarioEntity userEntity) {
        try {
            Object findUser = userService.findUserByEmailAndPassword(userEntity);
            return ResponseEntity.ok(findUser);
        } catch (Exception e) {
            logger.error("Error al actualizar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/users/updatePassword")
    public ResponseEntity<Object> updatePassword(@RequestBody AppUsuarioEntity userEntity) {
        try {
            AppUsuarioEntity user = userService.updatePassword(userEntity);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            logger.error("Error al actualizar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/user/id/{id}")
    public ResponseEntity<Object> users(@PathVariable long id) {
        try {
            AppUsuarioEntity result = userService.findById(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/user/suscripcion/id/{id}")
    public ResponseEntity<Object> usersSuscripcion(@PathVariable long id) {
        try {
            Object result = userService.findByIdSuscripcion(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/user/nombre/{nombre}")
    public ResponseEntity<Object> users(@PathVariable String nombre) {
        try {
            /* Edit Gary */
            // List<AppUsuarioEntity> result = userService.findByAllUser(nombre);
            List<AppUsuarioEntity> result = userService.findByAllUserByNombre(nombre);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /* Add Gary */
    @GetMapping("/user/telefono")
    public ResponseEntity<Object> getTelefono(@RequestParam(defaultValue = "") String email) {
        try {
            Map<String, String> result = userService.findTelefonoByEmail(email);

            if (result == null || result.isEmpty() || Objects.isNull(result))
                return ResponseEntity.ok("{\"Error\": \"Email no registrado\"}");
            else
                return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/user/byIdPaciente")
    public ResponseEntity<Object> getUsuarioByIdPaciente(@RequestParam Long id) {
        try {
            Long result = userService.findIdUsuarioByIdPaciente(id);

            if (result == null || result == 0 || Objects.isNull(result))
                return ResponseEntity.ok("{\"Error\": \"Paciente con id: "+id+" no registrado\"}");
            else
                return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/users/ultimosUsuarios")
    public ResponseEntity<Object> lastUsers() {
        try {
            List<AppUsuarioEntity> result = userService.findLastUsers();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/prefijos")
    public ResponseEntity<Object> getPrefijos() {
        try {
            Object result = userService.getPrefijos();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/users/sendSMS")
    public ResponseEntity<Object> sendSMS(@RequestParam("smsDestinationNumber") String smsDestinationNumber) {
        try {
            //System.out.println(smsDestinationNumber);
            Map<String, String> resultSedSMS = userService.sendSMS(smsDestinationNumber);
            return ResponseEntity.ok(resultSedSMS);
        } catch (Exception e) {
            logger.error("Error al enviar notificacion por SMS", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/users/authTelefono")
    public ResponseEntity<Object> authTelefono(@RequestBody Map<String, String> params) {
        try {
            String telefono = params.get("telefono");
            String codigo = params.get("codigo");
            Map<String, String> resultAuth = userService.authenticationTelefono(telefono, codigo);
            return ResponseEntity.ok(resultAuth);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
