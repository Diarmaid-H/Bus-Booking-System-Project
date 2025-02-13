package com.bus_project.Bus.Booking.Project.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import com.bus_project.Bus.Booking.Project.DTO.UserDTO;
import com.bus_project.Bus.Booking.Project.Model.User;
import com.bus_project.Bus.Booking.Project.Repository.UserRepository;
import com.bus_project.Bus.Booking.Project.Security.Jwt;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Jwt jwt;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, Jwt jwt) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwt = jwt;
    }

    public User registerUser(UserDTO userDTO) {
        if(userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already registered");
        }

        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());

        User user = new User(userDTO.getName(), userDTO.getEmail(), hashedPassword);

        logger.info("User registered successfully");
        return userRepository.save(user);
    }

    //Return a JWT when the user logs in
    public String loginUser(String email, String password) {
        return userRepository.findByEmail(email)
            .filter(user -> passwordEncoder.matches(password, user.getPasswordHash()))
            .map(user -> jwt.generateToken(user.getEmail()))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));
    }

    public User updateUser(Long id, String email, UserDTO userDTO) {
        User user = userRepository.findById(id)
                                            .filter(u -> u.getEmail().equals(email))
                                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found or unauthorized")); 
        
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPasswordHash(userDTO.getPassword());

        logger.info("User updated successfully!");
        return userRepository.save(user);
    }

    public void deleteUser(Long id, String email) {
        User user = userRepository.findById(id)
                                            .filter(u -> u.getEmail().equals(email))
                                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found or unauthorized"));

        logger.info("User deleted successfully");
        userRepository.delete(user);
    }

}
