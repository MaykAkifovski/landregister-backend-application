package de.htw.berlin.landregisterbackendapplication.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class InventoryRegister {
    private String subdistrict;
    private String hall;
    private String parcel;
    private String economicType;
    private String location;
    private String size;
}
