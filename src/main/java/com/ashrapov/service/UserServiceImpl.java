package com.ashrapov.service;

import com.ashrapov.error.UserNotFoundException;
import com.ashrapov.model.document.User;
import com.ashrapov.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findById(String id) {
        User user = userRepository.findById(id).orElseThrow((UserNotFoundException::new));
        return user;
    }

}
