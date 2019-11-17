package com.altran.shoppingcart.dto;

import java.math.BigDecimal;
import java.util.List;
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
public class ShoppingCartDTO {

    private String id;
    private BigDecimal total;
    private List<ShoppingDTO> shoppings;

}
