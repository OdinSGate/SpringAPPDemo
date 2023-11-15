package ru.spring.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.spring.demo.dao.UserDAO;
import ru.spring.demo.model.Role;
import ru.spring.demo.model.User;
import ru.spring.demo.repository.RoleRepo;
import ru.spring.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping
public class AdminController {

    private final UserService userService;
    private final UserDAO userDAO;
    private final RoleRepo roleRepo;

    public AdminController(UserService userService, UserDAO userDAO, RoleRepo roleRepo) {
        this.userService = userService;
        this.userDAO = userDAO;
        this.roleRepo = roleRepo;
    }

    @GetMapping("/index")
    public String showAdminPage() {
        return "/index";
    }

    @GetMapping({"", "/", "/users"})
    public String showAllUsers(Model model, @ModelAttribute("flashMessage") String flashAttribute) {
        model.addAttribute("users", userService.getAllUsers());
        return "/admin/users";
    }

    @GetMapping("/new")
    public String addUser(@ModelAttribute("user") User user, Model model) {
        List<Role> roles = roleRepo.findAll();
        model.addAttribute("roles", roles);
        return "/admin/new";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") User user) {
        userService.createUser(user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/{id}/edit")
    public String update(@PathVariable("id") long id, Model model) {
        List<Role> roles = roleRepo.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("user", userService.readUser(id));
        return "/admin/edit";
    }

    @PatchMapping("/users/{id}")
    public String updateUser(@ModelAttribute("user") @Validated User user) {
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping(value = "/currentUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody UserDetails getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return this.userService.loadUserByUsername(name);
    }

    @GetMapping(value = "/allUsers", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<User> allUsers(){
        return userService.getAllUsers();
    }
}
