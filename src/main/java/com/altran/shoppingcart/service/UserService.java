package com.altran.shoppingcart.service;

import com.altran.shoppingcart.dto.UserDTO;
import java.util.List;

/**
 *
 * @author priscilaluz
 */
public interface UserService {

    UserDTO save(UserDTO user);

    UserDTO findById(String id);

    UserDTO findByNameAndEmail(String name, String email);

    List<UserDTO> findAll();

    void delete(String id);
}
