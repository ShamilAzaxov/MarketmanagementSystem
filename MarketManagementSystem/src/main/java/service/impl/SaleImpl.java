package service.impl;

import entity.Product;
import entity.Sale;
import service.inter.ProductInter;
import service.inter.SaleInter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SaleImpl implements SaleInter {

ProductInter marketable = new ProductImpl();
   private final List<Sale> sales = new ArrayList<>();

    @Override
    public boolean addSale(Sale sale) {
        return sales.add(sale);
    }

    @Override
    public Product returnProductFromSale(String productName) {

        return marketable.getProductDueToName(productName);
    }

    @Override
    public Sale returnSale(long saleNumber) {
        for(Sale sale : sales)
            if(sale.getSaleNumber() == saleNumber)
                return sale;
        return null;
    }

    @Override
    public List<Sale> salesFromTimeToTime(LocalDateTime from, LocalDateTime to) {
        List<Sale> salesBetweenTime = new ArrayList<>();
        for(Sale sale : sales)
            if (sale.getSaleTime().compareTo(from) > 0 && sale.getSaleTime().compareTo(to)<0)
                salesBetweenTime.add(sale);
        return salesBetweenTime;
    }

    @Override
    public List<Sale> salesInDate(LocalDate day) {
        List<Sale> salesInDay = new ArrayList<>();
        for (Sale sale : sales){
            if(sale.getSaleTime().getDayOfYear() == day.getDayOfYear())
                salesInDay.add(sale);
        }
        return salesInDay;
    }

    @Override
    public List<Sale> salesDueToPrice(long from, long to) {
        List<Sale> salesDueToPrice = new ArrayList<>();
        for (Sale sale : sales){
            if(sale.getSalePrice() > from && sale.getSalePrice() <to){
                salesDueToPrice.add(sale);
            }
        }
        return salesDueToPrice;
    }

    @Override
    public Sale saleDueToSaleNumber(long saleNumber) {
        for (Sale sale : sales) {
            if(sale.getSaleNumber() == saleNumber){
                return sale;
            }
        }
        return null;
    }
}
