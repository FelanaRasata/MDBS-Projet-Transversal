package itu.mbds.transversal.repository;

import itu.mbds.transversal.entity.Item;
import itu.mbds.transversal.entity.ItemCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepo extends JpaRepository<Item, Long> {

    Page<Item> findByNameContainsIgnoreCase(String keyword, Pageable pageable);

    Page<Item> findByUserIdAndNameContainsIgnoreCase(long userId, String keyword, Pageable pageable);

}
