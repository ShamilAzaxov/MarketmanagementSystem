package entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaleItem {
    Integer id;
    String product;
    Integer quantity;
    BigDecimal price;

    public SaleItem(Integer id, Product product, Integer quantity) {
        this.id = id;
        this.product = product.getProductName();
        this.quantity = quantity;
        this.price = product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}