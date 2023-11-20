package service.inter;

import entity.Product;
import entity.Sale;

import javax.swing.text.html.parser.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SaleInter {

     Sale addSale(Sale sale);

     boolean returnProductFromSale(int saleId, String barcode);

     Sale returnSale(Integer saleNumber);

     List<Sale> salesFromTimeToTime(LocalDateTime from, LocalDateTime to);

     List<Sale> salesInDate(LocalDate day);

     List<Sale> salesDueToPrice(BigDecimal from, BigDecimal to);

     Sale saleDueToSaleNumber(Integer saleId);

    HashMap<Integer, Sale> getSales();


}
