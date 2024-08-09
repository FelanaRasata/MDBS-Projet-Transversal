package itu.mbds.transversal.repository;

import itu.mbds.transversal.entity.ItemCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCategoryRepo extends JpaRepository<ItemCategory, Long> {

    Page<ItemCategory> findByTitleContainsIgnoreCase(String keyword, Pageable pageable);
}
