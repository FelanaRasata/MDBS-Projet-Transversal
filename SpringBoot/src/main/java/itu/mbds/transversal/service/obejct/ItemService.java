package itu.mbds.transversal.service.obejct;

import itu.mbds.transversal.entity.Item;
import org.springframework.data.domain.Page;

public interface ItemService {

    void deleteById(long id);

    Item save(Item item);

    Page<Item> findAll(int page);

    Item findById(int id);
}
