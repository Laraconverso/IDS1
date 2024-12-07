package com.is1g6.backend.service;

import com.is1g6.backend.dto.TokenDTO;
import com.is1g6.backend.model.Pedido;
import com.is1g6.backend.model.User;
import com.is1g6.backend.model.UserCredentials;
import com.is1g6.backend.repository.UserRepository;
import com.is1g6.backend.security.JwtService;
import com.is1g6.backend.security.JwtUserDetails;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final EmailService emailService;

    public Optional<User> findUserByUsername(String email) {
        return userRepository.findByUsername(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> {
                    var msg = String.format("Username '%s' not found", username);
                    return new UsernameNotFoundException(msg);
                });
    }

    public Optional<TokenDTO> createUser(User user) {
        if (userRepository.findByUsername(user.username()).isPresent()) {
            return loginUser(user);
        } else {
            user.setPassword(passwordEncoder.encode(user.password()));
            user.setRole("USER");
            userRepository.save(user);
            return Optional.of(generateTokens(user));
        }
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(userDetails.getName());
        user.setSurname(userDetails.getSurname());
        user.setUsername(userDetails.getUsername());
        user.setAge(userDetails.getAge());
        user.setPicture(userDetails.getPicture());
        user.setGender(userDetails.getGender());
        user.setAddress(userDetails.getAddress());
       // user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public Optional<TokenDTO> loginUser(UserCredentials data) {
        Optional<User> maybeUser = userRepository.findByUsername(data.username());
        return maybeUser
                .filter(user -> passwordEncoder.matches(data.password(),  user.password()))
                .map(this::generateTokens);
    }

    private TokenDTO generateTokens(User user) {
        String accessToken = jwtService.createToken(new JwtUserDetails(
                user.getUsername(),
                user.getRole()
        ));
        String userRole = user.getRole();
        return new TokenDTO(accessToken, userRole);
    }

    @Value("${app.port:8080}")
    private String port;

    public void sendPasswordResetEmail(String email) {
        Optional<User> userOptional = userRepository.findByUsername(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            logger.info("User found: {}", user);

            TokenDTO tokenDTO = this.generateTokens(user);


//            String resetUrl = "http://localhost:8080/reset-password?token=" + tokenDTO.accessToken();
            String resetUrl = "http://localhost:" + port + "/reset-password?token=" + tokenDTO.accessToken();
            String subject = "Restablecimiento de contraseña";
            String text = "Haz clic en el enlace para restablecer tu contraseña: " + resetUrl;
            emailService.sendEmail(email, subject, text);
        } else {
            throw new RuntimeException("Usuario no encontrado con el correo: " + email);
        }
    }

    public void resetPassword(String token, String newPassword) throws Exception {
        logger.info("Iniciando el proceso de restablecimiento de contraseña para el token: {}", token);
        Optional<JwtUserDetails> userDetailsOpt = jwtService.extractVerifiedUserDetails(token);

        logger.info("New passowrd {}", newPassword);

        logger.info("Token verificado: {}", userDetailsOpt);

        if (userDetailsOpt.isEmpty()) {
            throw new Exception("Token no válido");
        }

        JwtUserDetails userDetails = userDetailsOpt.get();
        logger.info("Token verificado para el usuario: {}", userDetails.username());
        Optional<User> userOpt = userRepository.findByUsername(userDetails.username());

        if (userOpt.isEmpty()) {
            throw new Exception("Usuario no encontrado");
        }

        User user = userOpt.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

}
