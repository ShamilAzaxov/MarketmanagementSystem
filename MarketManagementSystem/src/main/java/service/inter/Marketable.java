package service.inter;

import entity.Category;
import entity.Product;
import entity.Sale;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface Marketable {

    public boolean addSale(Sale sale);

    public Product returnProductFromSale(String productName);

    public Sale returnSale(long saleNumber);

    public List<Sale> salesFromTimeToTime(LocalDateTime from, LocalDateTime to);

    public List<Sale> salesInDate(LocalDate day);

    public List<Sale> salesDueToPrice(long from, long to);

    public Sale saleDueToSaleNumber(long saleNumber);

    public boolean addProduct(Product product);

    public Product changeProductFeature(String productCode, Product product);

    public List<Product> productDueToCategory(Category category);

    public List<Product> productsBetweenPrice(long from, long to);

    public Product getProductDueToName(String productName);

}
