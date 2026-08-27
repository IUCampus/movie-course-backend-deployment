package com.frankapp.backendmovie.service;

import com.frankapp.backendmovie.entity.Booking;
import com.frankapp.backendmovie.repository.BookingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaOperations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private KafkaOperations<String, String> kafkaTemplate;

    @InjectMocks
    private BookingService bookingService;

    @Test
    void createBooking_ShouldSucceed_WhenSeatIsAvailable() {
        // Arrange
        String userId = "user1";
        String movieId = "movie1";
        String seatNumber = "A1";

        when(bookingRepository.existsByMovieIdAndSeatNumberAndStatus(movieId, seatNumber, "CONFIRMED"))
                .thenReturn(false);

        Booking savedBooking = new Booking();
        savedBooking.setId("bookingId123");
        savedBooking.setUserId(userId);
        savedBooking.setMovieId(movieId);
        savedBooking.setSeatNumber(seatNumber);
        savedBooking.setAmount(15.00);
        savedBooking.setStatus("PENDING_PAYMENT");
        
        when(bookingRepository.save(any(Booking.class))).thenReturn(savedBooking);

        // Act
        Booking result = bookingService.createBooking(userId, movieId, seatNumber);

        // Assert
        assertNotNull(result);
        assertEquals("bookingId123", result.getId());
        assertEquals("PENDING_PAYMENT", result.getStatus());
        
        verify(bookingRepository).existsByMovieIdAndSeatNumberAndStatus(movieId, seatNumber, "CONFIRMED");
        verify(bookingRepository).save(any(Booking.class));
        verify(kafkaTemplate).send(eq("booking-created-topic"), anyString());
    }

    @Test
    void createBooking_ShouldThrowException_WhenSeatIsAlreadyBooked() {
        // Arrange
        String userId = "user1";
        String movieId = "movie1";
        String seatNumber = "A1";

        when(bookingRepository.existsByMovieIdAndSeatNumberAndStatus(movieId, seatNumber, "CONFIRMED"))
                .thenReturn(true);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookingService.createBooking(userId, movieId, seatNumber);
        });

        assertEquals("Seat A1 is already booked for this movie.", exception.getMessage());
        verify(bookingRepository).existsByMovieIdAndSeatNumberAndStatus(movieId, seatNumber, "CONFIRMED");
        verify(bookingRepository, never()).save(any(Booking.class));
        verify(kafkaTemplate, never()).send(anyString(), anyString());
    }
}
