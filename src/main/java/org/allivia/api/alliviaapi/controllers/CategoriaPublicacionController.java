package org.allivia.api.alliviaapi.controllers;

import org.allivia.api.alliviaapi.entities.AppCategoriaPublicacionEntity;
import org.allivia.api.alliviaapi.services.impl.CategoriaPublicacionServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/categoriaPublicacion")
public class CategoriaPublicacionController extends BaseControllerImpl<AppCategoriaPublicacionEntity, CategoriaPublicacionServiceImpl> {
}
