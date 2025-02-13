package com.bus_project.Bus.Booking.Project.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bus_project.Bus.Booking.Project.DTO.BookingDTO;
import com.bus_project.Bus.Booking.Project.DTO.BookingResponseDTO;
import com.bus_project.Bus.Booking.Project.Model.Booking;
import com.bus_project.Bus.Booking.Project.Model.BusRoute;
import com.bus_project.Bus.Booking.Project.Model.BusSchedule;
import com.bus_project.Bus.Booking.Project.Model.User;
import com.bus_project.Bus.Booking.Project.Repository.BookingRepository;
import com.bus_project.Bus.Booking.Project.Repository.BusRouteRepository;
import com.bus_project.Bus.Booking.Project.Repository.BusScheduleRepository;
import com.bus_project.Bus.Booking.Project.Repository.UserRepository;

@Service
public class BookingService {

    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final BusRouteRepository busRouteRepository;
    private final BusScheduleRepository busScheduleRepository;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository,
        BusRouteRepository busRouteRepository, BusScheduleRepository busScheduleRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.busRouteRepository = busRouteRepository;
        this.busScheduleRepository = busScheduleRepository;
    }

    public Booking getUserBooking(Long id, String email) {
        return bookingRepository.findById(id)
                                    .filter(booking -> booking.getUser().getEmail().equals(email))
                                    .orElseThrow(() -> {
                                        logger.warn("Booking not found, or unauthorized for user");
                                        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found or authorised for user");
                                    });       
    }

    public List<BookingResponseDTO> getUserBookings(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.warn("User with email {} not found.", email);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
                });

        List<BookingResponseDTO> bookings = user.getBookings().stream()
                .map(booking -> new BookingResponseDTO(
                        booking.getId(),
                        booking.getBookingDate(),
                        booking.getSeatNumber(),
                        booking.getPrice(),
                        user.getName(),
                        user.getEmail(),
                        booking.getRoute().getId(),
                        booking.getRoute().getName(),
                        booking.getDepartureSchedule().getStop().getLocation(),
                        booking.getArrivalSchedule().getStop().getLocation(),
                        booking.getDepartureSchedule().getDepartureTime().toString(),
                        booking.getArrivalSchedule().getArrivalTime().toString()
                ))
                .collect(Collectors.toList());

        if (bookings.isEmpty()) {
            logger.info("User {} has no bookings.", email);
        }

        return bookings;
    }

    public BookingResponseDTO createBooking(String email, BookingDTO bookingDTO) {
        User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));

        BusRoute busRoute = busRouteRepository.findById(bookingDTO.getRouteId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found"));
        
        BusSchedule departureSchedule = busScheduleRepository.findById(bookingDTO.getDepartureScheduleId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Departure Schedule not found"));
        
        BusSchedule arrivalSchedule = busScheduleRepository.findById(bookingDTO.getArrivalScheduleId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Arrival Schedule not found"));

        if (arrivalSchedule.getStop().getStopOrder() <= departureSchedule.getStop().getStopOrder()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Arrival stop must be after departure stop.");
        }

        boolean isSeatBooked = bookingRepository.existsByRouteAndBookingDateAndSeatNumberAndOverlappingStops(
            busRoute, bookingDTO.getBookingDate(), bookingDTO.getSeatNumber(), departureSchedule.getStop().getStopOrder(), arrivalSchedule.getStop().getStopOrder()
        );

        if(isSeatBooked) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Seat already booked for this journey.");
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRoute(busRoute);
        booking.setDepartureSchedule(departureSchedule);
        booking.setArrivalSchedule(arrivalSchedule);
        booking.setBookingDate(bookingDTO.getBookingDate());
        booking.setSeatNumber(bookingDTO.getSeatNumber());
        booking.setPrice(2.00);

        Booking savedBooking = bookingRepository.save(booking);
        logger.info("Booking created for user");

        return new BookingResponseDTO(
            savedBooking.getId(),
            savedBooking.getBookingDate(),
            savedBooking.getSeatNumber(),
            savedBooking.getPrice(),
            user.getName(),
            user.getEmail(),
            savedBooking.getRoute().getId(),
            savedBooking.getRoute().getName(),
            savedBooking.getDepartureSchedule().getStop().getLocation(),
            savedBooking.getArrivalSchedule().getStop().getLocation(),
            savedBooking.getDepartureSchedule().getDepartureTime().toString(),
            savedBooking.getArrivalSchedule().getArrivalTime().toString()
        );
    }

    public BookingResponseDTO updateBooking(Long id, String email, BookingDTO bookingDTO) {
        Booking booking = bookingRepository.findById(id)
                                .filter(b -> b.getUser().getEmail().equals(email))
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found or unauthorized"));

        BusRoute busRoute = busRouteRepository.findById(bookingDTO.getRouteId())
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found"));
                
        BusSchedule departureSchedule = busScheduleRepository.findById(bookingDTO.getDepartureScheduleId())
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Departure Schedule not found"));
                
        BusSchedule arrivalSchedule = busScheduleRepository.findById(bookingDTO.getArrivalScheduleId())
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Arrival Schedule not found"));

        if (arrivalSchedule.getStop().getStopOrder() <= departureSchedule.getStop().getStopOrder()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Arrival stop must be after departure stop.");
        }
 
        booking.setRoute(busRoute);
        booking.setDepartureSchedule(departureSchedule);
        booking.setArrivalSchedule(arrivalSchedule);
        booking.setBookingDate(bookingDTO.getBookingDate());
        booking.setSeatNumber(bookingDTO.getSeatNumber());

        Booking updatedBooking = bookingRepository.save(booking);
        logger.info("Booking updated successfully!");

        return new BookingResponseDTO(
                updatedBooking.getId(),
                updatedBooking.getBookingDate(),
                updatedBooking.getSeatNumber(),
                updatedBooking.getPrice(),
                updatedBooking.getUser().getName(),
                updatedBooking.getUser().getEmail(),
                updatedBooking.getRoute().getId(),
                updatedBooking.getRoute().getName(),
                updatedBooking.getDepartureSchedule().getStop().getLocation(),
                updatedBooking.getArrivalSchedule().getStop().getLocation(),
                updatedBooking.getDepartureSchedule().getDepartureTime().toString(),
                updatedBooking.getArrivalSchedule().getArrivalTime().toString()
        ); 
    }

    public void deleteBooking(Long id, String email) {
        Booking booking = bookingRepository.findById(id)
                .filter(b -> b.getUser().getEmail().equals(email))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found or unauthorized"));

        bookingRepository.delete(booking);
        logger.info("Booking  deleted successfully");
    }
}
