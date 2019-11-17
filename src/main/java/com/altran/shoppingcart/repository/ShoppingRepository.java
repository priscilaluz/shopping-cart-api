package com.altran.shoppingcart.repository;

import com.altran.shoppingcart.document.Shopping;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author priscilaluz
 */
public interface ShoppingRepository extends MongoRepository<Shopping, String> {
}
