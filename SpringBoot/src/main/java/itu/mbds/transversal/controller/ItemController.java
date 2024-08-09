package itu.mbds.transversal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import itu.mbds.transversal.entity.Item;
import itu.mbds.transversal.entity.ItemCategory;
import itu.mbds.transversal.security.CustomUserDetails;
import itu.mbds.transversal.service.item.FileService;
import itu.mbds.transversal.service.item.ItemService;
import itu.mbds.transversal.utils.dto.request.ChangeOwnerDTO;
import itu.mbds.transversal.utils.dto.response.ResponseMessage;
import itu.mbds.transversal.utils.enumeration.EntityValue;
import itu.mbds.transversal.utils.enumeration.Message;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/item")
public class ItemController {

    private final ItemService itemService;


    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> saveItem(@RequestParam(name = "item") String itemJson, @RequestParam(name = "file") MultipartFile file) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        Item item = mapper.readValue(itemJson, Item.class);
        CustomUserDetails userDetails = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        long userId = userDetails.getId();

        try {

            Item saved = itemService.save(item, userId, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(e.getMessage()));
        }

    }

    @GetMapping("{id}")
    public ResponseEntity<?> getItem(@PathVariable long id) {

        Item item = itemService.findById(id);

        if (item == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage(Message.NOT_FOUND.byId(EntityValue.ITEM, id)));
        }

        return ResponseEntity.ok(item);

    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<Item>> getItems(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "") String keyword
    ) {

        Page<Item> items = itemService.findAll(page, keyword);
        return ResponseEntity.ok(items);

    }

    @GetMapping("/mine")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Page<Item>> getMyItems(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "") String keyword
    ) {

        CustomUserDetails userDetails = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        long userId = userDetails.getId();

        Page<Item> items = itemService.findByUser(page, userId, keyword);
        return ResponseEntity.ok(items);

    }


    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> updateItem(
            @PathVariable long id,
            @RequestParam(name = "item") String itemJson,
            @RequestParam(required = false, name = "file") MultipartFile file
    ) {

        try {

            ObjectMapper mapper = new ObjectMapper();

            Item item = mapper.readValue(itemJson, Item.class);

            if (item.getUser() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseMessage(Message.NOT_FOUND.byEntity(EntityValue.USER)));
            }

            Item saved = itemService.update(id, item, file);

            if (saved == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseMessage(Message.NOT_FOUND.byId(EntityValue.ITEM, id)));
            }

            return ResponseEntity.status(HttpStatus.OK).body(saved);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(e.getMessage()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @PutMapping("enable")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> enableItems(@RequestBody List<Item> items) {

        try {

            List<Item> itemList = itemService.enable(items);
            return ResponseEntity.status(HttpStatus.OK).body(itemList);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(e.getMessage()));
        }

    }

    @PutMapping("change-owner")
    public ResponseEntity<?> changeOwner(@RequestBody ChangeOwnerDTO changeOwnerDTO) {

        try {

            itemService.changeOwner(changeOwnerDTO.getNewOwner(), changeOwnerDTO.getItems());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (IllegalArgumentException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage(e.getMessage()));

        }

    }


    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable long id) {

        itemService.delete(id);

    }


}
