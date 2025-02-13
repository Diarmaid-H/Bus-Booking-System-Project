package com.bus_project.Bus.Booking.Project.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bus_project.Bus.Booking.Project.DTO.BookingDTO;
import com.bus_project.Bus.Booking.Project.DTO.BookingResponseDTO;
import com.bus_project.Bus.Booking.Project.Model.Booking;
import com.bus_project.Bus.Booking.Project.Security.Jwt;
import com.bus_project.Bus.Booking.Project.Service.BookingService;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private final BookingService bookingService;
    private final Jwt jwt;

    public BookingController(BookingService bookingService, Jwt jwt) {
        this.bookingService = bookingService;
        this.jwt = jwt;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserBooking(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        String email = jwt.validateTokenAndGetEmail(token.replace("Bearer ", ""));

        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        Booking booking = bookingService.getUserBooking(id, email);

        return ResponseEntity.ok(booking);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getUserBookings(@RequestHeader("Authorization") String token) {
        String email = jwt.validateTokenAndGetEmail(token.replace("Bearer ", ""));

        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        List<BookingResponseDTO> bookings = bookingService.getUserBookings(email);

        if(bookings.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No bookings found.");
        }

        return ResponseEntity.ok(bookings);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@RequestHeader("Authorization") String token, @RequestBody BookingDTO bookingDTO) {
        String email = jwt.validateTokenAndGetEmail(token.replace("Bearer ", ""));

        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        try {
            BookingResponseDTO newBooking = bookingService.createBooking(email, bookingDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newBooking);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating booking: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable Long id, @RequestHeader("Authorization") String token, @RequestBody BookingDTO bookingDTO) {
        String email = jwt.validateTokenAndGetEmail(token.replace("Bearer ", ""));

        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        try {
            BookingResponseDTO updatedBooking = bookingService.updateBooking(id, email, bookingDTO);

            return ResponseEntity.ok(updatedBooking);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating booking: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        String email = jwt.validateTokenAndGetEmail(token.replace("Bearer ", ""));

        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        try {
            bookingService.deleteBooking(id, email);

            return ResponseEntity.ok("Booking successfully deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error deleting booking: " + e.getMessage());
        }
    }

}
