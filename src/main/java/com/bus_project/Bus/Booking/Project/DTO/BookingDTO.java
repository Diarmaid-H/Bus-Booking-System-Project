package com.bus_project.Bus.Booking.Project.DTO;

import java.time.LocalDate;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingDTO {

    private Long routeId;
    private Long departureScheduleId;
    private Long arrivalScheduleId;
    private LocalDate bookingDate;

    @Min(1)
    @Max(10)
    private int seatNumber;

}
