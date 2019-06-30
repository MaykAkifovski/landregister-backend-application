package de.htw.berlin.landregisterbackendapplication.models;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LandRegister {
    private String docType;
    private TitlePage titlePage;
    private InventoryRegister inventoryRegister;
    private List<Owner> owners;
    private boolean reservationNote;
}
