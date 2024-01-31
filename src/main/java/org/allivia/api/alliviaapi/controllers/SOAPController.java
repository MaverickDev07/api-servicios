package org.allivia.api.alliviaapi.controllers;

import org.allivia.api.alliviaapi.entities.AppSOAPEntity;
import org.allivia.api.alliviaapi.services.impl.SOAPServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/soap")
public class SOAPController extends BaseControllerImpl<AppSOAPEntity, SOAPServiceImpl> {
}
