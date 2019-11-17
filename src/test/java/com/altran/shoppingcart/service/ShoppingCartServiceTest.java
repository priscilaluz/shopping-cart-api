package com.altran.shoppingcart.service;

import com.altran.shoppingcart.ShoppingCartApiApplication;
import com.altran.shoppingcart.document.Item;
import com.altran.shoppingcart.document.Shopping;
import com.altran.shoppingcart.document.ShoppingCart;
import com.altran.shoppingcart.document.User;
import com.altran.shoppingcart.error.BusinessException;
import com.altran.shoppingcart.dto.ShoppingCartDTO;
import com.altran.shoppingcart.dto.ShoppingDTO;
import com.altran.shoppingcart.repository.ItemRepository;
import com.altran.shoppingcart.repository.ShoppingCartRepository;
import com.altran.shoppingcart.repository.ShoppingRepository;
import com.altran.shoppingcart.repository.UserRepository;
import com.altran.shoppingcart.util.Constant;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ShoppingCartApiApplication.class)
@AutoConfigureMockMvc
public class ShoppingCartServiceTest {

    @Autowired
    private ShoppingCartService service;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    
    @Autowired
    private ShoppingRepository shoppingRepository;
    
    @Autowired
    private ItemRepository itemRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    private Item i1, i2, i3, i4, i5;

    @Before
    public void init() {
        //Item
        itemRepository.deleteAll();
        i1 = itemRepository.save(new Item("1", "item1", new BigDecimal("5.3")));
        i2 = itemRepository.save(new Item("2", "item2", new BigDecimal("2.6")));
        i3 = itemRepository.save(new Item("3", "item3", new BigDecimal("7.07")));
        i4 = itemRepository.save(new Item("4", "item4", new BigDecimal("11.4")));
        i5 = itemRepository.save(new Item("5", "item5", new BigDecimal("1.15")));
        
        //Shopping
        shoppingRepository.deleteAll();
        Shopping s1 = shoppingRepository.save(new Shopping("1", 1, i1));
        Shopping s2 = shoppingRepository.save(new Shopping("2", 2, i1));
        Shopping s3 = shoppingRepository.save(new Shopping("3", 2, i2));
        Shopping s4 = shoppingRepository.save(new Shopping("4", 5, i3));
        Shopping s5 = shoppingRepository.save(new Shopping("5", 7, i4));
        Shopping s6 = shoppingRepository.save(new Shopping("6", 4, i5));
        
        //Shopping Cart
        shoppingCartRepository.deleteAll();
        ShoppingCart sc1 = shoppingCartRepository.save(new ShoppingCart("1", BigDecimal.ZERO, Arrays.asList(s3, s4, s1, s5, s6)));
        ShoppingCart sc2 = shoppingCartRepository.save(new ShoppingCart("2", BigDecimal.ZERO,Arrays.asList(s2)));
        ShoppingCart sc3 = shoppingCartRepository.save(new ShoppingCart("3", BigDecimal.ZERO, new ArrayList<>()));
        
        //User
        userRepository.deleteAll();
        userRepository.save(new User("1", "joao", "joao@email.com", sc1));
        userRepository.save(new User("2", "maria", "maria@email.com", sc2));
        userRepository.save(new User("3", "lucia", "lucia@email.com", sc3));
    }
    
    @Test
    public void whenSaveListOfNewShoppings() {
        //Before Test
        Optional<ShoppingCart> scBefore = shoppingCartRepository.findById("3");
        assertTrue(scBefore.isPresent());
        assertEquals(0, scBefore.get().getShoppings().size());
        
        List<ShoppingDTO> shoppingsDto = new ArrayList<>();
        shoppingsDto.add(modelMapper.map(new Shopping(null, 1, i1), ShoppingDTO.class));
        shoppingsDto.add(modelMapper.map(new Shopping(null, 3, i2), ShoppingDTO.class));
        service.save("3", shoppingsDto);
        
        //After Test
        Optional<ShoppingCart> scAfter = shoppingCartRepository.findById("3");
        assertTrue(scAfter.isPresent());
        assertEquals(2, scAfter.get().getShoppings().size());
    }
    
    @Test
    public void whenAddAmountInItemShoppings() {
        //Before Test
        Optional<ShoppingCart> scBefore = shoppingCartRepository.findById("2");
        assertTrue(scBefore.isPresent());
        assertEquals(1, scBefore.get().getShoppings().size());
        
        List<ShoppingDTO> shoppingsDto = new ArrayList<>();
        shoppingsDto.add(modelMapper.map(new Shopping(null, 4, i1), ShoppingDTO.class));
        service.save("2", shoppingsDto);
        
        //After Test
        Optional<ShoppingCart> scAfter = shoppingCartRepository.findById("2");
        assertTrue(scAfter.isPresent());
        assertEquals(1, scAfter.get().getShoppings().size());
        assertEquals("2", scAfter.get().getShoppings().get(0).getId());
        assertEquals(Integer.valueOf(6), scAfter.get().getShoppings().get(0).getAmount());
        assertEquals("1", scAfter.get().getShoppings().get(0).getItem().getId());
    }
    
    @Test
    public void whenCloseShoppingWithInvalidId() {
        ShoppingCartDTO s = service.closeShoppingCart("10");
        assertNull(s);
    }
    
    @Test
    public void whenCloseShoppingWithoutByAnything() {
        ShoppingCartDTO s = service.closeShoppingCart("3");
        assertNotNull(s);
        assertEquals(BigDecimal.ZERO, s.getTotal());
    }
    
    @Test
    public void whenCloseShoppingWithListOfItem() {
        ShoppingCartDTO s = service.closeShoppingCart("1");
        assertNotNull(s);
        assertEquals(new BigDecimal("130.25"), s.getTotal());
    }
    
    @Test
    public void whenDeleteByInvalidIdReturnErro() {
        try {
            service.deleteShopping("12");
            fail();
        } catch (BusinessException e) {
            assertEquals(Constant.SHOPPING_NOT_FOUND, e.getMessage());
        }
    }

    @Test
    public void whenDeleteByValidIdReturnSuccess() {
        //Before Test
        assertEquals(6, shoppingRepository.count());
        
        service.deleteShopping("2");
        
        //After Test
        assertEquals(5, shoppingRepository.count());
        assertTrue(!shoppingRepository.findById("2").isPresent());
    }
}
