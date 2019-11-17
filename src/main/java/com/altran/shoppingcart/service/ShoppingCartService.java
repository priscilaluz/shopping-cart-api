package com.altran.shoppingcart.service;

import com.altran.shoppingcart.dto.ShoppingCartDTO;
import com.altran.shoppingcart.dto.ShoppingDTO;
import java.util.List;

/**
 *
 * @author priscilaluz
 */
public interface ShoppingCartService {

    void save(String idShoppingCart, List<ShoppingDTO> shoppingsDto);

    ShoppingCartDTO closeShoppingCart(String id);
    
    void deleteShopping(String id);
}
