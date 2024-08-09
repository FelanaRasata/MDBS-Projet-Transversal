package itu.mbds.transversal.service.item;

import itu.mbds.transversal.entity.Item;
import itu.mbds.transversal.entity.User;
import itu.mbds.transversal.entity.UserItem;
import itu.mbds.transversal.repository.UserItemRepo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserItemService {

    private final UserItemRepo userItemRepo;

    public UserItemService(UserItemRepo userItemRepo) {
        this.userItemRepo = userItemRepo;
    }

    public void save(User user, Item item) {
        UserItem userItem = new UserItem();
        userItem.setItem(item);
        userItem.setUser(user);
        userItemRepo.save(userItem);
    }


    public void deleteByItem(Item item) {
        userItemRepo.deleteByItem(item);
    }
}
