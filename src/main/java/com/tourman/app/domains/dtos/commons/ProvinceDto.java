package com.tourman.app.domains.dtos.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProvinceDto {
    private String name;

    private String code;

    private String description;
}
