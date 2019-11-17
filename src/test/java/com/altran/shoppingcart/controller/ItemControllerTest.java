package com.altran.shoppingcart.controller;

import com.altran.shoppingcart.dto.ItemDTO;
import com.altran.shoppingcart.service.ItemService;
import java.math.BigDecimal;
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
@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ItemService service;

    @MockBean
    private MongoTemplate mongoTemplate;

    @Test
    public void whenCallPathSaveReturnObjectWithId() throws Exception {
        ItemDTO item = new ItemDTO("2", "name2", BigDecimal.valueOf(5L));

        when(service.save(any())).thenReturn(item);

        mvc.perform(post("/api/item")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void whenCallPathFindAllItemsReturnAll() throws Exception {
        List<ItemDTO> items = Arrays.asList(
                new ItemDTO("1", "name1", BigDecimal.ONE),
                new ItemDTO("2", "name2", BigDecimal.valueOf(5L)));

        when(service.findAll()).thenReturn(items);

        mvc.perform(get("/api/item/findAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void whenCallPathFindByIdReturnObjectId() throws Exception {
        ItemDTO item = new ItemDTO("2", "name2", BigDecimal.valueOf(5L));

        when(service.findById(any())).thenReturn(item);

        mvc.perform(get("/api/item/findById/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }
    
    @Test
    public void whenCallPathDeleteIdReturnSuccess() throws Exception {
        ItemDTO item = new ItemDTO("2", "name2", BigDecimal.valueOf(5L));

       doNothing().when(service).delete(any());

        mvc.perform(delete("/api/item/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
