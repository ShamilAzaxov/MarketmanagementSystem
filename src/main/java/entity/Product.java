package entity;

import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    Integer id;
    @Column(name = "product_name")
    @NotNull
    String productName;
    @NotNull
    BigDecimal price;
    @Column(name = "barcode", unique = true)
    @NotNull
    String productCode;
    @Enumerated(EnumType.STRING)
    @NotNull
    Category category;
    @NotNull
    Integer count;
    @OneToMany(mappedBy = "product", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @ToString.Exclude
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    List<SaleItem> saleItems;

    public Product(String productName, BigDecimal price, String productCode, Category category, Integer count) {
        this.productName = productName;
        this.price = price;
        this.productCode = productCode;
        this.category = category;
        this.count = count;
    }
}