package com.altran.shoppingcart.service;

import com.altran.shoppingcart.ShoppingCartApiApplication;
import com.altran.shoppingcart.document.Item;
import com.altran.shoppingcart.dto.ItemDTO;
import com.altran.shoppingcart.error.BusinessException;
import com.altran.shoppingcart.repository.ItemRepository;
import com.altran.shoppingcart.util.Constant;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ShoppingCartApiApplication.class)
@AutoConfigureMockMvc
public class ItemServiceTest {

    @Autowired
    private ItemService service;

    @Autowired
    private ItemRepository repository;

    @Before
    public void init() {
        repository.deleteAll();

        repository.insert(new Item(null, "coffee", new BigDecimal("5.6")));
        repository.save(new Item("2", "bread", new BigDecimal("2.6")));
    }
    
    @Test
    public void whenSaveWithoutNameReturnErro() {
        try {
            service.save(new ItemDTO(null, null, BigDecimal.ONE));
            fail();
        } catch (BusinessException e) {
            assertEquals(Constant.ITEM_NAME_REQUIRED, e.getMessage());
        }
    }
    
    @Test
    public void whenSaveWithoutValueReturnErro() {
        try {
            service.save(new ItemDTO(null, "coffee", BigDecimal.ONE));
            fail();
        } catch (BusinessException e) {
            assertEquals(Constant.ITEM_ALREADY_EXISTS, e.getMessage());
        }
    }
    
    @Test
    public void whenSaveWithoutIdAndWithNameAlredySaveReturnErro() {
        try {
            service.save(new ItemDTO(null, "Name", null));
            fail();
        } catch (BusinessException e) {
            assertEquals(Constant.ITEM_VALUE_REQUIRED, e.getMessage());
        }
    }
    
    @Test
    public void whenSaveWithValueEqualZeroReturnErro() {
        try {
            service.save(new ItemDTO(null, "Name", BigDecimal.ZERO));
            fail();
        } catch (BusinessException e) {
            assertEquals(Constant.ITEM_VALUE_CANNOT_BE_LESS_EQUAL_ZERO, e.getMessage());
        }
    }
    
    @Test
    public void whenSaveObjectValidReturnObjectWithId() {
        //Before Test
        assertEquals(2L, repository.count());
        
        ItemDTO item = service.save(new ItemDTO(null, "Name", BigDecimal.ONE));
        
        //After Test
        assertEquals(3L, repository.count());
        assertTrue(repository.findById(item.getId()).isPresent());
    }

    @Test
    public void whehFindByInvalidIdReturnErro() {
        try {
            service.findById("1");
            fail();
        } catch (BusinessException e) {
            assertEquals(Constant.ITEM_NOT_FOUND, e.getMessage());
        }
    }
    
    @Test
    public void whenFindByValidIdReturnObject() {
        ItemDTO item = service.findById("2");
        assertNotNull(item);
        assertEquals("2", item.getId());
        assertEquals("bread", item.getName());
        assertEquals(new BigDecimal("2.6"), item.getValue());
    }
    
    @Test
    public void whenFingAllReturnAllItems() {
        List<ItemDTO> items = service.findAll();
        assertEquals(2, items.size());
    }

    @Test
    public void whenDeleteByInvalidIdReturnErro() {
        try {
            service.delete("12");
            fail();
        } catch (BusinessException e) {
            assertEquals(Constant.ITEM_NOT_FOUND, e.getMessage());
        }
    }

    @Test
    public void whenDeleteByValidIdReturnSuccess() {
        //Before Test
        assertEquals(2, repository.count());
        
        service.delete("2");
        
        //After Test
        assertEquals(1, repository.count());
        assertTrue(!repository.findById("2").isPresent());
    }
}
