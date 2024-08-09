package itu.mbds.transversal.controller;

import itu.mbds.transversal.entity.ItemCategory;
import itu.mbds.transversal.service.ObjectCategory.ItemCategoryService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/item-category")
public class ItemCategoryController {

    private final ItemCategoryService itemCategoryService;

    public ItemCategoryController(ItemCategoryService itemCategoryService) {

        this.itemCategoryService = itemCategoryService;

    }

    @PostMapping()
    public ResponseEntity<ItemCategory> saveItemCategory(@RequestBody ItemCategory itemCategory) {

        ItemCategory saved = itemCategoryService.save(itemCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);

    }

    @GetMapping("{id}")
    public ResponseEntity<ItemCategory> getItemCategory(@PathVariable int id) {

        ItemCategory itemCategory = itemCategoryService.findById(id);

        if (itemCategory == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
    public ResponseEntity<?> updateItemCategory(@PathVariable int id, @RequestBody ItemCategory itemCategory) {

        ItemCategory updated = itemCategoryService.update(id, itemCategory);

        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(updated);

    }

    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteItemCategory(@PathVariable int id) {

        itemCategoryService.delete(id);

    }
}
