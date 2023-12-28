package service.implDB;

import config.ListDbFactory;
import config.MarketConfig;
import config.SingletonOfFactory;
import entity.Product;
import entity.Sale;
import entity.SaleItem;
import service.inter.ProductInter;
import service.inter.SaleInter;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class SaleDB implements SaleInter {

    private final ProductInter productInter = SingletonOfFactory.getListDbFactory().productInter();
    @Override
    public Sale addSale(Sale sale) {
        EntityManager manager = MarketConfig.manager();
        Product product;
        List<SaleItem> saleItems = sale.getSaleItems();
        BigDecimal salePrice = BigDecimal.valueOf(0);
        manager.getTransaction().begin();
        for (int i = 0; i < saleItems.size(); i++){
            salePrice = salePrice.add(saleItems.get(i).getPrice());
            manager.persist(saleItems.get(i));
            int productId = saleItems.get(i).getProduct().getId();
            product = manager.find(Product.class, productId);
            product.setCount(product.getCount() - saleItems.get(i).getQuantity());
            System.out.print(i + 1 + "." + product.getProductName() + "\tquantity - " +
                    sale.getSaleItems().get(i).getQuantity() + "\tproduct price - " +
                    product.getPrice() + "\tsum of price - " +
                    saleItems.get(i).getPrice() + "\n");
        }
        sale.setSalePrice(salePrice);
        manager.persist(sale);
        manager.getTransaction().commit();
        manager.close();
        System.out.println("Sale Price - " + sale.getSalePrice() + "\n");
        return sale;
    }

    @Override
    public boolean returnProductFromSale(int saleId, String barcode) {

        EntityManager manager = MarketConfig.manager();
        Product product = productInter.getProductDueToBarcode(barcode);
        SaleItem saleItem;
        Sale sale = manager.find(Sale.class, saleId);

        for (int i = 0; i < sale.getSaleItems().size(); i++) {
            saleItem = sale.getSaleItems().get(i);
            if (saleItem.getProduct().getProductCode().equals(barcode)) {
                manager.getTransaction().begin();
                sale.getSaleItems().remove(saleItem);
                manager.createQuery("delete from SaleItem where id = " + saleItem.getId()).executeUpdate();
                product.setCount(product.getCount() + saleItem.getQuantity());
                manager.merge(product);

                if (sale.getSaleItems().isEmpty())
                    manager.remove(sale);
                else
                    sale.setSalePrice(sale.getSalePrice().subtract(saleItem.getPrice()));

                manager.getTransaction().commit();
                manager.close();
                return true;
            } else if (i == sale.getSaleItems().size() - 1)
                System.out.println("There is no such a product in list: ");
        }
        return false;
    }

    @Override
    public Sale returnSale(Integer saleId) {
        EntityManager manager = MarketConfig.manager();
        Sale sale = manager.find(Sale.class, saleId);
        manager.getTransaction().begin();
        try {
            for (SaleItem saleItem : sale.getSaleItems()) {
                Product product = manager.find(Product.class, saleItem.getProduct().getId());
                product.setCount(product.getCount() + saleItem.getQuantity());
                manager.remove(saleItem);
            }
            manager.remove(sale);
        }catch (NullPointerException ex){
            System.out.println("There is no such an element that id = " + saleId);
        }
        manager.getTransaction().commit();
        manager.close();
        return sale;
    }

    @Override
    public List<Sale> salesFromTimeToTime(LocalDateTime from, LocalDateTime to) {
        return getSales().values().stream().filter(sale -> sale.getSaleTime().isAfter(from) && sale.getSaleTime().isBefore(to)).toList();
    }

    @Override
    public List<Sale> salesInDate(LocalDate day) {
        return getSales().values().stream().filter(sale -> sale.getSaleTime().getDayOfYear() == day.getDayOfYear()).toList();
    }

    @Override
    public List<Sale> salesDueToPrice(BigDecimal from, BigDecimal to) {
        return getSales().values().stream().filter(sale -> sale.getSalePrice().compareTo(from)>0
                && sale.getSalePrice().compareTo(to)<0).toList();
    }

    @Override
    public Sale saleDueToSaleId(Integer saleId) {
        return getSales().get(saleId);
    }

    @Override
    public HashMap<Integer, Sale> getSales() {
        EntityManager manager1 = MarketConfig.manager();
        manager1.getTransaction().begin();
        HashMap<Integer, Sale> sales = new HashMap<>();
        List<Sale> saleList = manager1.createQuery("select s from Sale s").getResultList();
        saleList.forEach(sale -> sales.put(sale.getId(), sale));
        manager1.getTransaction().commit();
        manager1.close();
        return sales;
    }
}