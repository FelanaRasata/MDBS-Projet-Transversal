package itu.mbds.transversal.service.user;

import itu.mbds.transversal.entity.Role;
import itu.mbds.transversal.entity.User;
import itu.mbds.transversal.repository.UserRepo;
import itu.mbds.transversal.service.role.RoleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    @Value("${pageable.size}")
    private int pageSize;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    private final UserRepo userRepo;

    public UserServiceImpl(PasswordEncoder passwordEncoder, RoleService roleService, UserRepo userRepo) {
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.userRepo = userRepo;
    }

    private User saveWithRole(User user, Role role) {
        user.setUsername(user.getUsername().toLowerCase());
        user.setRoles(new HashSet<>(Collections.singletonList(role)));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public User saveUser(User user) {
        user.setEnabled(false);
        Role role = roleService.getUserRole();
        return this.saveWithRole(user, role);
    }

    @Override
    public User saveAdmin(User user) {
        user.setEnabled(true);
        Role role = roleService.getAdminRole();
        return this.saveWithRole(user, role);
    }

    @Override
    public User findById(int id) {
        Optional<User> userOptional = userRepo.findById(id);
        return userOptional.orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsernameIgnoreCase(username);
    }

    @Override
    public Page<User> findAll(int page, String keyword) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return userRepo.findByNameContainsIgnoreCase(keyword, pageable);
    }

    @Override
    public void delete(int id) {
        userRepo.deleteById(id);
    }

    @Override
    public User update(int id, User user) {
        User userUpdate = findById(id);

        if (userUpdate == null)
            return null;

        userUpdate.setId(id);
        userUpdate.setUsername(user.getUsername());
        userUpdate.setContact(user.getContact());
        userUpdate.setName(user.getName());

        return userRepo.save(userUpdate);
    }

    @Override
    public User enable(int id) {

        User user = findById(id);
        if (user == null) {
            return null;
        }
        user.setEnabled(true);

        return userRepo.save(user);

    }

}
