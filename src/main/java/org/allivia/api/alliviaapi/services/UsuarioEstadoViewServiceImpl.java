package org.allivia.api.alliviaapi.services;

import org.allivia.api.alliviaapi.entities.*;
import org.allivia.api.alliviaapi.repositories.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class UsuarioEstadoViewServiceImpl {
    public static final Logger logger = LogManager.getLogger(UsuarioEstadoViewServiceImpl.class);

    @Autowired
    private IUsuarioEstadoViewRepository iUsuarioEstadoViewRepository;
    @Autowired
    private IPacienteRepository iPacienteRepository;
    @Autowired
    private IFichaMedicaRepository iFichaMedicaRepository;



    public Object findAllUserView(int doctorID) {
        List<AppUsuarioEstadoViewEntity> list = iUsuarioEstadoViewRepository.getType(doctorID, "");
        logger.log(Level.INFO, "Result findAllUserView: " + list.toString());
        return list;
    }

    public List<AppUsuarioEstadoViewEntity> findByNombre(String nombre, int doctorID) {
        List<AppUsuarioEstadoViewEntity> list = iUsuarioEstadoViewRepository.getType(doctorID, nombre);
        logger.log(Level.INFO, "Result findByNombre: " + list.toString());
        return list;
    }

    public List<AppUsuarioEstadoViewEntity> findByEstado(List<String> estado, int doctorID) {
        List<AppUsuarioEstadoViewEntity> listAll = new ArrayList<>();
        List<AppUsuarioEstadoViewEntity> list = iUsuarioEstadoViewRepository.getType(doctorID, "");
        String eTratamiento = "";
        String ePrograma = "";
        if(estado != null){
            for(int i = 0;  i < estado.size(); i++){
                String state = estado.get(i).toUpperCase();
                if(state.equals("EN TRATAMIENTO")){
                    eTratamiento = state;
                }
                if(state.equals("EN PROGRAMA")){
                    ePrograma = state;
                }
            }
        }

        logger.log(Level.INFO, list.toString()+"SOLO POR ESTADO : tratamiento: "+eTratamiento+", programa: "+ePrograma);
        listAll = getListAll(list, eTratamiento, ePrograma);
        logger.log(Level.INFO, "Result findByEstado: " + listAll.toString());
        return listAll;
    }

    public List<AppUsuarioEstadoViewEntity> findByNombreAndEstado(String nombre, List<String> estado, int doctorID) {
        List<AppUsuarioEstadoViewEntity> listAll = new ArrayList<>();
        List<AppUsuarioEstadoViewEntity> list = iUsuarioEstadoViewRepository.getType(doctorID, nombre);
        String eTratamiento = "";
        String ePrograma = "";
        if(estado != null){
            for(int i = 0;  i < estado.size(); i++){
                String state = estado.get(i).toUpperCase();
                if(state.equals("EN TRATAMIENTO")){
                    eTratamiento = state;
                }
                if(state.equals("EN PROGRAMA")){
                    ePrograma = state;
                }
            }
        }
        logger.log(Level.INFO, "NOMBRE Y ESTADO : tratamiento: "+eTratamiento+", programa: "+ePrograma);
        listAll = getListAll(list, eTratamiento, ePrograma);
        logger.log(Level.INFO, "Result findByNombreAndEstado: " + listAll.toString());
        return listAll;
    }

    //METHOD GENERIC
    public List<AppUsuarioEstadoViewEntity> getListAll(List<AppUsuarioEstadoViewEntity> listGral, String eTratamiento, String ePrograma) {
        AppUsuarioEstadoViewEntity entity;
        List<AppUsuarioEstadoViewEntity> listAll = new ArrayList<>();
        if(eTratamiento.equals("EN TRATAMIENTO") && ePrograma.equals("EN PROGRAMA")){
            return (listGral==null)?listAll:listGral;
        } else if(eTratamiento.equals("EN TRATAMIENTO")){
            if(listGral != null)
               return  listGral.stream().filter(item->eTratamiento.equals(item.getEstado().toUpperCase())).collect(Collectors.toList());
        } else if (ePrograma.equals("EN PROGRAMA")) {
            if(listGral != null)
                return listGral.stream().filter(item->ePrograma.equals(item.getEstado().toUpperCase())).collect(Collectors.toList());
        }
        return listAll;
    }



    // public Object findAllUserView(int doctorID) {
    //     List<AppUsuarioEstadoViewEntity> list = new ArrayList<>();
    //     List<AppUsuarioEstadoViewEntity> appUsuarioEstadoViewEntitiesList = iUsuarioEstadoViewRepository.findAllUserView(doctorID);
    //     list = getListAll(appUsuarioEstadoViewEntitiesList);
    //     logger.log(Level.INFO, "Result findAllUserView: " + list.toString());
    //     return list;
    // }

    // public List<AppUsuarioEstadoViewEntity> findByNombre(String nombre, int doctorID) {
    //     List<AppUsuarioEstadoViewEntity> list = new ArrayList<>();
    //     List<AppUsuarioEstadoViewEntity>  appUsuarioEstadoViewEntitiesList = iUsuarioEstadoViewRepository.findByNombre(nombre, doctorID);
    //     list = getListAll(appUsuarioEstadoViewEntitiesList);
    //     logger.log(Level.INFO, "Result findByNombre: " + list.toString());
    //     return list;
    // }

    // public List<AppUsuarioEstadoViewEntity> findByEstado(List<String> estado, int doctorID) {
    //     List<AppUsuarioEstadoViewEntity> list = new ArrayList<>();
    //     String eTratamiento = "";
    //     String ePrograma = "";
    //     if(estado != null){
    //         for(int i = 0;  i < estado.size(); i++){
    //             String state = estado.get(i).toUpperCase();
    //             if(state.equals("EN TRATAMIENTO")){
    //                 eTratamiento = state;
    //             }
    //             if(state.equals("EN PROGRAMA")){
    //                 ePrograma = state;
    //             }
    //         }
    //     }
    //     logger.log(Level.INFO, "SOLO POR ESTADO : tratamiento: "+eTratamiento+", programa: "+ePrograma);
    //     if(eTratamiento.equals("EN TRATAMIENTO") && ePrograma.equals("EN PROGRAMA")){
    //         List<AppUsuarioEstadoViewEntity> appUsuarioEstadoViewEntitiesList = iUsuarioEstadoViewRepository.findByEstadoTratamientoPrograma(doctorID);
    //         list = getListAll(appUsuarioEstadoViewEntitiesList);
    //     } else if(eTratamiento.equals("EN TRATAMIENTO")){
    //         List<AppUsuarioEstadoViewEntity> appUsuarioEstadoViewEntitiesList = iUsuarioEstadoViewRepository.findByEstadoTratamiento(doctorID);
    //         list = getListAll(appUsuarioEstadoViewEntitiesList);
    //     } else if (ePrograma.equals("EN PROGRAMA")) {
    //         List<AppUsuarioEstadoViewEntity> appUsuarioEstadoViewEntitiesList = iUsuarioEstadoViewRepository.findByEstadoPrograma(doctorID);
    //         list = getListAll(appUsuarioEstadoViewEntitiesList);
    //     }
    //     logger.log(Level.INFO, "Result findByEstado: " + list.toString());
    //     return list;
    // }

    // public List<AppUsuarioEstadoViewEntity> findByNombreAndEstado(String nombre, List<String> estado, int doctorID) {
    //     List<AppUsuarioEstadoViewEntity> list = new ArrayList<>();
    //     String eTratamiento = "";
    //     String ePrograma = "";
    //     if(estado != null){
    //         for(int i = 0;  i < estado.size(); i++){
    //             String state = estado.get(i).toUpperCase();
    //             if(state.equals("EN TRATAMIENTO")){
    //                 eTratamiento = state;
    //             }
    //             if(state.equals("EN PROGRAMA")){
    //                 ePrograma = state;
    //             }
    //         }
    //     }
    //     logger.log(Level.INFO, "NOMBRE Y ESTADO : tratamiento: "+eTratamiento+", programa: "+ePrograma);
    //     if(eTratamiento.equals("EN TRATAMIENTO") && ePrograma.equals("EN PROGRAMA")){
    //         List<AppUsuarioEstadoViewEntity> appUsuarioEstadoViewEntitiesList = iUsuarioEstadoViewRepository.findByNombreAndEstadoTratamientoPrograma(nombre, doctorID);
    //         list = getListAll(appUsuarioEstadoViewEntitiesList);
    //     } else if(eTratamiento.equals("EN TRATAMIENTO")){
    //         List<AppUsuarioEstadoViewEntity> appUsuarioEstadoViewEntitiesList = iUsuarioEstadoViewRepository.findByNombreAndEstadoTratamiento(nombre, doctorID);
    //         list = getListAll(appUsuarioEstadoViewEntitiesList);
    //     } else if(ePrograma.equals("EN PROGRAMA")){
    //         List<AppUsuarioEstadoViewEntity> appUsuarioEstadoViewEntitiesList = iUsuarioEstadoViewRepository.findByNombreAndEstadoPrograma(nombre, doctorID);
    //         list = getListAll(appUsuarioEstadoViewEntitiesList);
    //     }
    //     logger.log(Level.INFO, "Result findByNombreAndEstado: " + list.toString());
    //     return list;
    // }

    // //METHOD GENERIC
    // public List<AppUsuarioEstadoViewEntity> getListAll(List<AppUsuarioEstadoViewEntity> listGral) {
    //     String data = "";
    //     int val1 = 0;
    //     int val2 = 0;
    //     List<AppUsuarioEstadoViewEntity> list = new ArrayList<>();
    //     for (AppUsuarioEstadoViewEntity itemUsuario : listGral) {
    //         AppUsuarioEstadoViewEntity entity = new AppUsuarioEstadoViewEntity();
    //         logger.log(Level.INFO, "==================== START QUERY ====================");
    //         logger.log(Level.INFO, "usuario_id: " + itemUsuario.getId() + ", nombre: " +itemUsuario.getNombre());
    //         boolean flag = true;
    //         entity.setId(itemUsuario.getId());
    //         entity.setNombre(itemUsuario.getNombre());
    //         List<AppFichamedicaEntity>  appFichamedicaEntitiesList = iFichaMedicaRepository.findByidProgramaFase((int) itemUsuario.getId());
    //         for (AppFichamedicaEntity itemFicha : appFichamedicaEntitiesList){
    //             val1 = (int) itemUsuario.getId();
    //             val2 = itemFicha.getIdProgramafase().intValue();
    //             logger.log(Level.INFO, "id_programafase: " +val2);
    //             if( itemFicha.getIdProgramafase() == 1) {
    //                 data = iUsuarioEstadoViewRepository.getTypeTreatment(val1, val2);
    //                 entity.setEstado(data);
    //             } else if (itemFicha.getIdProgramafase() > 1) {
    //                 data = iUsuarioEstadoViewRepository.getTypeTreatment(val1, val2);
    //                 entity.setEstado(data);
    //             }
    //             flag = false;
    //         }
    //         if(flag){
    //             entity.setEstado("");
    //         }
    //         logger.log(Level.INFO, "==================== END QUERY ====================");
    //         entity.setFechaNacimiento(itemUsuario.getFechaNacimiento());
    //         entity.setNombrearchivo(itemUsuario.getNombrearchivo());
    //         list.add(entity);
    //     }
    //     return list;
    // }
}