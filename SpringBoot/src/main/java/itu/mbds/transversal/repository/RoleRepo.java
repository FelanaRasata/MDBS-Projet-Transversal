package itu.mbds.transversal.repository;

import itu.mbds.transversal.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByAuthority(String authority);
}