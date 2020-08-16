package com.ashrapov.service;

import com.ashrapov.model.document.User;

public interface UserService {
    User save(User user);

    void deleteById(String id);

    User findById(String id);
}
