package menu;

import config.ListDbFactory;
import config.SingletonOfFactory;
import menu.operationList.ProductOperation;
import menu.operationList.SaleOperation;

import java.util.*;

public class Menu {

    public static void showMenu(){
        String[] productOperation = {"Add new product:",
                "Change products features:",
                "Delete a product:",
                "Show all the products:",
                "Show products due to category:",
                "Show products between prices:",
                "Search product due to name:"};

        String[] saleOperation = {"Add new sale:",
                "Return a product from sale:",
                "Delete a sale:",
                "Show all sales:",
                "Show sales between time:",
                "Show sales between price:",
                "Show sales in exact time(day):",
                "Show sale due to its id:"};

        Scanner scan = new Scanner(System.in);
            System.out.print("If you want to work with a list, enter " +
                    "'list' and if you want to work with a database, enter 'db': ");
             String storage = scan.next();
        SingletonOfFactory.getListDbFactory().setStorage(storage);
        while (true) {
            System.out.println("""
                    Menu
                     1. Operate over products:\s
                     2. Operate over sales:\s
                     3. Sign out from system:\s
                    """);
            System.out.print("Select the number of choice: ");
            int menu = scan.nextInt();
            if (menu == 1) {
                for (int i = 1; i <= productOperation.length; i++) {
                    System.out.print(i + "." + productOperation[i - 1] + "\n");
                }
                ProductOperation.operation();
            } else if (menu == 2) {
                for (int i = 1; i <= saleOperation.length; i++) {
                    System.out.print(i + "." + saleOperation[i - 1] + "\n");
                }
                SaleOperation.operation();
            } else
                return;
        }
        }
}
