package com.is1g6.backend.user;

import com.is1g6.backend.model.User;
import com.is1g6.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        User user = new User("user.one@example.com", "password123", "BASIC", "User", "One", 30, "picture.jpg", "Male", "El obelisco");
        User savedUser = userRepository.save(user);

        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getName()).isEqualTo("User");
    }

    @Test
    public void testFindUserByUsername() {
        User user = new User("user.one@example.com", "password123", "ADMIN", "User", "One", 30, "picture.jpg", "Male", "El obelisco");
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByUsername("user.one@example.com");

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getSurname()).isEqualTo("One");
    }

    @Test
    public void testDeleteUser() {
        User user = new User("user.one@example.com", "password123", "BASIC", "User", "One", 30, "picture.jpg", "Male", "El obelisco");
        userRepository.save(user);

        userRepository.delete(user);

        Optional<User> deletedUser = userRepository.findById(user.getId());
        assertThat(deletedUser).isEmpty();
    }
}
