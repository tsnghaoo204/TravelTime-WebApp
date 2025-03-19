package com.tourman.app.domains.dtos.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private String userName;
    private String tourName;
    private String bookingDate;
    private String status;
    private Integer price;
    private String paymentStatus;
}
