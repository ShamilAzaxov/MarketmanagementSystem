package service.impl;

import entity.Product;
import entity.Sale;
import entity.SaleItem;
import lombok.Getter;
import operation.ProductOperation;
import service.inter.ProductInter;
import service.inter.SaleInter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class SaleImpl implements SaleInter {

private ProductInter marketable = ProductOperation.getProductInter();
   private final HashMap<Integer, Sale> sales = new HashMap<>();

    @Override
    public Sale addSale(Sale sale) {
        Product product;
        List<SaleItem> saleItems=sale.getSaleItems();
        Sale addSale = sales.put(sale.getId(), sale);
        for(int i = 0; i<saleItems.size();i++) {
             product =saleItems.get(i).getProduct();
              product.setCount(product.getCount() - sale.getSaleItems().get(i).getQuantity());
            System.out.print(i+1 + "." + product.getProductName() + "               quantity - " +
                    sale.getSaleItems().get(i).getQuantity() + "                 product price - " + product.getPrice() + "                 sum of price - " +
                    saleItems.get(i).getPrice() + "\n");
        }
        System.out.println("Sale Price - " + sale.getSalePrice() + "\n");
        return addSale;
    }

    @Override
    public boolean returnProductFromSale(int saleId, String barcode) {
        Product product = marketable.getProductDueToBarcode(barcode);
        boolean removed = false;
        SaleItem saleItem;
        for(int i = 0; i<sales.get(saleId).getSaleItems().size(); i++){
            saleItem = sales.get(saleId).getSaleItems().get(i);
          if(saleItem.getProduct().getProductCode().equals(product.getProductCode())){
              removed = sales.get(saleId).getSaleItems().remove(saleItem);
              product.setCount(product.getCount() +saleItem.getQuantity());
              break;
          }
          else if(i == sales.get(saleId).getSaleItems().size()-1)
              System.out.println("There is no such a product in list: ");
        }
        return removed;
    }

    @Override
    public Sale returnSale(Integer saleId) {
        Sale sale = sales.get(saleId);
        int size = sale.getSaleItems().size();
        for (int i = 0; i < size; i++){
            returnProductFromSale(saleId, sale.getSaleItems().get(0).getProduct().getProductCode());
        }
        sale = sales.remove(saleId);
        return sale;
    }

    @Override
    public List<Map.Entry> salesFromTimeToTime(LocalDateTime from, LocalDateTime to) {
        List<Map.Entry> salesBetweenTime = new ArrayList<>();
        sales.entrySet().stream().forEach((sale)->{
            if(sale.getValue().getSaleTime().isAfter(from) && sale.getValue().getSaleTime().isBefore(to)){
                salesBetweenTime.add(sale);
            }
        });
        return salesBetweenTime;
    }

    @Override
    public List<Map.Entry> salesInDate(LocalDate day) {
        List<Map.Entry> salesInDate = new ArrayList<>();
        sales.entrySet().stream().forEach((sale) ->{
            if(sale.getValue().getSaleTime().getYear() == day.getYear() &&
                sale.getValue().getSaleTime().getMonth().equals(day.getMonth()) &&
              sale.getValue().getSaleTime().getDayOfMonth() == day.getDayOfMonth()){
                salesInDate.add(sale);
            }
                });
        return salesInDate;
    }

    @Override
    public List<Sale> salesDueToPrice(BigDecimal from, BigDecimal to) {
        List<Sale> salesBetweenPrice = new ArrayList<>();
        sales.forEach((key, value) ->{
            if(value.getSalePrice().compareTo(from)>0 && value.getSalePrice().compareTo(to)<0){
                salesBetweenPrice.add(value);
            }
        });
        return salesBetweenPrice;
    }
    @Override
    public Map.Entry<Integer, Sale> saleDueToSaleNumber(Integer saleId) {
        Map.Entry<Integer, Sale> sale = null;
        for(Map.Entry<Integer, Sale> sale1 : sales.entrySet()){
            if (sale1.getValue().getId().equals(saleId)){
                sale = sale1;
            }
        }
        return sale;
    }
}