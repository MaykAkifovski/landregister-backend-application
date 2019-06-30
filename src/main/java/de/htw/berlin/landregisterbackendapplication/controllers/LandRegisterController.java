package de.htw.berlin.landregisterbackendapplication.controllers;

import de.htw.berlin.landregisterbackendapplication.models.LandRegister;
import de.htw.berlin.landregisterbackendapplication.services.LandRegisterService;
import de.htw.berlin.landregisterbackendapplication.services.UserRegistrationEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/landregister")
@CrossOrigin
public class LandRegisterController {

    @Autowired
    private LandRegisterService landRegisterService;
    @Autowired
    private UserRegistrationEnrollmentService userRegistrationEnrollmentService;

    @GetMapping("/registerEnrollUser")
    public HttpStatus registerEnrollUser() {
        return userRegistrationEnrollmentService.registerEnrollUser();
    }

    @GetMapping("/queryAllLandRegisters")
    public List<LandRegister> queryAllLandRegisters() {
        return landRegisterService.queryAllLandRegisters();
    }
}
