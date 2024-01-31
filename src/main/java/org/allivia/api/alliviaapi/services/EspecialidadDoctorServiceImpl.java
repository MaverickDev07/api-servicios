package org.allivia.api.alliviaapi.services;


import org.allivia.api.alliviaapi.entities.*;
import org.allivia.api.alliviaapi.repositories.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Component
public class EspecialidadDoctorServiceImpl implements IEspecialidadDoctorService {
    public static final Logger logger = LogManager.getLogger(EspecialidadDoctorServiceImpl.class);
    @Autowired
    private IEspecialidadesRepository iEspecialidadesRepository;

    @Autowired
    private IEspecialidadDoctorRepository iEspecialidadDoctorRepository;

    @Autowired
    private IAfiliacionesRepository iAfiliacionesRepository;

    @Autowired
    private IAfiliacionesDoctorRepository iAfiliacionesDoctorRepository;

    @Autowired
    private IDoctorRepository iDoctorRepository;

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private IDiasRepository iDiasRepository;

    @Autowired
    private IHorasRepository iHorasRepository;

    @Autowired
    private IAgendaCitaRepository iAgendaCitaRepository;

    @Autowired
    private IHoraAtencionRepository iHoraAtencionRepository;


    public Object save(AppDoctoresespecialidadesEntity appDoctoresespecialidadesEntity) {
        logger.log(Level.INFO, "Guardado" + appDoctoresespecialidadesEntity.toString());
        return iEspecialidadDoctorRepository.save(appDoctoresespecialidadesEntity);
    }

    public Object findEspecialidadDoctor(long idEspecialidad) throws ParseException {
        // En caso de agendar cita par otros profecionales, por el momento se obtendrá los profecionales de Psicología
        if (idEspecialidad == 5) {
            idEspecialidad = 8;
        }

        if (idEspecialidad > 0) {
            List<AppDoctoresespecialidadesEntity> list = iEspecialidadDoctorRepository.findByIdEspecialidad(idEspecialidad);

            List<AppDoctorEntity> appDoctorEntities = new ArrayList<>();

            for (AppDoctoresespecialidadesEntity doctor : list) {
                Date fechaSistema = new Date();
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

//                String fechaHoy = dateFormat.format(fechaSistema);

                List<AppHorarioatencionEntity> appHorarioatencionEntityList = iHoraAtencionRepository.findByFecha(doctor.getIdDoctor());
                System.out.println("List " + appHorarioatencionEntityList.size());

                List<AppHorasEntity> horasEntityList = new ArrayList<>();
                List<String> appAgendacitaEntityListDoctor = iAgendaCitaRepository.findHorasAgendadasDoctor(doctor.getIdDoctor());
                for (AppHorarioatencionEntity row : appHorarioatencionEntityList) {
                    AppHorasEntity appHorasEntity = iHorasRepository.findById(row.getIdHoras()).get();
                    String[] hora = appHorasEntity.getDescripcion().split("-");

                    String fechaFinal = row.getFecha() + " " + hora[0].trim() + ":00";
                    SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date fechaHora = dateFormat2.parse(fechaFinal);
                    if (!appAgendacitaEntityListDoctor.contains(fechaFinal) && fechaHora.after(fechaSistema)) {
                        horasEntityList.add(appHorasEntity);
                    }
                }
                //if (horasEntityList.size() > 0) {
                    AppDoctorEntity appDoctorEntity = iDoctorRepository.findById(doctor.getIdDoctor()).get();
                    AppUsuarioEntity appUsuarioEntity = iUserRepository.findById(appDoctorEntity.getUsuarioId()).get();
                    List<AppEspecialidadEntity> appEspecialidadEntities = new ArrayList<>();
                    List<AppAfiliacionesEntity> appAfiliacionesEntities = new ArrayList<>();

                    List<AppDoctoresespecialidadesEntity> listDoctorEspecialidad = iEspecialidadDoctorRepository.findByIdDoctor(appDoctorEntity.getId());

                    for (AppDoctoresespecialidadesEntity rowDetail : listDoctorEspecialidad) {
                        if (rowDetail.getIdEspecialidad() == idEspecialidad) {
                            AppEspecialidadEntity especialidadEntity = iEspecialidadesRepository.findById(rowDetail.getIdEspecialidad()).get();
                            appEspecialidadEntities.add(especialidadEntity);
                        }
                    }


                    List<AppDoctorafiliacionesEntity> listAfiliaciones = iAfiliacionesDoctorRepository.findByIdDoctor(appDoctorEntity.getId());

                    for (AppDoctorafiliacionesEntity rowDetail : listAfiliaciones) {
                        AppAfiliacionesEntity appAfiliacionesEntity = iAfiliacionesRepository.findById(rowDetail.getIdAfiliaciones()).get();
                        appAfiliacionesEntities.add(appAfiliacionesEntity);
                    }

                    List<AppDiasEntity> appDiasEntityList = obtenerDiasHorasAtencion(appDoctorEntity.getId());
                    appDoctorEntity.setDiasAtencion(appDiasEntityList);
                    appDoctorEntity.setPerfilDoctor(appUsuarioEntity);
                    appDoctorEntity.setListEspecialidad(appEspecialidadEntities);
                    appDoctorEntity.setListAfiliaciones(appAfiliacionesEntities);
                    appDoctorEntities.add(appDoctorEntity);
                //}
            }
            logger.log(Level.INFO, "Listado de doctores por especialidad" + appDoctorEntities.toString());


            return appDoctorEntities;

        } else {
            List<AppDoctorEntity> appDoctorEntityList = iDoctorRepository.getDoctoresActivos();

            List<AppDoctorEntity> appDoctorEntityListData = new ArrayList<>();


            for (AppDoctorEntity doctor : appDoctorEntityList) {
                Date fechaSistema = new Date();
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//                String fechaHoy = dateFormat.format(fechaSistema);

                List<AppHorarioatencionEntity> appHorarioatencionEntityList = iHoraAtencionRepository.findByFecha(doctor.getId());
                System.out.println("List " + appHorarioatencionEntityList.size());

                List<AppHorasEntity> horasEntityList = new ArrayList<>();
                List<String> appAgendacitaEntityListDoctor = iAgendaCitaRepository.findHorasAgendadasDoctor(doctor.getId());
                for (AppHorarioatencionEntity row : appHorarioatencionEntityList) {
                    AppHorasEntity appHorasEntity = iHorasRepository.findById(row.getIdHoras()).get();
                    String[] hora = appHorasEntity.getDescripcion().split("-");
                    String fechaFinal = row.getFecha() + " " + hora[0].trim() + ":00";
                    SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date fechaHora = dateFormat2.parse(fechaFinal);
                    if (!appAgendacitaEntityListDoctor.contains(fechaFinal) && fechaHora.after(fechaSistema)) {
                        horasEntityList.add(appHorasEntity);
                    }
                }
                //if (horasEntityList.size() > 0) {
                    List<AppDiasEntity> appDiasEntityList = obtenerDiasHorasAtencion(doctor.getId());


                    List<AppDoctoresespecialidadesEntity> listEspecialidadDoctor = iEspecialidadDoctorRepository.findByIdDoctor(doctor.getId());


                    List<AppAfiliacionesEntity> appAfiliacionesEntities = new ArrayList<>();

                    List<AppDoctorafiliacionesEntity> listAfiliaciones = iAfiliacionesDoctorRepository.findByIdDoctor(doctor.getId());

                    for (AppDoctorafiliacionesEntity rowDetail : listAfiliaciones) {
                        AppAfiliacionesEntity appAfiliacionesEntity = iAfiliacionesRepository.findById(rowDetail.getIdAfiliaciones()).get();
                        appAfiliacionesEntities.add(appAfiliacionesEntity);
                    }


                    AppUsuarioEntity appUsuarioEntity = iUserRepository.findById(doctor.getUsuarioId()).get();


                    for (AppDoctoresespecialidadesEntity rowEspecialidadDoctor : listEspecialidadDoctor) {
                        AppDoctorEntity appDoctorEntity = new AppDoctorEntity();
                        List<AppEspecialidadEntity> appEspecialidadEntities = new ArrayList<>();
                        AppEspecialidadEntity especialidadEntity = iEspecialidadesRepository.findById(rowEspecialidadDoctor.getIdEspecialidad()).get();
                        appEspecialidadEntities.add(especialidadEntity);
                        appDoctorEntity.setId(doctor.getId());
                        appDoctorEntity.setUsuarioId(doctor.getUsuarioId());
                        appDoctorEntity.setDescripcion(doctor.getDescripcion());
                        appDoctorEntity.setPais(doctor.getPais());
                        appDoctorEntity.setPerfilDoctor(appUsuarioEntity);
                        appDoctorEntity.setListEspecialidad(appEspecialidadEntities);
                        appDoctorEntity.setListAfiliaciones(appAfiliacionesEntities);
                        appDoctorEntity.setDiasAtencion(appDiasEntityList);
                        appDoctorEntity.setRecomendacion(doctor.getRecomendacion());
                        appDoctorEntity.setBiografia(doctor.getBiografia());
                        appDoctorEntity.setPacientes(doctor.getPacientes());
                        appDoctorEntity.setExperiencias(doctor.getExperiencias());
                        appDoctorEntityListData.add(appDoctorEntity);
                    }
                //}
            }
            logger.log(Level.INFO, "Listado de doctores de todas las especialidades" + appDoctorEntityListData.toString());
            return appDoctorEntityListData;
        }
    }

    public Object findEspecialidadDoctorTipo(String tipo) throws ParseException {
        List<AppDoctorEntity> appDoctorEntityList = iDoctorRepository.getDoctoresActivosTipo(tipo);

        List<AppDoctorEntity> appDoctorEntityListData = new ArrayList<>();

        for (AppDoctorEntity doctor : appDoctorEntityList) {
            Date fechaSistema = new Date();
            List<AppHorarioatencionEntity> appHorarioatencionEntityList = iHoraAtencionRepository.findByFecha(doctor.getId());
            System.out.println("List " + appHorarioatencionEntityList.size());

            List<AppHorasEntity> horasEntityList = new ArrayList<>();
            List<String> appAgendacitaEntityListDoctor = iAgendaCitaRepository.findHorasAgendadasDoctor(doctor.getId());
            for (AppHorarioatencionEntity row : appHorarioatencionEntityList) {
                AppHorasEntity appHorasEntity = iHorasRepository.findById(row.getIdHoras()).get();
                String[] hora = appHorasEntity.getDescripcion().split("-");
                String fechaFinal = row.getFecha() + " " + hora[0].trim() + ":00";
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date fechaHora = dateFormat2.parse(fechaFinal);
                if (!appAgendacitaEntityListDoctor.contains(fechaFinal) && fechaHora.after(fechaSistema)) {
                    horasEntityList.add(appHorasEntity);
                }
            }
            List<AppDiasEntity> appDiasEntityList = obtenerDiasHorasAtencion(doctor.getId());


            List<AppDoctoresespecialidadesEntity> listEspecialidadDoctor = iEspecialidadDoctorRepository.findByIdDoctor(doctor.getId());


            List<AppAfiliacionesEntity> appAfiliacionesEntities = new ArrayList<>();

            List<AppDoctorafiliacionesEntity> listAfiliaciones = iAfiliacionesDoctorRepository.findByIdDoctor(doctor.getId());

            for (AppDoctorafiliacionesEntity rowDetail : listAfiliaciones) {
                AppAfiliacionesEntity appAfiliacionesEntity = iAfiliacionesRepository.findById(rowDetail.getIdAfiliaciones()).get();
                appAfiliacionesEntities.add(appAfiliacionesEntity);
            }


            AppUsuarioEntity appUsuarioEntity = iUserRepository.findById(doctor.getUsuarioId()).get();


            for (AppDoctoresespecialidadesEntity rowEspecialidadDoctor : listEspecialidadDoctor) {
                AppDoctorEntity appDoctorEntity = new AppDoctorEntity();
                List<AppEspecialidadEntity> appEspecialidadEntities = new ArrayList<>();
                AppEspecialidadEntity especialidadEntity = iEspecialidadesRepository.findById(rowEspecialidadDoctor.getIdEspecialidad()).get();
                appEspecialidadEntities.add(especialidadEntity);
                appDoctorEntity.setId(doctor.getId());
                appDoctorEntity.setUsuarioId(doctor.getUsuarioId());
                appDoctorEntity.setDescripcion(doctor.getDescripcion());
                appDoctorEntity.setPais(doctor.getPais());
                appDoctorEntity.setPerfilDoctor(appUsuarioEntity);
                appDoctorEntity.setListEspecialidad(appEspecialidadEntities);
                appDoctorEntity.setListAfiliaciones(appAfiliacionesEntities);
                appDoctorEntity.setDiasAtencion(appDiasEntityList);
                appDoctorEntity.setRecomendacion(doctor.getRecomendacion());
                appDoctorEntity.setBiografia(doctor.getBiografia());
                appDoctorEntity.setPacientes(doctor.getPacientes());
                appDoctorEntity.setExperiencias(doctor.getExperiencias());
                appDoctorEntityListData.add(appDoctorEntity);
            }
        }
        logger.log(Level.INFO, "Listado de doctores de todas las especialidades" + appDoctorEntityListData.toString());
        return appDoctorEntityListData;
    }

    public Object findDoctorEspecialidad(String nombre) throws ParseException {

        logger.log(Level.INFO, "Busqueda por nombre del doctor " + nombre.toString());

        List<AppUsuarioEntity> usuarioEntityList = iUserRepository.findByAllUsuario(nombre);

        List<AppDoctorEntity> appDoctorEntityList = new ArrayList<>();


        List<AppEspecialidadEntity> appEspecialidadEntities = new ArrayList<>();
        List<AppAfiliacionesEntity> appAfiliacionesEntities = new ArrayList<>();
        for (AppUsuarioEntity row : usuarioEntityList) {

            AppDoctorEntity appDoctorEntity = iDoctorRepository.findByUsuarioId(row.getUsuarioId());

            if (appDoctorEntity != null) {

                Date fechaSistema = new Date();
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

//                String fechaHoy = dateFormat.format(fechaSistema);

                List<AppHorarioatencionEntity> appHorarioatencionEntityList = iHoraAtencionRepository.findByFecha(appDoctorEntity.getId());
                System.out.println("List " + appHorarioatencionEntityList.size());

                List<AppHorasEntity> horasEntityList = new ArrayList<>();
                List<String> appAgendacitaEntityListDoctor = iAgendaCitaRepository.findHorasAgendadasDoctor(appDoctorEntity.getId());
                for (AppHorarioatencionEntity row2 : appHorarioatencionEntityList) {
                    AppHorasEntity appHorasEntity = iHorasRepository.findById(row2.getIdHoras()).get();
                    String[] hora = appHorasEntity.getDescripcion().split("-");
                    String fechaFinal = row2.getFecha() + " " + hora[0].trim() + ":00";
                    SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date fechaHora = dateFormat2.parse(fechaFinal);
                    if (!appAgendacitaEntityListDoctor.contains(fechaFinal) && fechaHora.after(fechaSistema)) {
                        horasEntityList.add(appHorasEntity);
                    }
                }
                //if (horasEntityList.size() > 0) {
                    List<AppDoctoresespecialidadesEntity> listDoctorEspecialidad = iEspecialidadDoctorRepository.findByIdDoctor(appDoctorEntity.getId());

                    for (AppDoctoresespecialidadesEntity rowDetail : listDoctorEspecialidad) {
                        AppEspecialidadEntity appEspecialidadEntity = iEspecialidadesRepository.findById(rowDetail.getIdEspecialidad()).get();
                        appEspecialidadEntities.add(appEspecialidadEntity);
                    }

                    List<AppDoctorafiliacionesEntity> listAfiliaciones = iAfiliacionesDoctorRepository.findByIdDoctor(appDoctorEntity.getId());

                    for (AppDoctorafiliacionesEntity rowDetail : listAfiliaciones) {
                        AppAfiliacionesEntity appAfiliacionesEntity = iAfiliacionesRepository.findById(rowDetail.getIdAfiliaciones()).get();
                        appAfiliacionesEntities.add(appAfiliacionesEntity);
                    }


                    List<AppDiasEntity> appDiasEntityList = obtenerDiasHorasAtencion(appDoctorEntity.getId());

                    appDoctorEntity.setDiasAtencion(appDiasEntityList);
                    appDoctorEntity.setListEspecialidad(appEspecialidadEntities);
                    appDoctorEntity.setPerfilDoctor(row);
                    appDoctorEntity.setListAfiliaciones(appAfiliacionesEntities);
                    appDoctorEntityList.add(appDoctorEntity);
                //}
            }
        }
        logger.log(Level.INFO, "Listado de doctores por filtro de busqueda por nombre" + appDoctorEntityList.toString());
        return appDoctorEntityList;
    }

    public Object findDoctorEspecialidad(long idEspecialidad, String nombre) {

        logger.log(Level.INFO, "Busqueda por nombre del doctor " + nombre.toString());

        List<AppUsuarioEntity> usuarioEntityList = iUserRepository.findByAllUsuario(idEspecialidad, nombre);

        List<AppDoctorEntity> appDoctorEntityList = new ArrayList<>();


        for (AppUsuarioEntity row : usuarioEntityList) {
            List<AppEspecialidadEntity> appEspecialidadEntities = new ArrayList<>();
            List<AppAfiliacionesEntity> appAfiliacionesEntities = new ArrayList<>();
            AppDoctorEntity appDoctorEntity = iDoctorRepository.findByUsuarioId(row.getUsuarioId());

            if (appDoctorEntity != null) {
                List<AppDoctoresespecialidadesEntity> listDoctorEspecialidad = iEspecialidadDoctorRepository.findByIdDoctor(appDoctorEntity.getId());

                for (AppDoctoresespecialidadesEntity rowDetail : listDoctorEspecialidad) {
                    AppEspecialidadEntity appEspecialidadEntity = iEspecialidadesRepository.findById(rowDetail.getIdEspecialidad()).get();
                    appEspecialidadEntities.add(appEspecialidadEntity);
                }

                List<AppDoctorafiliacionesEntity> listAfiliaciones = iAfiliacionesDoctorRepository.findByIdDoctor(appDoctorEntity.getId());

                for (AppDoctorafiliacionesEntity rowDetail : listAfiliaciones) {
                    AppAfiliacionesEntity appAfiliacionesEntity = iAfiliacionesRepository.findById(rowDetail.getIdAfiliaciones()).get();
                    appAfiliacionesEntities.add(appAfiliacionesEntity);
                }


                List<AppDiasEntity> appDiasEntityList = obtenerDiasHorasAtencion(appDoctorEntity.getId());

                appDoctorEntity.setDiasAtencion(appDiasEntityList);
                appDoctorEntity.setListEspecialidad(appEspecialidadEntities);
                appDoctorEntity.setPerfilDoctor(row);
                appDoctorEntity.setListAfiliaciones(appAfiliacionesEntities);
                if (appEspecialidadEntities.size() > 0 && row.getEstado().equals("HABILITADO")) {
                    logger.log(Level.INFO, "El doctor no cuenta con especualidad o no se encuentra habilitado " + appDoctorEntity.toString());
                    appDoctorEntityList.add(appDoctorEntity);
                }

            }
        }
        logger.log(Level.INFO, "Listado de doctores por filtro de busqueda por nombre" + appDoctorEntityList.toString());
        return appDoctorEntityList;

    }

    public Object findDoctorEspecialidad(long idDoctor) {


        logger.log(Level.INFO, "Busqueda doctor especialidad, idDoctor" + idDoctor);


        AppDoctorEntity appDoctorEntity = iDoctorRepository.findById(idDoctor).get();

        AppUsuarioEntity appUsuarioEntity = iUserRepository.findById(appDoctorEntity.getUsuarioId()).get();


        List<AppEspecialidadEntity> appEspecialidadEntities = new ArrayList<>();
        List<AppAfiliacionesEntity> appAfiliacionesEntities = new ArrayList<>();
        List<AppDoctoresespecialidadesEntity> listDoctorEspecialidad = iEspecialidadDoctorRepository.findByIdDoctor(appDoctorEntity.getId());

        for (AppDoctoresespecialidadesEntity rowDetail : listDoctorEspecialidad) {
            AppEspecialidadEntity appEspecialidadEntity = iEspecialidadesRepository.findById(rowDetail.getIdEspecialidad()).get();
            appEspecialidadEntities.add(appEspecialidadEntity);
        }

        List<AppDoctorafiliacionesEntity> listAfiliaciones = iAfiliacionesDoctorRepository.findByIdDoctor(appDoctorEntity.getId());

        for (AppDoctorafiliacionesEntity rowDetail : listAfiliaciones) {
            AppAfiliacionesEntity appAfiliacionesEntity = iAfiliacionesRepository.findById(rowDetail.getIdAfiliaciones()).get();
            appAfiliacionesEntities.add(appAfiliacionesEntity);
        }

        List<AppDiasEntity> appDiasEntityList = obtenerDiasHorasAtencion(appDoctorEntity.getId());

        appDoctorEntity.setDiasAtencion(appDiasEntityList);
        appDoctorEntity.setListEspecialidad(appEspecialidadEntities);
        appDoctorEntity.setPerfilDoctor(appUsuarioEntity);
        appDoctorEntity.setListAfiliaciones(appAfiliacionesEntities);
        if (appEspecialidadEntities.size() > 0 && appUsuarioEntity.getEstado().equals("HABILITADO")) {
            logger.log(Level.INFO, "Resultado" + appDoctorEntity.toString());
            return appDoctorEntity;
        } else {
            HashMap<String, String> resultError = new HashMap<>();
            resultError.put("Message", "Usuario doctor se encuentra con estado de inabilitado o no cuenta con especialidades");
            logger.log(Level.INFO, "El doctor no cuenta con especualidad o no se encuentra habilitado " + appDoctorEntity.toString());
            return resultError;
        }
    }


    public List<AppDiasEntity> obtenerDiasHorasAtencion(long id) {
        logger.log(Level.INFO, "Obtener dias del doctor con el id: " + id);
        List<AppDiasEntity> appDiasEntityList = new ArrayList<>();
        List<Integer> diasDoctor = iHoraAtencionRepository.findByDiasIdDoctor(id);

        for (Integer valor : diasDoctor) {
            AppDiasEntity appDiasEntity = iDiasRepository.getid(Long.valueOf(valor));
            appDiasEntity.setHorasAtencion(new ArrayList<>());
            List<AppHorarioatencionEntity> appHorarioatencionEntityList = iHoraAtencionRepository.findHorario(valor, id);
            List<AppHorasEntity> horasEntityList = new ArrayList<>();

            for (AppHorarioatencionEntity horarioatencionEntity : appHorarioatencionEntityList) {
                AppHorasEntity appHorasEntity = iHorasRepository.findById(horarioatencionEntity.getIdHoras()).get();
                horasEntityList.add(appHorasEntity);
            }
            appDiasEntity.setHorasAtencion(horasEntityList);
            appDiasEntityList.add(appDiasEntity);


        }

        logger.log(Level.INFO, "Resultado: " + appDiasEntityList.toString());
        return appDiasEntityList;
    }


    public List<AppDoctoresespecialidadesEntity> getEspecialidadesDoctor(long idDoctor) {

        logger.log(Level.INFO, "Obtener informacion del doctor mas sus especialidades con el idDoctor " + idDoctor);

        List<AppDoctoresespecialidadesEntity> appDoctoresespecialidadesEntities = iEspecialidadDoctorRepository.findByIdDoctor(idDoctor);

        for (AppDoctoresespecialidadesEntity row : appDoctoresespecialidadesEntities) {
            AppEspecialidadEntity appAfiliacionesEntity = iEspecialidadesRepository.findById(row.getIdEspecialidad()).get();
            row.setEspecialidades(appAfiliacionesEntity);
        }
        logger.log(Level.INFO, "Resultado: " + appDoctoresespecialidadesEntities.toString());
        return appDoctoresespecialidadesEntities;
    }

    @Transactional
    public void deleteEspecialidadDoctor(long idDoctor) {
        logger.log(Level.INFO, "Eliminaciones de especialidades del idDoctor: " + idDoctor);
        iEspecialidadDoctorRepository.deleteByIdDoctor(idDoctor);
    }


    public Object findDoctorAleatorio() throws ParseException {
        Random aleatorio = new Random();
        Date fechaSistema = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

//        String fechaHoy = dateFormat.format(fechaSistema);
        List<AppDoctorEntity> appDoctorEntityList = iDoctorRepository.findAllMedicosGeneral();
        List<AppDoctorEntity> disponibles = new ArrayList<>();
        if (appDoctorEntityList.size() > 0) {

            for (AppDoctorEntity doctor : appDoctorEntityList) {
                List<AppHorarioatencionEntity> appHorarioatencionEntityList = iHoraAtencionRepository.findByFecha(doctor.getId());
                System.out.println("List " + appHorarioatencionEntityList.size());

                List<AppHorasEntity> horasEntityList = new ArrayList<>();
                List<String> appAgendacitaEntityListDoctor = iAgendaCitaRepository.findHorasAgendadasDoctor(doctor.getId());
                for (AppHorarioatencionEntity row : appHorarioatencionEntityList) {
                    AppHorasEntity appHorasEntity = iHorasRepository.findById(row.getIdHoras()).get();
                    String[] hora = appHorasEntity.getDescripcion().split("-");

                    String fechaFinal = row.getFecha() + " " + hora[0].trim() + ":00";
                    SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date fechaHora = dateFormat2.parse(fechaFinal);
                    if (!appAgendacitaEntityListDoctor.contains(fechaFinal) && fechaHora.after(fechaSistema)) {
                        horasEntityList.add(appHorasEntity);
                    }
                }
                if (horasEntityList.size() > 0) {
                    disponibles.add(doctor);
                }
            }
            if (disponibles.size() > 0) {
                AppDoctorEntity appDoctorEntity = disponibles.get(aleatorio.nextInt(disponibles.size()));

                AppUsuarioEntity appUsuarioEntity = iUserRepository.findById(appDoctorEntity.getUsuarioId()).get();


                List<AppEspecialidadEntity> appEspecialidadEntities = new ArrayList<>();
                List<AppAfiliacionesEntity> appAfiliacionesEntities = new ArrayList<>();
                List<AppDoctoresespecialidadesEntity> listDoctorEspecialidad = iEspecialidadDoctorRepository.findByIdDoctor(appDoctorEntity.getId());

                for (AppDoctoresespecialidadesEntity rowDetail : listDoctorEspecialidad) {
                    AppEspecialidadEntity appEspecialidadEntity = iEspecialidadesRepository.findById(rowDetail.getIdEspecialidad()).get();
                    appEspecialidadEntities.add(appEspecialidadEntity);
                }

                List<AppDoctorafiliacionesEntity> listAfiliaciones = iAfiliacionesDoctorRepository.findByIdDoctor(appDoctorEntity.getId());

                for (AppDoctorafiliacionesEntity rowDetail : listAfiliaciones) {
                    AppAfiliacionesEntity appAfiliacionesEntity = iAfiliacionesRepository.findById(rowDetail.getIdAfiliaciones()).get();
                    appAfiliacionesEntities.add(appAfiliacionesEntity);
                }


                List<AppDiasEntity> appDiasEntityList = obtenerDiasHorasAtencion(appDoctorEntity.getId());

                appDoctorEntity.setDiasAtencion(appDiasEntityList);
                appDoctorEntity.setListEspecialidad(appEspecialidadEntities);
                appDoctorEntity.setPerfilDoctor(appUsuarioEntity);
                appDoctorEntity.setListAfiliaciones(appAfiliacionesEntities);
                logger.log(Level.INFO, "Doctor obtenido aleatoriamente: " + appDoctorEntity.toString());
                return appDoctorEntity;
            }
        }
        return new ArrayList<>();


    }

}
