package entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "sale_item")
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.DETACH)
    Product product;
    Integer quantity;
    BigDecimal price;
    @JoinColumn(name = "sale_id", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.DETACH)
    Sale sale;

    public SaleItem(Integer id, Product product, Integer quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.price = product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        this.price = this.product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SaleItem{" +
                "id=" + id +
                ", product=" + product.getProductName() +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}