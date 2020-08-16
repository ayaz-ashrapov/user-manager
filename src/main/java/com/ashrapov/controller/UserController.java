package com.ashrapov.controller;

import com.ashrapov.model.document.User;
import com.ashrapov.model.dto.UserDTO;
import com.ashrapov.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/")
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public UserDTO create(@Valid @RequestBody UserDTO userDTO) {
        User user = convertFromDto(userDTO);
        User userCreated = userService.save(user);
        return convertToDto(userCreated);
    }

    @GetMapping("/users/{id}")
    public UserDTO findById(@PathVariable("id") String id) {
        UserDTO userDTO = convertToDto(userService.findById(id));
        return userDTO;
    }


    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteById(id);
    }

    private User convertFromDto(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);

        return user;
    }


    private UserDTO convertToDto(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        return userDTO;
    }
}
