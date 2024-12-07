package com.is1g6.backend.user;

import com.is1g6.backend.dto.TokenDTO;
import com.is1g6.backend.model.User;
import com.is1g6.backend.model.UserCredentials;
import com.is1g6.backend.repository.UserRepository;
import com.is1g6.backend.security.JwtService;
import com.is1g6.backend.service.EmailService;
import com.is1g6.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User("user.one@example.com", "password123");
        user.setId(1L);
        user.setRole("USER");
        user.setName("User");
        user.setSurname("One");
        user.setAge(30);
        user.setPicture("picture.jpg");
        user.setGender("Male");
        user.setAddress("El obelisco");
    }

    @Test
    public void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findUserById(1L);

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo("User");

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteUser() {
        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testCreateUserNewUser() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(jwtService.createToken(any())).thenReturn("testToken");

        Optional<TokenDTO> tokenDTO = userService.createUser(user);

        assertThat(tokenDTO).isPresent();
        assertThat(tokenDTO.get().accessToken()).isEqualTo("testToken");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateUser_Success() {
        User updatedDetails = new User();
        updatedDetails.setName("Updated Name");
        updatedDetails.setSurname("Updated Surname");
        updatedDetails.setUsername("updated.email@example.com");
        updatedDetails.setAge(35);
        updatedDetails.setPicture("updated_picture.jpg");
        updatedDetails.setGender("Other");
        updatedDetails.setAddress("Updated Address");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = userService.updateUser(1L, updatedDetails);

        assertThat(updatedUser.getName()).isEqualTo("Updated Name");
        assertThat(updatedUser.getSurname()).isEqualTo("Updated Surname");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateUser_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.updateUser(1L, user));
    }

    @Test
    public void testSendPasswordResetEmail_UserFound() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(jwtService.createToken(any())).thenReturn("resetToken");

        userService.sendPasswordResetEmail(user.getUsername());

        verify(emailService, times(1)).sendEmail(eq(user.getUsername()), eq("Restablecimiento de contraseña"), contains("resetToken"));
    }

    @Test
    public void testSendPasswordResetEmail_UserNotFound() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.sendPasswordResetEmail(user.getUsername()));
    }
}
