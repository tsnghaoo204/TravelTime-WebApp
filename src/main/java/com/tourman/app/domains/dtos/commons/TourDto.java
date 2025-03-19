package com.tourman.app.domains.dtos.commons;

import com.tourman.app.domains.status.TourStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TourDto {
    private String tourName;
    private String description;
    private String tourer;
    private Integer price;
    private LocalDate startDate;
    private LocalDate endDate;
    private String maxParticipants;
    private String imageUrl;
    private String status;
}
