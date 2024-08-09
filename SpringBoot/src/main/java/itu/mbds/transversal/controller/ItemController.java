package itu.mbds.transversal.controller;

import itu.mbds.transversal.entity.Item;
import itu.mbds.transversal.service.obejct.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping()
    public ResponseEntity<?> saveItem(@RequestBody Item item) {
        Item itemSaved = itemService.save(item);
        return ResponseEntity.ok(itemSaved);
    }

}
