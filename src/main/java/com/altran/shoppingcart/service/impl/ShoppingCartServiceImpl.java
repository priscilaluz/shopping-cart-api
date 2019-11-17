package com.altran.shoppingcart.service.impl;

import com.altran.shoppingcart.document.Item;
import com.altran.shoppingcart.document.Shopping;
import com.altran.shoppingcart.document.ShoppingCart;
import com.altran.shoppingcart.dto.ShoppingCartDTO;
import com.altran.shoppingcart.dto.ShoppingDTO;
import com.altran.shoppingcart.error.BusinessException;
import com.altran.shoppingcart.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.altran.shoppingcart.repository.ShoppingCartRepository;
import com.altran.shoppingcart.repository.ShoppingRepository;
import com.altran.shoppingcart.util.Constant;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;

/**
 *
 * @author priscilaluz
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    
    @Autowired
    private ShoppingRepository shoppingRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void save(String idShoppingCart, List<ShoppingDTO> shoppingsDto) {
        List<Shopping> shoppings = new ArrayList<>();
        shoppingsDto.stream().forEach(shoppingDto -> {
            shoppings.add(modelMapper.map(shoppingDto, Shopping.class));
        });
        Optional<ShoppingCart> result = shoppingCartRepository.findById(idShoppingCart);
        if (result.isPresent()) {
            ShoppingCart shoppingCart = result.get();

            for (Shopping shopping : shoppings) {
                shoppingCart = createShopping(shoppingCart, shopping.getItem(), shopping.getAmount());
            }
            shoppingCartRepository.save(shoppingCart);
        }
    }

    private ShoppingCart createShopping(ShoppingCart shoppingCart, Item item, Integer amount) {
        Shopping shopping = shoppingCart.getShoppings().stream().filter(s -> (s.getItem().getId().equals(item.getId()))).findAny().orElse(null);
        if (shopping != null) {
            shoppingCart.getShoppings().remove(shopping);
            shopping.setAmount(shopping.getAmount() + amount);
        } else {
            shopping = new Shopping(null, amount, item);
        }
        shopping = shoppingRepository.save(shopping);
        shoppingCart.getShoppings().add(shopping);
        return shoppingCart;
    }

    @Override
    public ShoppingCartDTO closeShoppingCart(String id) {
        Optional<ShoppingCart> result = shoppingCartRepository.findById(id);
        if (result.isPresent()) {
            BigDecimal total = BigDecimal.ZERO;
            ShoppingCart shoppingCart = result.get();
            for (Shopping shopping : shoppingCart.getShoppings()) {
                total = total.add(shopping.getItem().getValue().multiply(new BigDecimal(shopping.getAmount())));
            }
            shoppingCart.setTotal(total);
            shoppingCart.setShoppings(shoppingCart.getShoppings().stream()
		.sorted((s1, s2) -> s1.getItem().getName().compareTo(s2.getItem().getName()))
		.collect(Collectors.toList()));
            shoppingCart = shoppingCartRepository.save(shoppingCart);
            return modelMapper.map(shoppingCart, ShoppingCartDTO.class);
        }
        return null;
    }

    @Override
    public void deleteShopping(String id) {
        Optional<Shopping> result = shoppingRepository.findById(id);
        if (result.isPresent()) {
            shoppingRepository.deleteById(id);
        } else {
            throw new BusinessException(Constant.SHOPPING_NOT_FOUND);
        }
    }
}
