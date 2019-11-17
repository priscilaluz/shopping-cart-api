package com.altran.shoppingcart.document;

import java.io.Serializable;
import java.math.BigDecimal;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
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
@Document(collection = "Item")
public class Item implements Serializable {

    private static final long serialVersionUID = -4856258677561078044L;

    @Id
    private String id;
    private String name;
    private BigDecimal value;

}
