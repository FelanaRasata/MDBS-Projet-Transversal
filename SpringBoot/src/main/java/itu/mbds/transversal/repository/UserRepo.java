package itu.mbds.transversal.repository;

import itu.mbds.transversal.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsernameIgnoreCase(String username);

    Page<User> findByNameContainsIgnoreCase(String keyword, Pageable pageable);
}