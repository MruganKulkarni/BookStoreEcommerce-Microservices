package com.bookstore.user.service;

import com.bookstore.user.dto.UserRequestDTO;
import com.bookstore.user.dto.UserResponseDTO;
import com.bookstore.user.entity.User;
import com.bookstore.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO register(UserRequestDTO request) {

        User user = new User(
                request.getName(),
                request.getEmail(),
                request.getPassword()
        );

        User saved = userRepository.save(user);

        return new UserResponseDTO(
                saved.getId(),
                saved.getName(),
                saved.getEmail()
        );
    }
}