package service.inter;

import entity.Sale;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public interface SaleInter {

     Sale addSale(Sale sale);

     boolean returnProductFromSale(int saleId, String barcode);

     Sale returnSale(Integer saleId);

     List<Sale> salesFromTimeToTime(LocalDateTime from, LocalDateTime to);

     List<Sale> salesInDate(LocalDate day);

     List<Sale> salesDueToPrice(BigDecimal from, BigDecimal to);

     Sale saleDueToSaleId(Integer saleId);

    HashMap<Integer, Sale> getSales();


}
