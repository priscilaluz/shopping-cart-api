package com.altran.shoppingcart.controller;

import com.altran.shoppingcart.dto.ShoppingCartDTO;
import com.altran.shoppingcart.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@RequestMapping("/api/shoppingCart")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService service;

    @PostMapping()
    public ResponseEntity<String> save(@RequestBody ShoppingCartDTO shoppingsDto) {
        service.save(shoppingsDto.getId(), shoppingsDto.getShoppings());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/closeShoppingCart")
    public ResponseEntity<ShoppingCartDTO> closeShoppingCart(@RequestBody ShoppingCartDTO shoppingsDto) {
        return ResponseEntity.ok().body(service.closeShoppingCart(shoppingsDto.getId()));
    }
    
    @DeleteMapping("/deleleShopping/{id}")
    public ResponseEntity<ShoppingCartDTO> deleleShopping(@PathVariable("id") String idShopping) {
        service.deleteShopping(idShopping);
        return ResponseEntity.ok().build();
    }

}
