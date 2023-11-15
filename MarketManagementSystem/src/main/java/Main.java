import entity.Category;
import entity.Product;
import entity.Sale;
import entity.SaleItem;
import service.impl.SaleImpl;
import service.inter.ProductInter;
import service.inter.SaleInter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SaleImpl saleInter = new SaleImpl();
        ProductInter product = Operations.productInter;
        SaleInter sale = new SaleImpl();
        Product p = new Product(1,"Book", new BigDecimal("4.5"), "12h", Category.CHILDREN, 30);
        Product p1 = new Product(2,"Clothes", new BigDecimal("24.59"), "11h", Category.FASHION, 25);
        Product p2 = new Product(3,"Food", new BigDecimal("5"), "13h", Category.HOME, 40);
        Product p3 = new Product(4,"Sport", new BigDecimal("140.9"), "10h", Category.HOME, 15);
        Product p4 = new Product(5,"Book", new BigDecimal("4.5"), "12h", Category.CHILDREN, 50);
        product.addProduct(p);
        product.addProduct(p1);
        product.addProduct(p2);
        product.addProduct(p3);
        product.addProduct(p4);
        System.out.println(product.getProducts() + "\n");

//
//
        List<SaleItem> saleItems1 = new ArrayList<>();
        saleItems1.add(new SaleItem(1, p, 3));
        saleItems1.add(new SaleItem(2, p1, 5));
        saleItems1.add(new SaleItem(3, p3, 1));
        saleItems1.add(new SaleItem(4, p2, 2));
//
//
        List<SaleItem> saleItems2 = new ArrayList<>();
        saleItems2.add(new SaleItem(1, p1, 5));
        saleItems2.add(new SaleItem(2, p2, 10));
        saleItems2.add(new SaleItem(3, p3, 9));

//
        Sale s1 = new Sale(1, saleItems1, BigDecimal.valueOf(60), LocalDateTime.now());
        Sale s2 = new Sale(2, saleItems2, BigDecimal.valueOf(100), LocalDateTime.now());
//

        saleInter.addSale(s1);
        saleInter.addSale(s2);
        System.out.println(saleInter.saleDueToSaleNumber(2));

        Operations.showMenu();
        System.out.println(product.getProducts());
    }
}
