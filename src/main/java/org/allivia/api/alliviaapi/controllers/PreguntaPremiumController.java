package org.allivia.api.alliviaapi.controllers;

import org.allivia.api.alliviaapi.entities.AppPreguntaPremiumEntity;
import org.allivia.api.alliviaapi.services.impl.PreguntaPremiumServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/preguntaPremium")
public class PreguntaPremiumController extends BaseControllerImpl<AppPreguntaPremiumEntity, PreguntaPremiumServiceImpl> {
}
