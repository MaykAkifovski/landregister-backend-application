package de.htw.berlin.landregisterbackendapplication.controllers;

import de.htw.berlin.landregisterbackendapplication.models.LandRegister;
import de.htw.berlin.landregisterbackendapplication.models.ReservationNoteRequest;
import de.htw.berlin.landregisterbackendapplication.services.LandRegisterService;
import de.htw.berlin.landregisterbackendapplication.services.UserRegistrationEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static de.htw.berlin.landregisterbackendapplication.hyperledger.user.UserContextDB.areUsersNotCreated;

@RestController
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
        if (areUsersNotCreated()) {
            userRegistrationEnrollmentService.registerEnrollUser();
        }
        return landRegisterService.queryAllLandRegisters();
    }

    @PostMapping(path = "/queryLandRegister/{id}")
    public LandRegister queryLandRegister(@PathVariable String id) {
        if (areUsersNotCreated()) {
            userRegistrationEnrollmentService.registerEnrollUser();
        }
        return landRegisterService.queryLandRegister(id);
    }

    @PostMapping(path = "/createLandRegister")
    public HttpStatus createLandRegister(@RequestBody LandRegister landRegister) {
        if (areUsersNotCreated()) {
            userRegistrationEnrollmentService.registerEnrollUser();
        }
        return landRegisterService.createLandRegister(landRegister);
    }

    @PostMapping(path = "/createReservationNote")
    public HttpStatus createReservationNote(@RequestBody ReservationNoteRequest reservationNoteRequest) {
        if (areUsersNotCreated()) {
            userRegistrationEnrollmentService.registerEnrollUser();
        }
        return landRegisterService.createReservationNote(reservationNoteRequest);
    }
}
