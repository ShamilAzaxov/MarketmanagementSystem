import config.ListDbFactory;
import config.MarketConfig;
import entity.Product;
import entity.SaleItem;
import menu.Menu;
import service.implDB.ProductDB;
import service.implDB.SaleDB;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
       Menu.showMenu();
//        MarketConfig.manager();
    }
}