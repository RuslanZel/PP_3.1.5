package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;

    public AdminController(UserServiceImpl userServiceImpl, RoleServiceImpl roleServiceImpl) {
        this.userService = userServiceImpl;
        this.roleService = roleServiceImpl;
    }

    @GetMapping
    public String getUsers(Model model, Principal principal) {
        User userPrincipal = userService.findByName(principal.getName());
        model.addAttribute("userPrincipal", userPrincipal);
        model.addAttribute("newUser", new User());
        model.addAttribute("allUsers", userService.allUsers());
        model.addAttribute("roles", roleService.allRoles());
        return "admin";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.allRoles());
        return "new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/admin";
    }


    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("edit", userService.getUser(id));
        model.addAttribute("roles", roleService.allRoles());
        return "edit";
    }


    @PatchMapping("/{id}")
    public String update(User user, @PathVariable("id") Long id) {
        user.setId(id);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
