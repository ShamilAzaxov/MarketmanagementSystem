package service.inter;

import entity.Category;
import entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductInter {

    public boolean addProduct(Product product);

    public Product changeProductFeature(String productCode, Product product);

    public List<Product> productDueToCategory(Category category);

    public List<Product> productsBetweenPrice(BigDecimal from, BigDecimal to);

    public Product getProductDueToName(String productName);

    public List<Product> getProducts();

}
