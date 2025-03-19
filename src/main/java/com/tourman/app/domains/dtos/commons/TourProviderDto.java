package com.tourman.app.domains.dtos.commons;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TourProviderDto {
    private String email;
    private String companyName;
    private String phoneNumber;
    private String description;
    private String address;
}

