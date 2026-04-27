package com.bookstore.user.service;

import com.bookstore.user.dto.UserRequestDTO;
import com.bookstore.user.dto.UserResponseDTO;
import com.bookstore.user.entity.User;
import com.bookstore.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.bookstore.user.dto.LoginRequestDTO;
import com.bookstore.user.dto.LoginResponseDTO;
import com.bookstore.user.config.JwtUtil;
import com.bookstore.user.exception.InvalidCredentialsException;
import com.bookstore.user.client.TestClient;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final TestClient testClient;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil,
                       TestClient testClient) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.testClient = testClient;
    }

    public UserResponseDTO register(UserRequestDTO request) {

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(
                request.getName(),
                request.getEmail(),
                encodedPassword
        );

        User saved = userRepository.save(user);

        return new UserResponseDTO(
                saved.getId(),
                saved.getName(),
                saved.getEmail()
        );
    }
    public LoginResponseDTO login(LoginRequestDTO request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new LoginResponseDTO(token);
    }
    public String callTestService() {
        return testClient.testCall();
    }
}