package org.allivia.api.alliviaapi.services;

import org.allivia.api.alliviaapi.entities.*;

import java.util.List;

public interface IAntecedenteMedicosService {
    Object save(AppAntecedenteMedicoEntity  appAntecedenteMedicoEntity)throws Exception;
    Object findByIdPaciente(long value);
}
