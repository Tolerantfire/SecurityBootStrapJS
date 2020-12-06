package boot.crud.securityBoot.service;


import boot.crud.securityBoot.dao.UserDao;
import boot.crud.securityBoot.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserDao userDAO;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDAO, BCryptPasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.addUser(user);
    }

    @Override
    public List<?> queryForUser() {
       return userDAO.queryForUser();
    }

    @Override
    public void removeUser(int id) {
        userDAO.removeUser(id);
    }

    @Override
    public void editUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.editUser(user);
    }

    @Override
    public User findByUsername(String username) {
       return userDAO.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public void addUserByAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.addUserByAdmin(user);
    }

    @Override
    public User getById(int id) {
        return userDAO.getById(id);
    }
}
