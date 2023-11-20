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
import java.util.*;

@Getter
public class SaleImpl implements SaleInter {

    private ProductInter marketable = ProductOperation.getProductInter();
    private final HashMap<Integer, Sale> sales = new HashMap<>();

    @Override
    public Sale addSale(Sale sale) {
        Product product;
        List<SaleItem> saleItems = sale.getSaleItems();
        Sale addSale = sales.put(sale.getId(), sale);
        for (int i = 0; i < saleItems.size(); i++) {
            product = saleItems.get(i).getProduct();
            product.setCount(product.getCount() - sale.getSaleItems().get(i).getQuantity());
            System.out.print(i + 1 + "." + product.getProductName() + "               quantity - " +
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
        for (int i = 0; i < sales.get(saleId).getSaleItems().size(); i++) {
            saleItem = sales.get(saleId).getSaleItems().get(i);
            if (saleItem.getProduct().getProductCode().equals(product.getProductCode())) {
                removed = sales.get(saleId).getSaleItems().remove(saleItem);
                product.setCount(product.getCount() + saleItem.getQuantity());
                break;
            } else if (i == sales.get(saleId).getSaleItems().size() - 1)
                System.out.println("There is no such a product in list: ");
        }
        return removed;
    }

    @Override
    public Sale returnSale(Integer saleId) {
        Sale sale = sales.get(saleId);
        int size = sale.getSaleItems().size();
        for (int i = 0; i < size; i++) {
            returnProductFromSale(saleId, sale.getSaleItems().get(0).getProduct().getProductCode());
        }
        sale = sales.remove(saleId);
        return sale;
    }

    @Override
    public List<Sale> salesFromTimeToTime(LocalDateTime from, LocalDateTime to) {
        return sales.values().stream().filter(sale -> sale.getSaleTime().isAfter(from) && sale.getSaleTime().isBefore(to)).toList();
    }

    @Override
    public List<Sale> salesInDate(LocalDate day) {
        return sales.values().stream().filter(sale -> sale.getSaleTime().toLocalDate().isEqual(day)).toList();
    }

    @Override
    public List<Sale> salesDueToPrice(BigDecimal from, BigDecimal to) {
        return sales.values().stream().filter(sale -> sale.getSalePrice().
                compareTo(from)>0 && sale.getSalePrice().compareTo(to)<0).toList();
    }

    @Override
    public Sale saleDueToSaleNumber(Integer saleId) {
             return sales.values().stream().filter(sale1 -> sale1.getId().equals(saleId)).findFirst().
                    orElseThrow(() -> new NoSuchElementException("There is no such element"));
    }
}
