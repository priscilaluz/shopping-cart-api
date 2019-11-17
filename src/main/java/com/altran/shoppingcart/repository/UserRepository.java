package com.altran.shoppingcart.repository;

import com.altran.shoppingcart.document.User;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author priscilaluz
 */
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByNameAndEmail(String name, String email);
    Optional<User> findByEmail(String email);
}
