package boot.crud.securityBoot.service;


import boot.crud.securityBoot.dao.RoleDao;
import boot.crud.securityBoot.model.Role;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDAO;

    public RoleServiceImpl(RoleDao roleDAO) {
        this.roleDAO = roleDAO;
    }


    @Override
    public Role getById(int id) {
        return roleDAO.getById(id);
    }
}
