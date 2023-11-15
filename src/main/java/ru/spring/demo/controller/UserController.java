package ru.spring.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.spring.demo.model.User;
import ru.spring.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/logout")
    public String logoutButton() {
        return "redirect:/login";
    }

    @GetMapping("/user/{id}")
    public String showUserPage(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.readUser(id));
        return "/user/index";
    }

    @GetMapping(value = "/userinfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<User> getAllUsers() {
        return this.userService.getAllUsers();
    }
}
