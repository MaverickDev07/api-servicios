package org.allivia.api.alliviaapi.controllers;

import com.voximplant.apiclient.ClientException;
import org.allivia.api.alliviaapi.entities.AppAntecedenteMedicoEntity;
import org.allivia.api.alliviaapi.entities.AppSeguimientoItemPremiumEntity;
import org.allivia.api.alliviaapi.repositories.IAntecedenteMedicoRepository;
import org.allivia.api.alliviaapi.services.AntecedenteMedicoImpl;
import org.allivia.api.alliviaapi.services.impl.SeguimientoItemPremiumServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/seguimientoItemPremium")
public class SeguimientoItemPremiumController extends BaseControllerImpl<AppSeguimientoItemPremiumEntity, SeguimientoItemPremiumServiceImpl> {
}
