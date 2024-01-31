package org.allivia.api.alliviaapi.controllers;

import org.allivia.api.alliviaapi.entities.AppImgPublicacionEntity;
import org.allivia.api.alliviaapi.services.impl.ImgPublicacionServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/imgPublicacion")
public class ImgPublicacionController extends BaseControllerImpl<AppImgPublicacionEntity, ImgPublicacionServiceImpl> {
}
