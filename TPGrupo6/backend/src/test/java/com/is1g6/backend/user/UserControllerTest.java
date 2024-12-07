package com.is1g6.backend.user;

import com.is1g6.backend.controller.UserController;
import com.is1g6.backend.dto.TokenDTO;
import com.is1g6.backend.model.User;
import com.is1g6.backend.security.JwtService;
import com.is1g6.backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@WithMockUser(username = "testUser", roles = "USER")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService;

    @Test
    public void testGetUserById() throws Exception {
        User user = new User("susana@gmail.com", "123");
        user.setId(1L);
        user.setName("Susana");
        user.setSurname("Doe");

        given(userService.findUserById(1L)).willReturn(Optional.of(user));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("susana@gmail.com"))
                .andExpect(jsonPath("$.name").value("Susana"))
                .andExpect(jsonPath("$.surname").value("Doe"));
    }

    @Test
    public void testRegisterUser() throws Exception {
        given(userService.createUser(any(User.class))).willReturn(Optional.of(new TokenDTO("accessToken123", "USER")));

        mockMvc.perform(post("/users/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"newuser@gmail.com\", \"password\": \"password123\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accessToken").value("accessToken123"))
                .andExpect(jsonPath("$.userRole").value("USER"));
    }

    @Test
    public void testRegisterUserConflict() throws Exception {
        given(userService.createUser(any(User.class))).willReturn(Optional.empty());

        mockMvc.perform(post("/users/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"conflictuser@gmail.com\", \"password\": \"password123\"}"))
                .andExpect(status().isConflict());
    }

    @Test
    public void testLoginUser() throws Exception {
        TokenDTO tokenDTO = new TokenDTO("loginToken", "ADMIN");

        // Cambiar el uso de UserLoginDTO por un Map directamente en el mock
        given(userService.loginUser(any())).willReturn(Optional.of(tokenDTO));

        mockMvc.perform(post("/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"loginuser@gmail.com\", \"password\": \"mypassword\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("loginToken"))
                .andExpect(jsonPath("$.userRole").value("ADMIN"));
    }

    @Test
    public void testLoginUserNotFound() throws Exception {
        given(userService.loginUser(any())).willReturn(Optional.empty());

        mockMvc.perform(post("/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"unknown@gmail.com\", \"password\": \"wrongpassword\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteUser() throws Exception {
        given(userService.findUserById(1L)).willReturn(Optional.of(new User()));
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/users/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("User with id 1 successfully deleted."));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateUser() throws Exception {
        User updatedUser = new User("updateduser@gmail.com", "newpassword123");
        updatedUser.setId(1L);

        given(userService.updateUser(any(Long.class), any(User.class))).willReturn(updatedUser);

        mockMvc.perform(put("/users/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"updateduser@gmail.com\", \"password\": \"newpassword123\"}"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.username").value("updateduser@gmail.com"));
    }

    @Test
    public void testForgotPassword() throws Exception {
        mockMvc.perform(post("/users/forgot-password")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"test@gmail.com\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Correo de restablecimiento enviado."));
    }

    @Test
    public void testResetPassword() throws Exception {
        mockMvc.perform(post("/users/reset-password")
                        .with(csrf())
                        .param("token", "testToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"password\": \"newpassword\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Contraseña restablecida correctamente"));
    }
}
