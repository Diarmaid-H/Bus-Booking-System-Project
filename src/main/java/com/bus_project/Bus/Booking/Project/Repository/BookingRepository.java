package com.bus_project.Bus.Booking.Project.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bus_project.Bus.Booking.Project.Model.Booking;
import com.bus_project.Bus.Booking.Project.Model.BusRoute;
import com.bus_project.Bus.Booking.Project.Model.User;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser(User user);

    @Query("""
    SELECT CASE WHEN COUNT(b) > 0 THEN TRUE ELSE FALSE END 
    FROM Booking b 
    JOIN b.departureSchedule ds
    JOIN ds.stop dStop
    JOIN b.arrivalSchedule as
    JOIN as.stop aStop
    WHERE b.route = :route 
    AND b.bookingDate = :bookingDate 
    AND b.seatNumber = :seatNumber 
    AND (
        dStop.stopOrder BETWEEN :departureStopOrder AND :arrivalStopOrder
        OR 
        aStop.stopOrder BETWEEN :departureStopOrder AND :arrivalStopOrder
    )
    """)
    boolean existsByRouteAndBookingDateAndSeatNumberAndOverlappingStops(
        @Param("route") BusRoute route,
        @Param("bookingDate") LocalDate bookingDate,
        @Param("seatNumber") int seatNumber,
        @Param("departureStopOrder") int departureStopOrder,
        @Param("arrivalStopOrder") int arrivalStopOrder
    );
}
