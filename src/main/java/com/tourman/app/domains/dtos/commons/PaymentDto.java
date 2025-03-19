package com.tourman.app.domains.dtos.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private Long bookingId;
    private Integer amount;
    private String paymentMethod;
    private String status;
}