package com.altran.shoppingcart.document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Document(collection = "ShoppingCart")
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = -4856258677561078044L;

    @Id
    private String id;
    private BigDecimal total;
    @DBRef
    private List<Shopping> shoppings;
}
