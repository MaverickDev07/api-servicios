package org.allivia.api.alliviaapi.services;



import org.allivia.api.alliviaapi.entities.*;

public interface IFichaMedicaService {
    Object saveFichaMedica(AppFichamedicaEntity appFichamedicaEntity);
    Object updateFichaMedica(AppFichamedicaEntity appFichamedicaEntity);
    Object saveDiagnostico(AppFichadiagnosticoEntity fichadiagnosticoEntity);
    Object saveMedicamentos(AppFichamedicamentosEntity fichamedicamentosEntity);
    Object saveLaboratorios(AppFichalaboratoriosEntity fichalaboratoriosEntity);
    Object saveExamenes(AppFichaexamenesEntity fichaexamenesEntity);
    Object saveArchivos(AppFichadocumentosEntity fichadocumentosEntity);
    Object getProgramaFase(long idPrograma);
    Object getPrograma();
    Object findExamenNombre(String descripcion);
    Object findLaboratorioNombre(String descripcion);
}
