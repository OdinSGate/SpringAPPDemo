package ru.spring.demo.dao;


import ru.spring.demo.model.User;

import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();

    void createUser(User user);

    void updateUser(User user);

    User readUser(long id);

    void deleteUser(long id);

    User getItemByUsername(String firstName);

    User getItemById(Long id);

    User getItemByEmail(String firstName);
}
