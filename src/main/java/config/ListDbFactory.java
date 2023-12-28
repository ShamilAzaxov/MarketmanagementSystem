package config;

import lombok.Getter;
import lombok.Setter;
import service.impl.ProductImpl;
import service.impl.SaleImpl;
import service.implDB.ProductDB;
import service.implDB.SaleDB;
import service.inter.ProductInter;
import service.inter.SaleInter;

@Setter
public class ListDbFactory {

    private String storage = "";

    public ProductInter productInter(){
        if (storage.equalsIgnoreCase("list"))
            return new ProductImpl();
        else if (storage.equalsIgnoreCase("db"))
            return new ProductDB();
        throw new NullPointerException("ProductInter came null !!!!!!!!!!");
    }

    public SaleInter saleInter(){
        if (storage.equalsIgnoreCase("list"))
            return new SaleImpl();
        else if (storage.equalsIgnoreCase("db"))
            return new SaleDB();
        throw new NullPointerException("SaleInter came null!!!!!!!!");
    }
}