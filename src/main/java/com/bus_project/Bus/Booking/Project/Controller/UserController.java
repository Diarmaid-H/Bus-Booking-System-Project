package com.bus_project.Bus.Booking.Project.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bus_project.Bus.Booking.Project.DTO.UserDTO;
//import com.bus_project.Bus.Booking.Project.DTO.UserResponseDTO;
import com.bus_project.Bus.Booking.Project.Model.User;
import com.bus_project.Bus.Booking.Project.Security.Jwt;
import com.bus_project.Bus.Booking.Project.Service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final Jwt jwt;

    public UserController(UserService userService, Jwt jwt) {
        this.userService = userService;
        this.jwt = jwt;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        userService.registerUser(userDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) {
        String jwtToken = userService.loginUser(userDTO.getEmail(), userDTO.getPassword());

        return ResponseEntity.ok(jwtToken);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestHeader("Authorization") String token, @RequestBody UserDTO userDTO) {
        String email = jwt.validateTokenAndGetEmail(token.replace("Bearer ", ""));

        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        User updatedUser = userService.updateUser(id, email, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        String email = jwt.validateTokenAndGetEmail(token.replace("Bearer ", ""));

        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        userService.deleteUser(id, email);

        return ResponseEntity.ok("User successfully deleted");
    }

}
