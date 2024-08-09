package itu.mbds.transversal.service.ObjectCategory;

import itu.mbds.transversal.entity.ItemCategory;
import org.springframework.data.domain.Page;

public interface ItemCategoryService {

    ItemCategory findById(int id);

    Page<ItemCategory> findAll(int page, String keyword);

    boolean exist(int id);

    ItemCategory save(ItemCategory itemCategory);

    void delete(int id);

    ItemCategory update(int id,ItemCategory itemCategory);
}
