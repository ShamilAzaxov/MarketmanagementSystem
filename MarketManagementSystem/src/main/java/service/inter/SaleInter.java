package service.inter;

import entity.Product;
import entity.Sale;

import javax.swing.text.html.parser.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface SaleInter {

    public Sale addSale(Sale sale);

    public boolean returnProductFromSale(int saleId, String productName);

    public Sale returnSale(Integer saleNumber);

    public List<Map.Entry> salesFromTimeToTime(LocalDateTime from, LocalDateTime to);

    public List<Map.Entry> salesInDate(LocalDate day);

    public List<Sale> salesDueToPrice(BigDecimal from, BigDecimal to);

    public Map.Entry<Integer, Sale> saleDueToSaleNumber(Integer saleId);

}
