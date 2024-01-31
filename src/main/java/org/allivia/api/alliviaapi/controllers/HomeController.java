/*package org.allivia.api.alliviaapi.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@CrossOrigin(origins = "*")
public class HomeController {
    @GetMapping("/")
    public RedirectView index() {
        // return new RedirectView("/allivia-app/swagger-ui/");
        return new RedirectView("http://104.154.164.119:8080/api/swagger-ui.html");
    }
}*/
