package de.htw.berlin.landregisterbackendapplication.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LandRegisterWrapper {

    @JsonProperty("Key")
    private String key;
    @JsonProperty("Record")
    private LandRegister record;
}
