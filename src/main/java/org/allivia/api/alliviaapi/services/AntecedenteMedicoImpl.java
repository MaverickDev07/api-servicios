package org.allivia.api.alliviaapi.services;


import com.voximplant.apiclient.ClientException;
import org.allivia.api.alliviaapi.entities.*;
import org.allivia.api.alliviaapi.repositories.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AntecedenteMedicoImpl implements IAntecedenteMedicosService {
    public static final Logger logger = LogManager.getLogger(AntecedenteMedicoImpl.class);
    @Autowired
    private IAntecedenteMedicoRepository iAntecedenteMedicoRepository;

    @Autowired
    private IAntecedenteCirugiaRepository iAntecedenteCirugiaRepository;
    @Autowired
    private IAntecedenteEnfermedadBaseRepository iAntecedenteEnfermedadBaseRepository;
    @Autowired
    private IAntecedenteAlergiaRepository iAntecedenteAlergiaRepository;
    @Autowired
    private IAntecedenteVacunaRepository iAntecedenteVacunaRepository;

    @Autowired
    private IAntecedenteFamiliarRepository iAntecedenteFamiliarRepository;

    @Autowired
    private ICirugiaRepository iCirugiaRepository;
    @Autowired
    private IEnfermedadBaseRepository iEnfermedadBaseRepository;
    @Autowired
    private IAlergiaRepository iAlergiaRepository;
    @Autowired
    private IVacunaRepository iVacunaRepository;

    @Override
    public Object save(AppAntecedenteMedicoEntity appAntecedenteMedicoEntity) throws ClientException {
        logger.log(Level.INFO, "Datos de antecedente médico" + appAntecedenteMedicoEntity.toString());
        validacion(appAntecedenteMedicoEntity);

        if (appAntecedenteMedicoEntity.getId() > 0) {
            return updateAntecedentes(appAntecedenteMedicoEntity);
        } else {
            return saveAntecedentes(appAntecedenteMedicoEntity);
        }
    }

    private void validarAntecedenteExistente(AppAntecedenteMedicoEntity appAntecedenteMedicoEntity) throws ClientException {
        AppAntecedenteMedicoEntity entity = iAntecedenteMedicoRepository.findByIdPaciente(appAntecedenteMedicoEntity.getIdPaciente());
        if (entity != null) {
            throw new ClientException("Ya tiene antecedentes registrados por favor actualize");
        }
    }

    @Override
    public Object findByIdPaciente(long value) {
        AppAntecedenteMedicoEntity result = iAntecedenteMedicoRepository.findByIdPaciente(value);
        logger.log(Level.INFO, "Listado de alergias" + result);
        return result;
    }

    public AppAntecedenteMedicoEntity saveAntecedentes(AppAntecedenteMedicoEntity appAntecedenteMedicoEntity) throws ClientException {

        validarAntecedenteExistente(appAntecedenteMedicoEntity);
        AppAntecedenteMedicoEntity medicoEntity = iAntecedenteMedicoRepository.save(appAntecedenteMedicoEntity);
        medicoEntity.setAppAntecedenteEnfermedadBase(saveAntecedentesEfermedadBase(appAntecedenteMedicoEntity, medicoEntity));
        medicoEntity.setAppAntecedenteCirugias(saveAntecedentesCirugia(appAntecedenteMedicoEntity, medicoEntity));
        medicoEntity.setAppAntecedenteVacuna(saveAntecedentesVacuna(appAntecedenteMedicoEntity, medicoEntity));
        medicoEntity.setAppAntecedenteAlergias(saveAntecedentesAlergia(appAntecedenteMedicoEntity, medicoEntity));
        medicoEntity.setAppAntecedenteFamilias(saveAntecedentesFamiliar(appAntecedenteMedicoEntity, medicoEntity));
        return appAntecedenteMedicoEntity;
    }

    public AppAntecedenteMedicoEntity updateAntecedentes(AppAntecedenteMedicoEntity appAntecedenteMedicoEntity) throws ClientException {
      //  try {
            iAntecedenteEnfermedadBaseRepository.deleteByIdAntecedente(appAntecedenteMedicoEntity.getId());
            iAntecedenteCirugiaRepository.deleteByIdAntecedente(appAntecedenteMedicoEntity.getId());
            iAntecedenteAlergiaRepository.deleteByIdAntecedente(appAntecedenteMedicoEntity.getId());
            iAntecedenteVacunaRepository.deleteByIdAntecedente(appAntecedenteMedicoEntity.getId());
            iAntecedenteFamiliarRepository.deleteByIdAntecedente(appAntecedenteMedicoEntity.getId());
        /*} catch (Exception IgnoException) {
        }*/

        AppAntecedenteMedicoEntity medicoEntity=new AppAntecedenteMedicoEntity();
        try {
             medicoEntity = iAntecedenteMedicoRepository.findById(appAntecedenteMedicoEntity.getId()).get();
        }catch (Exception e){
            throw new ClientException("No existe el antecedente para actualizar");
        }


        medicoEntity.setFechaNacimiento(appAntecedenteMedicoEntity.getFechaNacimiento());
        medicoEntity.setGenero(appAntecedenteMedicoEntity.getGenero());
        medicoEntity.setFuma(appAntecedenteMedicoEntity.getFuma());
        medicoEntity.setAlcohol(appAntecedenteMedicoEntity.getAlcohol());
        medicoEntity.setCafeina(appAntecedenteMedicoEntity.getCafeina());
        medicoEntity.setEjercicio(appAntecedenteMedicoEntity.getEjercicio());
        medicoEntity.setDrogas(appAntecedenteMedicoEntity.getDrogas());
        medicoEntity.setTestifico(appAntecedenteMedicoEntity.isTestifico());
        medicoEntity = iAntecedenteMedicoRepository.save(medicoEntity);

        medicoEntity.setAppAntecedenteEnfermedadBase(saveAntecedentesEfermedadBase(appAntecedenteMedicoEntity, medicoEntity));
        medicoEntity.setAppAntecedenteCirugias(saveAntecedentesCirugia(appAntecedenteMedicoEntity, medicoEntity));
        medicoEntity.setAppAntecedenteVacuna(saveAntecedentesVacuna(appAntecedenteMedicoEntity, medicoEntity));
        medicoEntity.setAppAntecedenteAlergias(saveAntecedentesAlergia(appAntecedenteMedicoEntity, medicoEntity));
        medicoEntity.setAppAntecedenteFamilias(saveAntecedentesFamiliar(appAntecedenteMedicoEntity, medicoEntity));
        return medicoEntity;
    }

    public List<AppAntecedenteEnfermedadBaseEntity> saveAntecedentesEfermedadBase(AppAntecedenteMedicoEntity appAntecedente, AppAntecedenteMedicoEntity entity) throws ClientException {
        List<AppAntecedenteEnfermedadBaseEntity> appAntecedenteEnfermedadBaseEntityArrayList = new ArrayList<>();
        for (AppAntecedenteEnfermedadBaseEntity value : appAntecedente.getAppAntecedenteEnfermedadBase()) {
            AppAntecedenteEnfermedadBaseEntity appAntecedenteEnfermedadBaseEntity = new AppAntecedenteEnfermedadBaseEntity();
            appAntecedenteEnfermedadBaseEntity.setAppAntecedenteMedicoByIdAntecedenteMedico(entity);
            try {
                appAntecedenteEnfermedadBaseEntity.setAppEnfermedadBase(iEnfermedadBaseRepository.findById(value.getAppEnfermedadBase().getId()).get());
            } catch (Exception e) {
                throw new ClientException("Enfermedad base no encontrada");
            }
            appAntecedenteEnfermedadBaseEntityArrayList.add(iAntecedenteEnfermedadBaseRepository.save(appAntecedenteEnfermedadBaseEntity));
        }
        return appAntecedenteEnfermedadBaseEntityArrayList;
    }

    public List<AppAntecedenteCirugiaEntity> saveAntecedentesCirugia(AppAntecedenteMedicoEntity appAntecedente, AppAntecedenteMedicoEntity entity) throws ClientException {
        List<AppAntecedenteCirugiaEntity> appAntecedenteCirugiaEntityList = new ArrayList<>();
        for (AppAntecedenteCirugiaEntity value : appAntecedente.getAppAntecedenteCirugias()) {
            AppAntecedenteCirugiaEntity appAntecedenteCirugiaEntity = new AppAntecedenteCirugiaEntity();
            appAntecedenteCirugiaEntity.setAppAntecedenteMedicoByIdAntecedenteMedico(entity);
            try {
                appAntecedenteCirugiaEntity.setAppCirugia(iCirugiaRepository.findById(value.getAppCirugia().getId()).get());
            } catch (Exception e) {
                throw new ClientException("Cirugia no encontrada");
            }
            appAntecedenteCirugiaEntityList.add(iAntecedenteCirugiaRepository.save(appAntecedenteCirugiaEntity));
        }
        return appAntecedenteCirugiaEntityList;
    }

    public List<AppAntecedenteVacunaEntity> saveAntecedentesVacuna(AppAntecedenteMedicoEntity appAntecedente, AppAntecedenteMedicoEntity entity) throws ClientException {
        List<AppAntecedenteVacunaEntity> appAntecedenteVacunaEntityArrayList = new ArrayList<>();
        for (AppAntecedenteVacunaEntity value : appAntecedente.getAppAntecedenteVacuna()) {
            AppAntecedenteVacunaEntity appAntecedenteVacunaEntity = new AppAntecedenteVacunaEntity();
            appAntecedenteVacunaEntity.setAppAntecedenteMedicoByIdAntecedenteMedico(entity);
            try {
                appAntecedenteVacunaEntity.setAppVacuna(iVacunaRepository.findById(value.getAppVacuna().getId()).get());
            } catch (Exception e) {
                throw new ClientException("Vacuna no encontrada");
            }

            appAntecedenteVacunaEntityArrayList.add(iAntecedenteVacunaRepository.save(appAntecedenteVacunaEntity));
        }
        return appAntecedenteVacunaEntityArrayList;
    }

    public List<AppAntecedenteAlergiaEntity> saveAntecedentesAlergia(AppAntecedenteMedicoEntity appAntecedente, AppAntecedenteMedicoEntity entity) throws ClientException {
        List<AppAntecedenteAlergiaEntity> appAntecedenteAlergiaEntityList = new ArrayList<>();
        for (AppAntecedenteAlergiaEntity value : appAntecedente.getAppAntecedenteAlergias()) {
            AppAntecedenteAlergiaEntity appAntecedenteAlergiaEntity = new AppAntecedenteAlergiaEntity();
            appAntecedenteAlergiaEntity.setAppAntecedenteMedicoByIdAntecedenteMedico(entity);
            try {
                appAntecedenteAlergiaEntity.setAppAlergia(iAlergiaRepository.findById(value.getAppAlergia().getId()).get());
            } catch (Exception e) {
                throw new ClientException("Alergia no encontrada");
            }
            appAntecedenteAlergiaEntityList.add(iAntecedenteAlergiaRepository.save(appAntecedenteAlergiaEntity));
        }
        return appAntecedenteAlergiaEntityList;
    }

    public List<AppAntecedenteFamiliaEntity> saveAntecedentesFamiliar(AppAntecedenteMedicoEntity appAntecedente, AppAntecedenteMedicoEntity entity) throws ClientException {
        List<AppAntecedenteFamiliaEntity> appAntecedenteFamiliaEntityList = new ArrayList<>();
        for (AppAntecedenteFamiliaEntity row : appAntecedente.getAppAntecedenteFamilias()) {
            row.setAppAntecedenteMedicoByIdAntecedenteMedico(entity);
            AppAntecedenteFamiliaEntity saverow = iAntecedenteFamiliarRepository.save(row);
            appAntecedenteFamiliaEntityList.add(saverow);
        }
        return appAntecedenteFamiliaEntityList;
    }

    public void validacion(AppAntecedenteMedicoEntity appAntecedenteMedicoEntity) throws ClientException {
        long aux = 0;
        for (AppAntecedenteEnfermedadBaseEntity value : appAntecedenteMedicoEntity.getAppAntecedenteEnfermedadBase()) {
            if (aux == value.getAppEnfermedadBase().getId()) {
                throw new ClientException("Enfermedad base: se acepta una opción");
            }
            aux = value.getAppEnfermedadBase().getId();
            try {
              iEnfermedadBaseRepository.findById(value.getAppEnfermedadBase().getId()).get();

            } catch (Exception e) {
                throw new ClientException("Enfermedad base no encontrada");
            }
        }
        aux = 0;
        for (AppAntecedenteCirugiaEntity value : appAntecedenteMedicoEntity.getAppAntecedenteCirugias()) {
            if (aux == value.getAppCirugia().getId()) {
                throw new ClientException("Cirugia: se acepta una opción");
            }
            aux = value.getAppCirugia().getId();
            try {
                iCirugiaRepository.findById(value.getAppCirugia().getId());
            } catch (Exception e) {
                throw new ClientException("Cirugia no encontrada");
            }
        }
        aux = 0;
        for (AppAntecedenteVacunaEntity value : appAntecedenteMedicoEntity.getAppAntecedenteVacuna()) {
            if (aux == value.getAppVacuna().getId()) {
                throw new ClientException("Vacuna: se acepta una opción");
            }
            aux = value.getAppVacuna().getId();
            try {
                iVacunaRepository.findById(value.getAppVacuna().getId());
            } catch (Exception e) {
                throw new ClientException("Vacuna no encontrada");
            }
        }
        aux = 0;
        for (AppAntecedenteAlergiaEntity value : appAntecedenteMedicoEntity.getAppAntecedenteAlergias()) {
            if (aux == value.getAppAlergia().getId()) {
                throw new ClientException("Alergia: se acepta una opción");
            }
            aux = value.getAppAlergia().getId();
            try {
                iAlergiaRepository.findById(value.getAppAlergia().getId());
            } catch (Exception e) {
                throw new ClientException("Alergia no encontrada");
            }
        }
    }
}