package boot.crud.securityBoot.controller;


import boot.crud.securityBoot.model.Role;
import boot.crud.securityBoot.model.User;
import boot.crud.securityBoot.service.RoleService;
import boot.crud.securityBoot.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

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

        model.addAttribute("currentUsers", userService.queryForUser());
        model.addAttribute("user", new User());
        return "admin/showList";
    }

    @GetMapping(value = "deleteUser/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }

    @GetMapping(value = "add")
    public String newUser(Model model) {
        model.addAttribute("currentUsers", userService.queryForUser());
        model.addAttribute("user", new User());
        model.addAttribute("flag1", true);
        model.addAttribute("flag2", false);
        return "admin/add";
    }

    @PostMapping(value = "add")
    public String addUser(@RequestParam(value = "ROLE_USER_checkbox", required = false) String ROLE_USER_checkbox,
                          @RequestParam(value = "ROLE_ADMIN_checkbox", required = false) String ROLE_ADMIN_checkbox,
                          @ModelAttribute("User") User user) {
        Set<Role> roles = new HashSet<>();
        if (ROLE_ADMIN_checkbox != null) {
            roles.add(roleService.getById(1));
            user.setRoles(roles);
        }
        if (ROLE_USER_checkbox != null) {
            roles.add(roleService.getById(2));
            user.setRoles(roles);
        }
        userService.addUserByAdmin(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "edit/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        User user = userService.getById(id);
        model.addAttribute("user", user);
        model.addAttribute("flag1", true);
        model.addAttribute("flag2", false);
        return "admin/edit";
    }

    @PostMapping(value = "edit")
    public String editUser(@RequestParam(value = "ROLE_USER_checkbox", required = false) String ROLE_USER_checkbox,
                           @RequestParam(value = "ROLE_ADMIN_checkbox", required = false) String ROLE_ADMIN_checkbox,
                           @ModelAttribute("User") User user) {

        Set<Role> roles = new HashSet<>();
        if (ROLE_ADMIN_checkbox != null) {
            roles.add(roleService.getById(1));
            user.setRoles(roles);
        }
        if (ROLE_USER_checkbox != null) {
            roles.add(roleService.getById(2));
            user.setRoles(roles);
        }
        userService.editUser(user);
        return "redirect:/admin";
    }




}
