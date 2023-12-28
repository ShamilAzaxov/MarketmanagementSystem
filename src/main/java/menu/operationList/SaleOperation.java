package menu.operationList;

import config.ListDbFactory;
import config.SingletonOfFactory;
import entity.Product;
import entity.Sale;
import entity.SaleItem;
import lombok.Getter;
import service.impl.SaleImpl;
import service.inter.ProductInter;
import service.inter.SaleInter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class SaleOperation{

    private static final SaleInter saleInter = SingletonOfFactory.getListDbFactory().saleInter();
    private static final ProductInter productInter = SingletonOfFactory.getListDbFactory().productInter();

    public static void operation(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Select your choice: ");
        int menu = scan.nextInt();
        switch (menu){
            case 1 : {
                Product product;
                List<SaleItem> saleItems = new ArrayList<>();
                Sale sale = new Sale();
                int saleItmeId = 1;
                while (true) {
                    System.out.print("Enter product's barcode you have bought: ");
                    String barcode = scan.next();
                    System.out.print("Enter amount how many you have bought:  ");
                    int amount = scan.nextInt();
                    product = productInter.getProductDueToBarcode(barcode);

                    SaleItem saleItem = new SaleItem();
                    saleItem.setProduct(product);
                    saleItem.setQuantity(amount);
                    if (saleInter instanceof SaleImpl) {
                        saleItem.setId(saleItmeId);
                        saleItmeId++;
                    }
                    else
                        saleItem.setSale(sale);

                    saleItems.add(saleItem);
                    System.out.print("If there are products that have not read barcode yet enter 1, else 0; ");
                    int stopOr = scan.nextInt();
                    if (stopOr == 0)
                        break;
                    while (stopOr != 1 & stopOr != 0) {
                        System.out.print("You entered wrong number please enter 1 or 0: ");
                        stopOr = scan.nextInt();
                    }
                }
                if (saleInter instanceof SaleImpl) {
                    int saleId;
                    if (saleInter.getSales().isEmpty())
                        saleId = 1;
                    else
                        saleId = saleInter.getSales().get(saleInter.getSales().size()).getId() + 1;
                    sale.setId(saleId);
                }
                LocalDateTime now = LocalDateTime.now();
                sale.setSaleItems(saleItems);
                sale.setSaleTime(now.minusNanos(now.getNano()));
                saleInter.addSale(sale);
                break;
            }
            case 2 : {
                System.out.print("Enter sale id: ");
                int saleId = scan.nextInt();
                System.out.print("Enter product's barcode you want to return back: ");
                String barcode = scan.next();
                saleInter.returnProductFromSale(saleId, barcode);
                break;
            }
            case 3 : {
                System.out.println("Sales in your database are below");
                saleInter.getSales().values().forEach(System.out::println);
                System.out.print("Enter sale id you want to return back: ");
                int saleId = scan.nextInt();
                saleInter.returnSale(saleId);
                break;
            }
            case 4 : {
                saleInter.getSales().values().forEach(System.out::println);
                break;
            }
            case 5 : {
                System.out.print("Enter first date: (Sample yyyy-MM-dd): ");
                String beginDate = scan.next();
                System.out.print("Enter first time: (Sample hh:mm:ss): ");
                String beginTime = scan.next();
                System.out.print("Enter second date: (Sample yyyy-MM-dd): ");
                String endDate = scan.next();
                System.out.print("Enter second time: (Sample hh:mm:ss): ");
                String endTime = scan.next();

                LocalDateTime from = LocalDateTime.parse(beginDate.concat("T").concat(beginTime));
                LocalDateTime to = LocalDateTime.parse(endDate.concat("T").concat(endTime));
                saleInter.salesFromTimeToTime(from, to).forEach(System.out::println);
                break;
            }
            case 6 : {
                System.out.print("Enter first price: ");
                BigDecimal from = scan.nextBigDecimal();
                System.out.print("Enter second price: ");
                BigDecimal to = scan.nextBigDecimal();
                saleInter.salesDueToPrice(from, to).forEach(System.out::println);
                break;
            }
            case 7 : {
                System.out.print("Enter the day of month you want to get sales: ");
                int day = scan.nextInt();
                LocalDate today = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), day);
                saleInter.salesInDate(today).forEach(System.out::println);
                break;
            }
            case 8 : {
                System.out.print("Enter sale id: ");
                int saleId = scan.nextInt();
                System.out.println(saleInter.saleDueToSaleId(saleId));
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + menu);
        }
    }
}
