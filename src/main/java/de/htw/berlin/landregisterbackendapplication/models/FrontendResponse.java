package de.htw.berlin.landregisterbackendapplication.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FrontendResponse {
    private boolean isSuccessful;
    private String message;
}
