package com.bus_project.Bus.Booking.Project.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Ensures one user can have many bookings
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    //Ensures one route can have many bookings
    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private BusRoute route;

    //Ensures one departure can have many bookings
    @ManyToOne
    @JoinColumn(name = "departure_schedule_id", nullable = false)
    private BusSchedule departureSchedule;

    //Ensures one departure can have many bookings
    @ManyToOne
    @JoinColumn(name = "arrival_schedule_id", nullable = false)
    private BusSchedule arrivalSchedule;

    @Column(nullable = false)
    private LocalDate bookingDate;

    @Column(nullable = false)
    private int seatNumber;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false, updatable = false)
    private LocalDateTime bookingTime = LocalDateTime.now();

}
