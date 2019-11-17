package com.altran.shoppingcart.controller;

import com.altran.shoppingcart.dto.UserDTO;
import com.altran.shoppingcart.service.UserService;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;

    @MockBean
    private MongoTemplate mongoTemplate;

    @Test
    public void whenCallPathSaveReturnObjectWithId() throws Exception {
        UserDTO user = new UserDTO("2", "name2", "name2@email.com", null);

        when(service.save(any())).thenReturn(user);

        mvc.perform(post("/api/user")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void whenCallPathFindAllUsersReturnAll() throws Exception {
        List<UserDTO> users = Arrays.asList(
                new UserDTO("1", "name1", "name1@email.com", null),
                new UserDTO("2", "name2", "name2@email.com", null));

        when(service.findAll()).thenReturn(users);

        mvc.perform(get("/api/user/findAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void whenCallPathFindByIdReturnObjectId() throws Exception {
        UserDTO user = new UserDTO("2", "name2", "name2@email.com", null);

        when(service.findById(any())).thenReturn(user);

        mvc.perform(get("/api/user/findById/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void whenCallPathFindByFilterReturnObjectId() throws Exception {
        UserDTO user = new UserDTO("2", "name2", "name2@email.com", null);

        when(service.findByNameAndEmail(any(), any())).thenReturn(user);

        mvc.perform(get("/api/user/filter/name/email")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }
    
    @Test
    public void whenCallPathDeleteIdReturnSuccess() throws Exception {
        UserDTO user = new UserDTO("2", "name2", "name2@email.com", null);

       doNothing().when(service).delete(any());

        mvc.perform(delete("/api/user/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
