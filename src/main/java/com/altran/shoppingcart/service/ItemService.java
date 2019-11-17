package com.altran.shoppingcart.service;

import com.altran.shoppingcart.dto.ItemDTO;
import java.util.List;

/**
 *
 * @author priscilaluz
 */
public interface ItemService {

    ItemDTO save(ItemDTO itemDTO);

    ItemDTO findById(String id);

    List<ItemDTO> findAll();

    void delete(String id);
}
