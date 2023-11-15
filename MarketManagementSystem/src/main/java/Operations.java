import entity.Category;
import entity.Product;
import service.impl.ProductImpl;
import service.impl.SaleImpl;
import service.inter.ProductInter;
import service.inter.SaleInter;

import java.math.BigDecimal;
import java.util.Scanner;

public class Operations {
    static final ProductInter productInter = new ProductImpl();
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
                productOperation();
                break;
            }
            case 2: {
                for(int i= 1; i<=saleOperation.length; i++){
                    System.out.print(i + "." + saleOperation[i-1] + "\n");
                }
                break;
            }
            case 3: break;
        }
    }

    private static void productOperation(){
        Category[] categories = Category.values();
        Scanner scan = new Scanner(System.in);
        System.out.print("\nSelect your choice: ");
        int menu = scan.nextInt();
        switch (menu){
            case 1: {
                System.out.print("Enter product's id: ");
                Integer productId = scan.nextInt();
                scan.nextLine();
                System.out.print("Enter product's name: ");
                String productName = scan.nextLine();
                System.out.print("Enter product's price: ");
                BigDecimal productPrice = scan.nextBigDecimal();
                System.out.print("Enter product code: ");
                String productCode = scan.next();
                System.out.println("Enter product category by number showed below: ");
                for(int i = 0; i<categories.length;i++){
                    System.out.println(i+1 + "." +categories[i]);
                }
                Integer productCategory = scan.nextInt();
                Category category = null;
                switch (productCategory){
                    case 1: category = Category.FASHION;
                    case 2: category = Category.HOME;
                    case 3: category = Category.SPORT;
                    case 4: category = Category.HEALTH;
                    case 5: category = Category.MEDICAL;
                    case 6: category = Category.CHILDREN;
                    case 7: category = Category.PET;
                }
                System.out.print("Enter product's quantity: ");
                Integer productCount = scan.nextInt();
                Product product = new Product(productId, productName, productPrice, productCode, category, productCount);
                productInter.addProduct(product);

            }
            case 2:{
                System.out.print("Enter product's name you want to change feature: ");
                String name = scan.nextLine();
                System.out.println(productInter.getProductDueToName(name));
                System.out.print("Which feature do you want to change? : ");
//                productInter.changeProductFeature();
            }

        }

    }
}
