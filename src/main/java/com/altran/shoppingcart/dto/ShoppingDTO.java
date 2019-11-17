package com.altran.shoppingcart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ShoppingDTO {

    private String id;
    private Integer amount;
    private ItemDTO item;

}
