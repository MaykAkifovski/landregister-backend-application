package de.htw.berlin.landregisterbackendapplication.models;

import java.util.Arrays;

public class DefaultLandRegister {
    public static final LandRegister DEFAULT = LandRegister.builder()
            .docType("landRegister")
            .titlePage(TitlePage.builder()
                    .districtCourt("districtCourt")
                    .landRegistryDistrict("landRegistryDistrict")
                    .sheetNumber("sheetNumber")
                    .build())
            .inventoryRegister(InventoryRegister.builder()
                    .subdistrict("subdistrict")
                    .hall("hall")
                    .parcel("parcel")
                    .economicType("economicType")
                    .location("location")
                    .size("size")
                    .build())
            .owners(Arrays.asList(
                    Owner.builder()
                            .identityNumber("identityNumber_1")
                            .title("title_1")
                            .firstname("firstname_1")
                            .lastname("lastname_1")
                            .dateOfBirth("dateOfBirth_1")
                            .postcode("postcode_1")
                            .city("city_1")
                            .street("street_1")
                            .streetnumber("streetnumber_1")
                            .build(),
                    Owner.builder()
                            .identityNumber("identityNumber_2")
                            .title("title_2")
                            .firstname("firstname_2")
                            .lastname("lastname_2")
                            .dateOfBirth("dateOfBirth_2")
                            .postcode("postcode_2")
                            .city("city_2")
                            .street("street_2")
                            .streetnumber("streetnumber_2")
                            .build()
            ))
            .reservationNote(false)
            .build();
}
