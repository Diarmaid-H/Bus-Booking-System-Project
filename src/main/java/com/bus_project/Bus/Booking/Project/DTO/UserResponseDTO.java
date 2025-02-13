package com.bus_project.Bus.Booking.Project.DTO;

public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private double credits;

    public UserResponseDTO(Long id, String name, String email, double credits) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.credits = credits;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public double getCredits() {
        return credits;
    }
}
