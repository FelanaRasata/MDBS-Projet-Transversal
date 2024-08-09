package itu.mbds.transversal.controller;

import itu.mbds.transversal.entity.ItemCategory;
import itu.mbds.transversal.service.itemCategory.ItemCategoryService;
import itu.mbds.transversal.utils.dto.response.ResponseMessage;
import itu.mbds.transversal.utils.enumeration.EntityValue;
import itu.mbds.transversal.utils.enumeration.Message;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/item-category")
public class ItemCategoryController {

    private final ItemCategoryService itemCategoryService;


    public ItemCategoryController(ItemCategoryService itemCategoryService) {

        this.itemCategoryService = itemCategoryService;

    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ItemCategory> saveItemCategory(@RequestBody ItemCategory itemCategory) {

        ItemCategory saved = itemCategoryService.save(itemCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);

    }

    @GetMapping("{id}")
    public ResponseEntity<?> getItemCategory(@PathVariable long id) {

        ItemCategory itemCategory = itemCategoryService.findById(id);

        if (itemCategory == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage(Message.NOT_FOUND.byId(EntityValue.ITEM_CATEGORY, id)));
        }

        return ResponseEntity.ok(itemCategory);

    }

    @GetMapping("")
    public ResponseEntity<Page<ItemCategory>> getItemCategories(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "") String keyword
    ) {

        Page<ItemCategory> itemCategories = itemCategoryService.findAll(page, keyword);
        return ResponseEntity.ok(itemCategories);

    }


    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateItemCategory(@PathVariable long id, @RequestBody ItemCategory itemCategory) {

        ItemCategory updated = itemCategoryService.update(id, itemCategory);

        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage(Message.NOT_FOUND.byId(EntityValue.ITEM_CATEGORY, id)));
        }

        return ResponseEntity.ok(updated);

    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteItemCategory(@PathVariable long id) {

        itemCategoryService.delete(id);

    }
}
