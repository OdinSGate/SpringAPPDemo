package ru.spring.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.spring.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();

    User readUser(long id);

    void deleteUser(long id);

    void createUser(User user);

    void updateUser(User user);

    User loadUserById(Long id) throws UsernameNotFoundException;
}
