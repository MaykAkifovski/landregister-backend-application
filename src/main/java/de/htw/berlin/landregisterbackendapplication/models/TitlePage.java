package de.htw.berlin.landregisterbackendapplication.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TitlePage {
    private String districtCourt;
    private String landRegistryDistrict;
    private String sheetNumber;
}
