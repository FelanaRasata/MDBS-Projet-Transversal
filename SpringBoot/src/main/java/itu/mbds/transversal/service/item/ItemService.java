package itu.mbds.transversal.service.item;

import itu.mbds.transversal.entity.Item;
import itu.mbds.transversal.entity.ItemCategory;
import itu.mbds.transversal.entity.User;
import itu.mbds.transversal.entity.UserItem;
import itu.mbds.transversal.repository.ItemRepo;
import itu.mbds.transversal.service.itemCategory.ItemCategoryService;
import itu.mbds.transversal.service.user.UserService;
import itu.mbds.transversal.utils.enumeration.EntityValue;
import itu.mbds.transversal.utils.enumeration.Message;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ItemService {

    @Value("${pageable.size}")
    private int pageSize;

    private final ItemRepo itemRepo;

    private final UserItemService userItemService;

    private final ItemCategoryService itemCategoryService;

    private final UserService userService;

    private final FileService fileService;

    public ItemService(ItemRepo itemRepo, UserItemService userItemService, ItemCategoryService itemCategoryService, UserService userService, FileService fileService) {
        this.itemRepo = itemRepo;
        this.userItemService = userItemService;
        this.itemCategoryService = itemCategoryService;
        this.userService = userService;
        this.fileService = fileService;
    }

    private Item saveOrUpdate(Item item) {
        return itemRepo.save(item);
    }

    public void deleteById(long id) {
        itemRepo.deleteById(id);
    }

    public Item save(Item item, long userId, MultipartFile multipartFile) {

        String filename = fileService.uploadFile(multipartFile);
        item.setPicture(filename);

        User user = userService.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException(Message.NOT_FOUND.byId(EntityValue.USER, userId));
        }
        item.setUser(user);

        ItemCategory itemCategory = item.getItemCategory();
        if (itemCategory == null) {
            throw new IllegalArgumentException(Message.NOT_FOUND.byEntity(EntityValue.ITEM_CATEGORY));
        }
        if (!itemCategoryService.exist(itemCategory.getId())) {
            throw new IllegalArgumentException(Message.NOT_FOUND.byId(EntityValue.ITEM_CATEGORY, itemCategory.getId()));
        }


        item.setEnabled(false);

        Item itemSaved = saveOrUpdate(item);

        userItemService.save(user, itemSaved);
        return itemSaved;

    }


    public Item update(long id, Item item, MultipartFile multipartFile) {

        Item itemUpdate = findById(id);

        if (itemUpdate == null)
            return null;

        if (multipartFile != null) {
            String filename = fileService.uploadFile(multipartFile);
            itemUpdate.setPicture(filename);
        }

        ItemCategory itemCategory = item.getItemCategory();

        if (itemCategory != null) {

            if (!itemCategoryService.exist(itemCategory.getId())) {
                throw new IllegalArgumentException(Message.NOT_FOUND.byId(EntityValue.ITEM_CATEGORY, itemCategory.getId()));
            }

            itemUpdate.setItemCategory(itemCategory);

        }

        itemUpdate.setName(item.getName());
        itemUpdate.setDescription(item.getDescription());

        itemUpdate.setEnabled(false);

        return saveOrUpdate(itemUpdate);

    }

    public Page<Item> findAll(int page, String keyword) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return itemRepo.findByNameContainsIgnoreCase(keyword, pageable);
    }

    public Page<Item> findByUser(int page, long userId, String keyword) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return itemRepo.findByUserIdAndNameContainsIgnoreCase(userId, keyword, pageable);
    }


    public Item findById(long id) {
        Optional<Item> objectOptional = itemRepo.findById(id);
        return objectOptional.orElse(null);
    }

    public List<Item> enable(List<Item> items) {

        List<Item> itemList = new ArrayList<>();

        for (Item item : items) {
            Item itemTemp = findById(item.getId());
            if (itemTemp != null) {
                itemTemp.setEnabled(!itemTemp.getEnabled());
                itemList.add(itemTemp);
            }
        }

        return itemRepo.saveAll(itemList);

    }

    public void changeOwner(User newer, List<Item> items) {

        for (Item item : items) {

            Item itemTemp = findById(item.getId());
            itemTemp.setUser(newer);
            saveOrUpdate(itemTemp);
            userItemService.save(newer, itemTemp);

        }
    }

    @Transactional
    public void delete(long id) {

        Item item = findById(id);
        userItemService.deleteByItem(item);
        itemRepo.deleteById(id);
    }
}
