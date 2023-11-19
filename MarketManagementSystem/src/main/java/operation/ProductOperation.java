package operation;

import entity.Category;
import entity.Product;
import service.impl.ProductImpl;
import service.inter.ProductInter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductOperation {

    private static final ProductInter productInter = new ProductImpl();

    public static ProductInter getProductInter(){
        return productInter;
    }

        static void operation(){
        Category[] categories = Category.values();
        Scanner scan = new Scanner(System.in);
        Integer productId = 0;
        System.out.print("\nSelect your choice: ");
        int menu = scan.nextInt();
        switch (menu){
            case 1: {
                System.out.print("Enter product's name: ");
                String productName = scan.nextLine();
                productName = scan.nextLine();
                System.out.print("Enter product's price: ");
                BigDecimal productPrice = scan.nextBigDecimal();
                System.out.print("Enter product barcode: ");
                String productCode = scan.next();
                System.out.println("Enter product category by number showed below: ");
                for(int i = 0; i<categories.length;i++){
                    System.out.println(i+1 + "." +categories[i]);
                }
                Integer productCategory = scan.nextInt();
                Category category = switch (productCategory) {
                    case 1 -> Category.FASHION;
                    case 2 -> Category.HOME;
                    case 3 -> Category.SPORT;
                    case 4 -> Category.HEALTH;
                    case 5 -> Category.MEDICAL;
                    case 6 -> Category.CHILDREN;
                    case 7 -> Category.PET;
                    default -> null;
                };
                System.out.print("Enter product's quantity: ");
                Integer productCount = scan.nextInt();
                if(productInter.getProducts().size() == 0)
                    productId = 1;
                else
                    productId = productInter.getProducts().get(productInter.getProducts().size()-1).getId()+1;
                Product product = new Product(productId, productName, productPrice, productCode, category, productCount);
                productInter.addProduct(product);
                Menu.showMenu();
                break;
            }
            case 2:{
                String[] productFields = {"Product name", "Product price", "Product code", "Product category", "Product quantity"};
                System.out.print("Enter product code you want to change a feature: ");
                Product product = new Product(null, null, null, null, null, null);
                String productCode =  scan.nextLine();
                productCode = scan.nextLine();
                System.out.println("Product's features: ");
                for (int i = 1; i<=productFields.length; i++) {
                    System.out.print(i +"." + productFields[i-1] + "\n");
                }
                System.out.print("Enter the number of the feature you want to change: ");
                int feature = scan.nextInt();
                switch (feature){
                    case 1: {
                        System.out.print("Enter product name: ");
                        String name = scan.nextLine();
                        name = scan.nextLine();
                        product.setProductName(name);
                        productInter.changeProductFeature(productCode, product);
                        break;
                    }
                    case 2: {
                        System.out.print("Enter product price: ");
                        BigDecimal price = scan.nextBigDecimal();
                        product.setPrice(price);
                        productInter.changeProductFeature(productCode, product);
                        break;
                    }
                    case 3: {
                        System.out.print("Enter product code: ");
                        String code = scan.next();
                        product.setProductCode(code);
                        productInter.changeProductFeature(productCode, product);
                        break;
                    }
                    case 4:{
                        System.out.println("Categories: ");
                        for(Category category : categories){
                            System.out.println(category.toString().toLowerCase());
                        }
                        System.out.print("Enter product category : ");
                        String category = scan.nextLine();
                        category = scan.nextLine().toUpperCase();
                        product.setCategory(Category.valueOf(category));
                        productInter.changeProductFeature(productCode, product);
                        break;
                    }
                    case 5: {
                        System.out.print("Enter product's quantity: ");
                        int count = scan.nextInt();
                        product.setCount(count);
                        productInter.changeProductFeature(productCode, product);
                        break;
                    }
                }
                Menu.showMenu();
                break;
            }
            case 3: {
                System.out.print("Enter product code you want to remove from list: ");
                String productCode = scan.next();
                int productsLength = productInter.getProducts().size();
                String deletedProduct = productInter.deleteProduct(productCode);
                if (productInter.getProducts().size() == productsLength)
                    System.err.println(deletedProduct);
                else
                    System.out.println(deletedProduct);
                Menu.showMenu();
                break;
            }
            case 4:{
                productInter.getProducts().stream().forEach(System.out::println);
               Menu. showMenu();
                break;
            }
            case 5: {
                List<Product> products = new ArrayList<>();
                for (Category category : categories){
                    System.out.println(category);
                }
                System.out.print("Enter a category: ");
                String category = scan.nextLine();
                category = scan.nextLine().toUpperCase();
                Category category1 = Category.valueOf(category);
                for (Product product : productInter.getProducts()){
                    if(product.getCategory().equals(category1))
                        products.add(product);
                }
                System.out.println(products);
                Menu.showMenu();
                break;
            }

            case 6 : {
                System.out.print("Enter first price: ");
                BigDecimal from = scan.nextBigDecimal();
                System.out.print("Enter second price: ");
                BigDecimal to = scan.nextBigDecimal();
                productInter.productsBetweenPrice(from, to).stream().forEach(System.out::println);
                Menu.showMenu();
                break;
            }
            case 7 : {
                System.out.print("Enter product's name you want to get: ");
                String name = scan.nextLine();
                name = scan.nextLine();
                System.out.println(productInter.getProductDueToName(name));
                Menu.showMenu();
                break;
            }
        }
    }
}
