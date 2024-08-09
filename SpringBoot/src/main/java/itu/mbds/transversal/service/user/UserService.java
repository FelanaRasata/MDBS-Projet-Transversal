package itu.mbds.transversal.service.user;

import itu.mbds.transversal.entity.User;
import org.springframework.data.domain.Page;

public interface UserService {
    User saveUser(User user);

    User saveAdmin(User user);

    User findById(int id);

    User findByUsername(String username);

    Page<User> findAll(int page, String keyword);

    void delete(int id);

    User update(int id, User user);

    User enable(int id);
}
