package com.frankapp.backendmovie.repository;

import com.frankapp.backendmovie.entity.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends MongoRepository<Booking, String> {

    // Custom query automatically mapped by Spring Data MongoDB
    boolean existsByMovieIdAndSeatNumberAndStatus(String movieId, String seatNumber, String status);
}
