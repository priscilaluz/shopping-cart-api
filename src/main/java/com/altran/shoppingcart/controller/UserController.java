package com.altran.shoppingcart.controller;

import com.altran.shoppingcart.dto.UserDTO;
import com.altran.shoppingcart.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User Controller
 *
 * @author priscilaluz
 */
@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping()
    public ResponseEntity<UserDTO> save(@RequestBody UserDTO user) {
        return ResponseEntity.ok(service.save(user));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> result = service.findAll();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") String id) {
        UserDTO result = service.findById(id);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/filter/{name}/{email}")
    public ResponseEntity<UserDTO> findByNameAndEmail(@PathVariable("name") String name, @PathVariable("email") String email) {
        UserDTO result = service.findByNameAndEmail(name, email);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
