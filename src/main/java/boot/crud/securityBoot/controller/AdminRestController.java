
package boot.crud.securityBoot.controller;

import boot.crud.securityBoot.model.Role;
import boot.crud.securityBoot.model.User;
import boot.crud.securityBoot.service.RoleService;
import boot.crud.securityBoot.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/")
public class AdminRestController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminRestController(UserService userDAO, RoleService roleDAO) {
        this.userService = userDAO;
        this.roleService = roleDAO;
    }

    @GetMapping("/users")
        public ResponseEntity<List<User>> getAllUsers (){                                        //Получить всех User
        List<User> userList = (List<User>) userService.queryForUser();

       if (userList == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }



    @GetMapping("/user")                                                                    //Получить User по Id
    public  ResponseEntity<User> getUserById(){
        System.out.println("вошли в рестюзер");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(authentication.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @DeleteMapping("user/{id}")                                                                 //Удалить по Id
    public ResponseEntity deleteUserById(@PathVariable (name = "id") Integer id) {
        User user = userService.getById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        userService.removeUser(id);
        return new ResponseEntity (HttpStatus.OK);
    }

    @PutMapping("/user")
    public ResponseEntity updateUser(@RequestBody User reqUser) {                           //обновить по User
        System.out.println("вошли в рестедит");
        User user = userService.getById(reqUser.getId());

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        userService.editUser(reqUser);
        return new ResponseEntity (HttpStatus.OK);
    }
                                                                                            //Добавляем User
    @PostMapping("/user")
    public ResponseEntity addUser(@RequestBody User user){
        System.out.println("вошли в рестADD");
        if (user == null) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        userService.addUserByAdmin(user);
        return new ResponseEntity(HttpStatus.OK);
    }

}

