package com.tourman.app.services;

import com.tourman.app.domains.dtos.commons.BookingDto;
import com.tourman.app.domains.entities.Booking;

public interface BookingService {
    BookingDto addBooking(BookingDto bookingDto);
    BookingDto updateBooking(Long id ,BookingDto bookingDto);
    Booking getBookingById(Long id);
    String deleteBooking(Long id);
}
