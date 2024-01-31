package org.allivia.api.alliviaapi.controllers;

import org.allivia.api.alliviaapi.entities.AppItemPremiumEntity;
import org.allivia.api.alliviaapi.services.impl.ItemPremiumServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/itemPremium")
public class ItemPremiumController extends BaseControllerImpl<AppItemPremiumEntity, ItemPremiumServiceImpl> {
}
