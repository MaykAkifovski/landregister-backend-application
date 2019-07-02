package de.htw.berlin.landregisterbackendapplication.models;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReservationNoteRequest {
    private String docType;
    private TitlePage titlePage;
    private InventoryRegister inventoryRegister;
    private List<Owner> owners;
}
