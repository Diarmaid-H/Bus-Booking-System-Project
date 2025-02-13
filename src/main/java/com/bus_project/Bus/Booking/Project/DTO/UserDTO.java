package com.bus_project.Bus.Booking.Project.DTO;

// import jakarta.validation.constraints.Email;
// import jakarta.validation.constraints.NotBlank;

public class UserDTO {

    //@NotBlank(message = "name is required")
    private String name;

    //@NotBlank(message = "email is required")
    private String email;

    //@NotBlank(message = "password is required")
    private String password;

    public UserDTO() {}

    public UserDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}

