package com.altran.shoppingcart.service;

import com.altran.shoppingcart.ShoppingCartApiApplication;
import com.altran.shoppingcart.document.User;
import com.altran.shoppingcart.dto.UserDTO;
import com.altran.shoppingcart.error.BusinessException;
import com.altran.shoppingcart.repository.UserRepository;
import com.altran.shoppingcart.util.Constant;
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
public class UserServiceTest {

    @Autowired
    private UserService service;

    @Autowired
    private UserRepository repository;

    @Before
    public void init() {
        repository.deleteAll();

        repository.insert( new User(null, "joao", "joao@email.com", null));
        repository.save(new User("2", "maria", "maria@email.com", null));
    }

    @Test
    public void whenSaveWithoutNameReturnErro() {
        try {
            service.save(new UserDTO(null, null, "email@email.com", null));
            fail();
        } catch (BusinessException e) {
            assertEquals(Constant.USER_NAME_REQUIRED, e.getMessage());
        }
    }
    
    @Test
    public void whenSaveWithoutEmailReturnErro() {
        try {
            service.save(new UserDTO(null, "Name", null, null));
            fail();
        } catch (BusinessException e) {
            assertEquals(Constant.USER_EMAIL_REQUIRED, e.getMessage());
        }
    }
    
    @Test
    public void whenSaveWithAlredySaveEmailReturnErro() {
        try {
            service.save(new UserDTO(null, "Name", "maria@email.com", null));
            fail();
        } catch (BusinessException e) {
            assertEquals(Constant.USER_ALREADY_EXISTS, e.getMessage());
        }
    }
    
    @Test
    public void whenSaveObjectValidReturnObjectWithId() {
        //Before Test
        assertEquals(2L, repository.count());
        
        UserDTO user = service.save(new UserDTO(null, "Name", "nome@email.com", null));
        
        //After Test
        assertEquals(3L, repository.count());
        assertTrue(repository.findById(user.getId()).isPresent());
    }

    @Test
    public void whehFindByInvalidIdReturnErro() {
        try {
            service.findById("1");
            fail();
        } catch (BusinessException e) {
            assertEquals(Constant.USER_NOT_FOUND, e.getMessage());
        }
    }
    
    @Test
    public void whenFindByValidIdReturnObject() {
        UserDTO user = service.findById("2");
        assertNotNull(user);
        assertEquals("2", user.getId());
        assertEquals("maria", user.getName());
        assertEquals("maria@email.com", user.getEmail());
    }
    
    @Test
    public void whehFindByWithouNameRetornErro() {
        try {
            service.findByNameAndEmail(null, "email@emai.com");
            fail();
        } catch (BusinessException e) {
            assertEquals(Constant.USER_NAME_REQUIRED, e.getMessage());
        }
    }
    
    @Test
    public void whehFindByWithouEmailRetornErro() {
        try {
            service.findByNameAndEmail("nome", null);
            fail();
        } catch (BusinessException e) {
            assertEquals(Constant.USER_EMAIL_REQUIRED, e.getMessage());
        }
    }
    
    @Test
    public void whehFindByNameEmailWithInvalidResultRetornErro() {
        try {
            service.findByNameAndEmail("nome", "nome@email.com");
            fail();
        } catch (BusinessException e) {
            assertEquals(Constant.USER_NOT_FOUND, e.getMessage());
        }
    }
    
    @Test
    public void whehFindByNameEmailValidResultRetornObject() {
        UserDTO user = service.findByNameAndEmail("maria", "maria@email.com");
        assertNotNull(user);
        assertEquals("2", user.getId());
    }
    
    @Test
    public void whenFingAllReturnAllUsers() {
        List<UserDTO> users = service.findAll();
        assertEquals(2, users.size());
    }

    @Test
    public void whenDeleteByInvalidIdReturnErro() {
        try {
            service.delete("12");
            fail();
        } catch (BusinessException e) {
            assertEquals(Constant.USER_NOT_FOUND, e.getMessage());
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
