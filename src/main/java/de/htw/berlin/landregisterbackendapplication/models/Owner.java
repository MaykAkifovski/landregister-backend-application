package de.htw.berlin.landregisterbackendapplication.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Owner {
    private String identityNumber;
    private String title;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String postcode;
    private String city;
    private String street;
    private String streetNumber;
    private String coOwnership;
}
