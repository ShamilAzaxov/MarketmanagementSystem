package menu.operationList;

import config.ListDbFactory;
import config.SingletonOfFactory;
import entity.Category;
import entity.Product;
import lombok.Getter;
import service.impl.ProductImpl;
import service.inter.ProductInter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Scanner;

public class ProductOperation {

    private static final ProductInter productInter = SingletonOfFactory.getListDbFactory().productInter();

    public static void operation(){
        Category[] categories = Category.values();
        Scanner scan = new Scanner(System.in);
        int productId = 0;
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
                System.out.print("Enter product's quantity: ");
                Integer productCount = scan.nextInt();
                System.out.println("Enter product category by number showed below: ");
                for(int i = 0; i<categories.length;i++){
                    System.out.println(i+1 + "." +categories[i]);
                }
                int productCategory = scan.nextInt();
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
                Product product= null;
                if (productInter instanceof ProductImpl) {
                    if (productInter.getProducts().isEmpty())
                        productId = 1;
                    else
                        productId = productInter.getProducts().get(productInter.getProducts().size() - 1).getId() + 1;
                    product = new Product(productId, productName, productPrice, productCode, category, productCount);
                }
                else
                    product = new Product(productName, productPrice, productCode, category, productCount);

                productInter.addProduct(product);
                break;
            }
            case 2:{
                String[] productFields = {"Product name", "Product price", "Product code", "Product category", "Product quantity"};
                System.out.print("Enter product code you want to change a feature: ");
                Product product = new Product();
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
                        Arrays.stream(categories).map(category -> category.toString().toLowerCase()).forEach(System.out::println);
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
                break;
            }
            case 4:{
                productInter.getProducts().forEach(System.out::println);
                break;
            }
            case 5: {
                Arrays.stream(categories).forEach(System.out::println);
                System.out.print("Enter a category: ");
                String category = scan.nextLine();
                category = scan.nextLine().toUpperCase();
                Category category1 = Category.valueOf(category);
               productInter.getProducts().
                       stream().filter(product1 -> product1.getCategory().equals(category1)).toList().
                       forEach(System.out::println);
                break;
            }

            case 6 : {
                System.out.print("Enter first price: ");
                BigDecimal from = scan.nextBigDecimal();
                System.out.print("Enter second price: ");
                BigDecimal to = scan.nextBigDecimal();
                productInter.productsBetweenPrice(from, to).forEach(System.out::println);
                break;
            }
            case 7 : {
                System.out.print("Enter product's name you want to get: ");
                String name = scan.nextLine();
                name = scan.nextLine();
                System.out.println(productInter.getProductDueToName(name));
                break;
            }
        }
    }
}
