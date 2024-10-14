package com.my_project.task_management.service;

import com.my_project.task_management.api.model.LoginBody;
import com.my_project.task_management.exception.UserNotFoundException;
import com.my_project.task_management.exception.UsernameAlreadyExistsException;
import com.my_project.task_management.model.User;
import com.my_project.task_management.model.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("Username " + user.getUsername() + " is already taken.");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UsernameAlreadyExistsException("Email " + user.getEmail() + " is already registered.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            return userRepository.save(user);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to register user: " + ex.getMessage());
        }
    }

    public User loginUser(LoginBody loginBody) {
        User user = userRepository.findByUsername(loginBody.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User with username " + loginBody.getUsername() + " not found."));

        // Check if the password matches the stored hash
        if (!passwordEncoder.matches(loginBody.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password.");
        }
        return user;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found."));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found."));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username " + username + " not found."));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User updateUser(Long id, User updatedUser) {
        User existingUser = getUserById(id);

        Optional<User> userWithSameUserName = userRepository.findByUsername(updatedUser.getUsername());
        Optional<User> userWithSameEmail = userRepository.findByEmail(updatedUser.getEmail());
        if (userWithSameUserName.isPresent() && userWithSameEmail.isPresent()
                && !userWithSameUserName.get().getId().equals(id) && !userWithSameEmail.get().getId().equals(id)) {
            throw new UsernameAlreadyExistsException("Username " + updatedUser.getUsername() + " is already taken.");
        }

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        try {
            return userRepository.save(existingUser);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to update user: " + ex.getMessage());
        }
    }


    @Transactional
    public void deleteUser(Long id) {
        User user = getUserById(id);
        try {
            userRepository.delete(user);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to delete user: " + ex.getMessage());
        }
    }


}
