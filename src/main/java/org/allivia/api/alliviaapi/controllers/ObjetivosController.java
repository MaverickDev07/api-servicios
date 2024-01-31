package org.allivia.api.alliviaapi.controllers;

import org.allivia.api.alliviaapi.entities.AppObjetivosEntity;
import org.allivia.api.alliviaapi.services.impl.ObjetivosServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/objetivos")
public class ObjetivosController extends BaseControllerImpl<AppObjetivosEntity, ObjetivosServiceImpl> {
}
