package com.lesson49.service;

import com.lesson49.entity.User;

import java.util.List;

public interface UserService {
    public List<User> getAllUser();
    public User getUserById(int id);

    public void saveUser(User user);

    public void deleteUser(int id);
}
