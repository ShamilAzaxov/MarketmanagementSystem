package entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sale")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @OneToMany(mappedBy = "sale",fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    List<SaleItem> saleItems;
    @Column(name = "sale_price")
    BigDecimal salePrice;
    @Column(name = "sale_time")
    LocalDateTime saleTime;
}