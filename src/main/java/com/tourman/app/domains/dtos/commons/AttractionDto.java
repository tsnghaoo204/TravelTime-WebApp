package com.tourman.app.domains.dtos.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttractionDto {
    private String name;
    private String description;
    private String location;
    private Float rate;
}
