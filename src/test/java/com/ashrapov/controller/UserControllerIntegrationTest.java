package com.ashrapov.controller;

import com.ashrapov.model.document.User;
import com.ashrapov.model.dto.UserDTO;
import com.ashrapov.repository.UserRepository;
import com.ashrapov.service.UserService;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void whenPostRequestToUsersAndValidUser_thenCorrectResponse() throws Exception {
        String email = "valid@gmail.com";
        String lastName = "last";
        String firstName = "first";
        String requestBody = String.format("{\"email\" : \"%s\",\"firstName\" : \"%s\",\"lastName\" : \"%s\"}",
                email, firstName, lastName);

        User user = new User();
        user.setEmail(email);
        user.setLastName(lastName);
        user.setFirstName(firstName);

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);

        Mockito.when(userService.save(any(User.class))).thenReturn(user);
        Mockito.when(modelMapper.map(any(UserDTO.class), any(Class.class))).thenReturn(user);
        Mockito.when(modelMapper.map(any(User.class), any(Class.class))).thenReturn(userDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Is.is(email)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Is.is(firstName)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Is.is(lastName)))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenPostRequestToUsersAndInValidUser_thenCorrectResponse() throws Exception {
        String userWithAllEmptyFields = "{\"email\" : \"\",\"firstName\" : \"first\",\"lastName\" : \"last\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .content(userWithAllEmptyFields)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]", Is.is("Email must not be empty")))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));

    }
}
