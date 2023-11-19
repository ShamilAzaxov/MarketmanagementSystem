package service.inter;

import entity.Category;
import entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductInter {

    boolean addProduct(Product product);

    Product changeProductFeature(String productCode, Product product);

     Product getProductDueToBarcode(String productCode);

    String deleteProduct(String productCode);

    List<Product> productDueToCategory(Category category);

    List<Product> productsBetweenPrice(BigDecimal from, BigDecimal to);

    Product getProductDueToName(String productName);

    List<Product> getProducts();

}
