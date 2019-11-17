package com.altran.shoppingcart.controller;

import com.altran.shoppingcart.dto.ItemDTO;
import com.altran.shoppingcart.service.ItemService;
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
 * Item Controller
 *
 * @author priscilaluz
 */
@RestController
@RequestMapping("/api/item")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ItemController {

    @Autowired
    private ItemService service;

    @PostMapping()
    public ResponseEntity<ItemDTO> save(@RequestBody ItemDTO item) {
        return ResponseEntity.ok(service.save(item));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<ItemDTO>> findAll() {
        List<ItemDTO> result = service.findAll();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<ItemDTO> findById(@PathVariable("id") String id) {
        ItemDTO result = service.findById(id);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
