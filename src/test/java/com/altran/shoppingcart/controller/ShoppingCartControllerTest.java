package com.altran.shoppingcart.controller;

import com.altran.shoppingcart.dto.ShoppingCartDTO;
import com.altran.shoppingcart.service.ShoppingCartService;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ShoppingCartController.class)
public class ShoppingCartControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ShoppingCartService service;

    @MockBean
    private MongoTemplate mongoTemplate;

    @Test
    public void whenCallPathSaveReturnOk() throws Exception {
        doNothing().when(service).save(any(), any());
        mvc.perform(post("/api/shoppingCart")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenCallPathCloseShoppingReturnObject() throws Exception {
        ShoppingCartDTO shoppingCart = new ShoppingCartDTO("1", BigDecimal.ONE, new ArrayList<>());

        when(service.closeShoppingCart(any())).thenReturn(shoppingCart);

        mvc.perform(put("/api/shoppingCart/closeShoppingCart")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.total").exists());
    }

    @Test
    public void whenCallPathDeleteShoppingReturnOk() throws Exception {
        doNothing().when(service).save(any(), any());
        mvc.perform(delete("/api/shoppingCart/deleleShopping/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
