package org.allivia.api.alliviaapi.services;


import com.voximplant.apiclient.ClientException;
import org.allivia.api.alliviaapi.entities.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;


public interface IAgendaCitaService {
    Object save(AppAgendacitaEntity appAgendacitaEntity, String token) throws MessagingException, Exception;
    Object save(AppAgendacitaEntity appAgendacitaEntity) throws Exception;
    Object saveDoctor(AppAgendacitaEntity appAgendacitaEntity) throws Exception;
    Object getConsultasPacienteDoctor(long idPaciente, String nombre, List<String> estadoList);
    Object getConsultasVoxImplant(String idVoxImplant, String fecha);
    Object testVoxImplant(int idVoxImplant) throws IOException, ClientException;

    Object update(AppAgendacitaEntity appAgendacitaEntity) throws Exception;
    Object getListPacientesDoctor(long id, Date fecha);
    List<AppAgendacitaEntity> getHistorialConsultasDoctor(long idDoctor, List<String> estadoList);
    List<AppAgendacitaEntity> getLastPendingHistorialConsultasDoctor(long idDoctor);
    Object findAgendaCitaById(long id);
    Object getAdminPacientesDoctor(long idDoctor, List<String> estadoList);
    Object getFindTodos(long id, String sintoma, String nombre);
    
    Object getListPacientesDoctorPorMes(long id, Date fecha) ;

    Object getListHorariosFechaDoctor(long id, String fecha, Boolean restante);
    Object getAgendaCitaActual(long id,String fecha);
    List<AppAgendacitaEntity> getLitHistorial(long idPaciente);
    List<AppFichamedicamentosEntity> getListMedicamentos(long idPaciente);
    List<AppFichalaboratoriosEntity> getListLaboratorios(long idPaciente);

    List<ProgramasEntity> getLitProgramas(long idPaciente);
    List<TratamientosEntity> getLitTratamientos(long idPaciente);
    boolean Comprobar(String dActual, String dInicial, String dFinal) throws ParseException;
}
