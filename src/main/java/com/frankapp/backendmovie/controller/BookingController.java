package com.frankapp.backendmovie.controller;

import com.frankapp.backendmovie.entity.Booking;
import com.frankapp.backendmovie.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> bookSeat(@RequestParam String userId,
                                            @RequestParam String movieId,
                                            @RequestParam String seatNumber) {
        try {
            Booking booking = bookingService.createBooking(userId, movieId, seatNumber);
            return ResponseEntity.ok(booking);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
