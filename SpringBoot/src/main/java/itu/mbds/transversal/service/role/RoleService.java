package itu.mbds.transversal.service.role;


import itu.mbds.transversal.entity.Role;
import itu.mbds.transversal.repository.RoleRepo;
import itu.mbds.transversal.utils.enumeration.RoleValue;
import org.springframework.stereotype.Component;

@Component
public class RoleService {

    private final RoleRepo roleRepo;

    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public Role getUserRole() {
        return roleRepo.findByAuthority(RoleValue.USER.name());
    }

    public Role getAdminRole() {
        return roleRepo.findByAuthority(RoleValue.ADMIN.name());
    }
}
