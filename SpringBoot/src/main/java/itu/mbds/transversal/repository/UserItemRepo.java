package itu.mbds.transversal.repository;

import itu.mbds.transversal.entity.Item;
import itu.mbds.transversal.entity.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserItemRepo extends JpaRepository<UserItem, Long> {


    void deleteByItem(Item item);
}
