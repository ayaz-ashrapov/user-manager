package com.ashrapov.service;

import com.ashrapov.model.document.User;
import com.ashrapov.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setFirstName("first");
        user.setLastName("last");
        user.setId("unique-id");

        Mockito.when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));
    }

    @Test
    public void whenIdProvided_thenUserShouldBeFound() {
        String id = "unique-id";

        User user = userService.findById(id);

        assertEquals(id, user.getId());
    }

    @Test
    public void whenUserCreated_thenIdShouldBeAssigned() {
        User user = new User();

        user.setFirstName("firstname");
        user.setLastName("lastname");
        user.setEmail("some@generic.mail");

        Answer<User> answer = invocation -> {
            User user1 = invocation.getArgument(0, User.class);
            user1.setId(UUID.randomUUID().toString());

            return user1;
        };

        doAnswer(answer).when(userRepository).save(user);

        User userCreated = userService.save(user);

        assertTrue(userCreated.getId() != null && !userCreated.getId().isEmpty());
    }

    @Test
    public void whenUserDeleted_thenRepositoryMethodIsCalled() {
        String uniqueId = UUID.randomUUID().toString();
        userService.deleteById(uniqueId);

        verify(userRepository, times(1)).deleteById(uniqueId);
    }

    @TestConfiguration
    static class UserServiceImplTestConfiguration {
        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }
}
