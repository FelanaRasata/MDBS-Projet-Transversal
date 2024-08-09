package itu.mbds.transversal.service.ObjectCategory;

import itu.mbds.transversal.entity.ItemCategory;
import itu.mbds.transversal.repository.ItemCategoryRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ItemCategoryServiceImpl implements ItemCategoryService {

    @Value("${pageable.size}")
    private int pageSize;

    private final ItemCategoryRepo itemCategoryRepo;

    public ItemCategoryServiceImpl(ItemCategoryRepo itemCategoryRepo) {
        this.itemCategoryRepo = itemCategoryRepo;
    }

    @Override
    public ItemCategory findById(int id) {
        Optional<ItemCategory> categoryOptional = itemCategoryRepo.findById(id);
        return categoryOptional.orElse(null);
    }

    @Override
    public Page<ItemCategory> findAll(int page, String keyword) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return itemCategoryRepo.findByTitleContainsIgnoreCase(keyword, pageable);
    }

    @Override
    public boolean exist(int id) {
        return itemCategoryRepo.existsById(id);
    }

    @Override
    public ItemCategory save(ItemCategory itemCategory) {
        return saveOrUpdate(itemCategory);
    }

    private ItemCategory saveOrUpdate(ItemCategory itemCategory) {
        return itemCategoryRepo.save(itemCategory);
    }

    @Override
    public void delete(int id) {
        itemCategoryRepo.deleteById(id);
    }

    @Override
    public ItemCategory update(int id, ItemCategory itemCategory) {
        boolean exist = exist(id);
        if (!exist) {
            return null;
        }
        return saveOrUpdate(itemCategory);

    }
}
