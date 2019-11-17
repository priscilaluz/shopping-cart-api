package com.altran.shoppingcart.repository;

import com.altran.shoppingcart.document.ShoppingCart;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author priscilaluz
 */
public interface ShoppingCartRepository extends MongoRepository<ShoppingCart, String> {
    Optional<ShoppingCart> findByIdAndShoppingsItemId(String id, String idItem);
}
