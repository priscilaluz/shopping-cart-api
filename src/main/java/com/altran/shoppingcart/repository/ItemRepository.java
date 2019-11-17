package com.altran.shoppingcart.repository;

import com.altran.shoppingcart.document.Item;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author priscilaluz
 */
public interface ItemRepository extends MongoRepository<Item, String> {

    Optional<Item> findByName(String name);

}
