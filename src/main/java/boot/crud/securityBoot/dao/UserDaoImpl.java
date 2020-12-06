package boot.crud.securityBoot.dao;


import boot.crud.securityBoot.model.Role;
import boot.crud.securityBoot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RoleDao roleDao;

    @Override
    public List<?> queryForUser() {
        List<?> users = entityManager.createQuery("SELECT user from User user").getResultList();
        System.out.println(users.size());
        return users;
    }

    @Override
    public void removeUser(int id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public void editUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void addUser(User user) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.getById(2));
        user.setRoles(roles);
        entityManager.persist(user);
        System.out.println("User с именем " + user.getName() + "и ролями" + user.getRoles() +" добавлен в базу данных");
    }

    @Override
    public void addUserByAdmin(User user) {
        entityManager.persist(user);
    }

    @Override
    public User findByUsername(String username) {
        System.out.println("вошли в метод findByUsername");
        User user = (User) entityManager
                .createQuery("select u from User u where u.name like :username")
                .setParameter("username", username)
                .getSingleResult();
        System.out.println("User с именем " + user.getName() + "и ролями" + user.getAuthorities() + "получен из базы");
        return user;
    }
    @Override
    public User findByEmail (String email) {
        System.out.println("вошли в метод findByEmail");
        User user = (User) entityManager
                .createQuery("select u from User u where u.email like :email")
                .setParameter("email", email)
                .getSingleResult();
        System.out.println("User с именем " + user.getName() + "и ролями" + user.getAuthorities() + "получен из базы");
        return user;
    }

    @Override
    public User getById(int id) {
        return entityManager.find(User.class, id);
    }
}
