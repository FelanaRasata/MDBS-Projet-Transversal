package itu.mbds.transversal.controller;

import itu.mbds.transversal.entity.User;
import itu.mbds.transversal.service.user.UserService;
import itu.mbds.transversal.utils.dto.response.ResponseMessage;
import itu.mbds.transversal.utils.enumeration.Message;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    private String entity = "Utilisateur";

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> saveAdmin(@RequestBody User user) {

        try {

            User savedUser = userService.saveAdmin(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);

        } catch (DataIntegrityViolationException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage(Message.USERNAME_USED.toString()));

        }

    }

    @GetMapping("{id}")
    public ResponseEntity<?> getUser(@PathVariable int id) {

        User user = userService.findById(id);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage(Message.NOT_FOUND.byId(entity, id)));
        }

        return ResponseEntity.ok(user);

    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<User>> getUsers(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "") String keyword
    ) {

        Page<User> itemCategories = userService.findAll(page, keyword);
        return ResponseEntity.ok(itemCategories);

    }

    @PutMapping("{id}")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<?> updateUser(
            @PathVariable int id,
            @RequestBody User user
    ) {
        User updated = userService.update(id, user);

        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage(Message.NOT_FOUND.byId(entity, id)));
        }

        return ResponseEntity.ok(updated);
    }

    @PutMapping("enable/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> enableUser(
            @PathVariable int id
    ) {
        User updated = userService.enable(id);

        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage(Message.NOT_FOUND.byId(entity, id)));
        }

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable int id) {
        userService.delete(id);
    }


}
