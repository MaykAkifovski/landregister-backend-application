package de.htw.berlin.landregisterbackendapplication.services;

import de.htw.berlin.landregisterbackendapplication.models.LandRegister;

import java.util.List;

public interface LandRegisterService {
    List<LandRegister> queryAllLandRegisters();

    LandRegister queryLandRegister(String id);


}
