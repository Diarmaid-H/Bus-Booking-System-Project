package com.bus_project.Bus.Booking.Project.DTO;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingResponseDTO {

    private Long id;
    private LocalDate bookingDate;
    private int seatNumber;
    private double price;

    private String userName;
    private String userEmail;

    private Long routeId;
    private String routeName;

    private String departureStopName;
    private String arrivalStopName;
    private String departureTime;
    private String arrivalTime;

    public BookingResponseDTO (Long id, LocalDate bookingDate, int seatNumber, double price, 
                                String userName, String userEmail,
                                Long routeId, String routeName,
                                String departureStopName, String arrivalStopName, String departureTime, String arrivalTime                            
                                ) {
        this.id = id;
        this.bookingDate = bookingDate;
        this.seatNumber = seatNumber;
        this.price = price;
        this.userName = userName;
        this.userEmail = userEmail;
        this.routeId = routeId;
        this.routeName = routeName;
        this.departureStopName = departureStopName;
        this.arrivalStopName = arrivalStopName;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }
}
