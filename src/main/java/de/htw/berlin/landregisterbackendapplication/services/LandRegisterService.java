package de.htw.berlin.landregisterbackendapplication.services;

import de.htw.berlin.landregisterbackendapplication.models.LandRegister;
import de.htw.berlin.landregisterbackendapplication.models.ReservationNoteRequest;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface LandRegisterService {
    List<LandRegister> queryAllLandRegisters();

    LandRegister queryLandRegister(String id);


    HttpStatus createLandRegister(LandRegister landRegister);

    HttpStatus createReservationNote(ReservationNoteRequest reservationNoteRequest);
}
