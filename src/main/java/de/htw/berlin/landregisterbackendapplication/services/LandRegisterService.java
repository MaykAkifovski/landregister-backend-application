package de.htw.berlin.landregisterbackendapplication.services;

import de.htw.berlin.landregisterbackendapplication.models.FrontendResponse;
import de.htw.berlin.landregisterbackendapplication.models.LandRegister;
import de.htw.berlin.landregisterbackendapplication.models.ReservationNoteRequest;

import java.util.List;

public interface LandRegisterService {

    List<LandRegister> queryAllLandRegisters();

    LandRegister queryLandRegister(String id);

    FrontendResponse createLandRegister(LandRegister landRegister);

    FrontendResponse createReservationNote(ReservationNoteRequest reservationNoteRequest);
}
