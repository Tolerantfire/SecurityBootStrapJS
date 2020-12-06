package boot.crud.securityBoot.controller;

import boot.crud.securityBoot.model.Role;
import boot.crud.securityBoot.model.User;
import boot.crud.securityBoot.service.RoleService;
import boot.crud.securityBoot.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userDAO, RoleService roleDAO) {
        this.userService = userDAO;
        this.roleService = roleDAO;
    }


    @GetMapping()
    public String showAllUsers(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(authentication.getName());
        if (authentication.getAuthorities().toString().contains("ROLE_ADMIN")) {
            model.addAttribute("roleAdmin", "ADMIN");
        } else {
            model.addAttribute("roleAdmin", "");
        }
        if (authentication.getAuthorities().toString().contains("ROLE_USER")) {
            model.addAttribute("roleUser", "USER");
        } else {
            model.addAttribute("roleUser", "");
        }
        model.addAttribute("loginedUserEmail", user.getEmail());
        model.addAttribute("loginedUserRoles", authentication.getAuthorities());
        model.addAttribute("currentUsers", userService.queryForUser());
        model.addAttribute("user", new User());
        return "admin/showList";
    }

    @GetMapping(value = "add")
    public String newUser(Model model) {
        model.addAttribute("currentUsers", userService.queryForUser());
        model.addAttribute("user", new User());
        model.addAttribute("flag1", false);
        model.addAttribute("flag2", false);
        return "admin/add";
    }

    @PostMapping(value = "add")
    public String addUser(@ModelAttribute("User") User user, @RequestParam List<String> SelectedSortOrderOptions) {
        Set<Role> roles = new HashSet<>();
        if (SelectedSortOrderOptions.contains("User")) {
            roles.add(roleService.getById(2));
            user.setRoles(roles);
        }
        if (SelectedSortOrderOptions.contains("Admin")) {
            roles.add(roleService.getById(1));
            user.setRoles(roles);
        }
        userService.addUserByAdmin(user);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/edit", method = {RequestMethod.POST, RequestMethod.GET})
    public String update(User user, @RequestParam List<String> SelectedSortOrderOptions) {
        Set<Role> roles = new HashSet<>();
        if (SelectedSortOrderOptions.contains("User")) {
            roles.add(roleService.getById(2));
            user.setRoles(roles);
        }
        if (SelectedSortOrderOptions.contains("Admin")) {
            roles.add(roleService.getById(1));
            user.setRoles(roles);
        }
        userService.editUser(user);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public String delete(User user) {
        userService.removeUser(user.getId());
        return "redirect:/admin";
    }

    @GetMapping("/getOne/")
    @ResponseBody
    public User getOne(Integer id) {
        User user = userService.getById(id);
        System.out.println("Юзер получен: " + user.getName());
        return user;
    }
}


//версии методов контроллера из предыдущих решений:

 /*@GetMapping(value = "edit/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        User user = userService.getById(id);
        model.addAttribute("user", user);
        model.addAttribute("flag1", true);
        model.addAttribute("flag2", false);
        return "admin/edit";
    }*/

    /*@PostMapping(value = "edit")
    public String editUser(@ModelAttribute("User") User user, @RequestParam List<String> SelectedSortOrderOptions) {

        Set<Role> roles = new HashSet<>();
        if (SelectedSortOrderOptions.contains("User")){
            roles.add(roleService.getById(2));
            user.setRoles(roles);
        }
        if (SelectedSortOrderOptions.contains("Admin")){
            roles.add(roleService.getById(1));
            user.setRoles(roles);
        }
        userService.editUser(user);
        return "redirect:/admin";
    }*/
/* @GetMapping(value = "deleteUser/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }*/