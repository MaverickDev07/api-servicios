package org.allivia.api.alliviaapi.controllers;

import org.allivia.api.alliviaapi.entities.AppLaboratorioEmpresaEntity;
import org.allivia.api.alliviaapi.services.impl.LaboratorioEmpresaServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/laboratorioEmpresa")
public class LaboratorioEmpresaController extends BaseControllerImpl<AppLaboratorioEmpresaEntity, LaboratorioEmpresaServiceImpl> {
}
