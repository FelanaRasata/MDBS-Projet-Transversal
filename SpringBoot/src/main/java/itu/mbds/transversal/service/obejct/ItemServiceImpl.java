package itu.mbds.transversal.service.obejct;

import itu.mbds.transversal.entity.Item;
import itu.mbds.transversal.repository.ItemRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ItemServiceImpl implements ItemService {

    private final ItemRepo itemRepo;

    public ItemServiceImpl(ItemRepo itemRepo) {
        this.itemRepo = itemRepo;
    }

    private Item saveOrUpdate(Item item) {
        return itemRepo.save(item);
    }

    @Override
    public void deleteById(long id) {
        itemRepo.deleteById(id);
    }

    @Override
    public Item save(Item item) {
        item.setValidated(false);
        return saveOrUpdate(item);
    }

    @Override
    public Page<Item> findAll(int page) {
        Pageable pageable = PageRequest.of(page, 2);
        return itemRepo.findAll(pageable);
    }

    @Override
    public Item findById(int id) {
        Optional<Item> objectOptional = itemRepo.findById((long) id);
        if (objectOptional.isPresent())
            return objectOptional.get();
        throw new NullPointerException("Objet Id invalide : " + id);
    }
}
