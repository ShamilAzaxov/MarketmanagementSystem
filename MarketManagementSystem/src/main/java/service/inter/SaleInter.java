package service.inter;

import entity.Product;
import entity.Sale;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SaleInter {

    public boolean addSale(Sale sale);

    public Product returnProductFromSale(String productName);

    public Sale returnSale(long saleNumber);

    public List<Sale> salesFromTimeToTime(LocalDateTime from, LocalDateTime to);

    public List<Sale> salesInDate(LocalDate day);

    public List<Sale> salesDueToPrice(long from, long to);

    public Sale saleDueToSaleNumber(long saleNumber);

}
