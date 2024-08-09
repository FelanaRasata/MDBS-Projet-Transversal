package itu.mbds.transversal.service.role;


import itu.mbds.transversal.entity.Role;
import itu.mbds.transversal.repository.RoleRepo;
import itu.mbds.transversal.utils.enumeration.RoleValue;
import org.springframework.stereotype.Component;

@Component
public class RoleServiceImpl implements RoleService {

    private final RoleRepo roleRepo;

    public RoleServiceImpl(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public Role getUserRole() {
        return roleRepo.findByAuthority(RoleValue.USER.name());
    }

    @Override
    public Role getAdminRole() {
        return roleRepo.findByAuthority(RoleValue.ADMIN.name());
    }
}
