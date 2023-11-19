package operation;

import service.impl.SaleImpl;
import service.inter.SaleInter;

import java.util.*;

public class Menu {
    static final SaleInter saleInter = new SaleImpl();
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
        System.out.println("Menu\n 1. Operate over products: \n 2. Operate over sales: \n 3. Sign out from system: \n");
        System.out.print("Select the number of choice: ");
        int menu = scan.nextInt();
        switch (menu) {
            case 1: {
                for (int i = 1; i<=productOperation.length; i++){
                System.out.print(i + "." + productOperation[i-1] + "\n");
            }
                ProductOperation.operation();
                break;
            }
            case 2: {
                for(int i= 1; i<=saleOperation.length; i++){
                    System.out.print(i + "." + saleOperation[i-1] + "\n");
                }
                SaleOperation.operation();
                break;
            }
            case 3: break;
        }
    }
}
