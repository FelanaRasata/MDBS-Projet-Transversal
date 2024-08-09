package itu.mbds.transversal.service.itemCategory;

import itu.mbds.transversal.entity.ItemCategory;
import itu.mbds.transversal.repository.ItemCategoryRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ItemCategoryService {

    @Value("${pageable.size}")
    private int pageSize;

    private final ItemCategoryRepo itemCategoryRepo;

    public ItemCategoryService(ItemCategoryRepo itemCategoryRepo) {
        this.itemCategoryRepo = itemCategoryRepo;
    }

    public ItemCategory findById(long id) {
        Optional<ItemCategory> categoryOptional = itemCategoryRepo.findById(id);
        return categoryOptional.orElse(null);
    }

    public Page<ItemCategory> findAll(int page, String keyword) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return itemCategoryRepo.findByTitleContainsIgnoreCase(keyword, pageable);
    }

    public boolean exist(long id) {
        return itemCategoryRepo.existsById(id);
    }

    public ItemCategory save(ItemCategory itemCategory) {
        return saveOrUpdate(itemCategory);
    }

    private ItemCategory saveOrUpdate(ItemCategory itemCategory) {
        return itemCategoryRepo.save(itemCategory);
    }

    public void delete(long id) {
        itemCategoryRepo.deleteById(id);
    }

    public ItemCategory update(long id, ItemCategory itemCategory) {
        boolean exist = exist(id);
        if (!exist) {
            return null;
        }
        return saveOrUpdate(itemCategory);

    }
}
