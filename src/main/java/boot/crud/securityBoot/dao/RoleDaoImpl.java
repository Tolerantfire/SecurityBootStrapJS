package boot.crud.securityBoot.dao;


import boot.crud.securityBoot.model.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role getById(int id) {
        Role role = entityManager.find(Role.class, id);
        return role;
    }
}
