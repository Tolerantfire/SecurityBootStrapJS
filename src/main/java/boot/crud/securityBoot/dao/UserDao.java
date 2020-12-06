package boot.crud.securityBoot.dao;



import boot.crud.securityBoot.model.User;

import java.util.List;

public interface UserDao {
    void addUser(User user);

    List<?> queryForUser();

    void removeUser(int id);

    void editUser(User user);

    User findByUsername(String username);
    User findByEmail(String email);

    void addUserByAdmin(User user);

    User getById(int id);
}
