package operation;

import entity.Product;
import entity.Sale;
import entity.SaleItem;
import service.impl.SaleImpl;
import service.inter.ProductInter;
import service.inter.SaleInter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class SaleOperation {

    private static final SaleInter saleInter = new SaleImpl();
    private static ProductInter productInter = ProductOperation.getProductInter();

    public static SaleInter getSaleInter(){
        return saleInter;
    }

    public static void operation(){
        SaleItem saleItem;
        Product product;
        List<SaleItem> saleItems = new ArrayList<>();
        Sale sale;
        BigDecimal salePrice = BigDecimal.valueOf(0);
        Scanner scan = new Scanner(System.in);
        System.out.print("Select your choice: ");
        int menu = scan.nextInt();
        switch (menu){
            case 1 : {
                int done = 1;
                int id = 1;
                while (done == 1) {
                    System.out.print("Enter product's barcode you have bought: ");
                    String barcode = scan.next();
                    System.out.print("Enter amount how many you have bought:  ");
                    int amount = scan.nextInt();
                    product = productInter.getProductDueToBarcode(barcode);
                    saleItem = new SaleItem(id, product, amount);
                    id++;
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
                int saleId;
                if(saleInter.getSales().size() == 0)
                    saleId = 1;
                else
                    saleId = saleInter.getSales().get(saleInter.getSales().size()).getId()+1;
                for(SaleItem saleItem1 : saleItems){
                    salePrice = salePrice.add(saleItem1.getPrice());
                }
                LocalDateTime now = LocalDateTime.now();
                sale = new Sale(saleId, saleItems, salePrice, now.minusNanos(now.getNano()));
                saleInter.addSale(sale);
                Menu.showMenu();
                break;
            }
            case 2 : {
                System.out.print("Enter sale id: ");
                int saleId = scan.nextInt();
                System.out.print("Enter product's barcode you want to return back: ");
                String barcode = scan.next();

                SaleItem returnedSaleItem = saleInter.getSales().get(saleId).getSaleItems().stream().
                        filter(saleItem1 -> saleItem1.getProduct().getProductCode().equals(barcode)).findFirst().get();
                saleInter.returnProductFromSale(saleId, barcode);
                saleInter.getSales().get(saleId).setSalePrice(saleInter.getSales().
                        get(saleId).getSalePrice().subtract(returnedSaleItem.getPrice()));

                boolean isPresent = saleInter.getSales().values().stream().anyMatch(sale1 -> sale1.getSaleItems().isEmpty());
                Sale onlySale;
                if(isPresent){
                    onlySale = saleInter.getSales().values().stream().filter(sale1 -> sale1.getSaleItems().isEmpty()).findFirst().get();
                    saleInter.returnSale(onlySale.getId());
                }

                Menu.showMenu();
                break;
            }
            case 3 : {
                System.out.print("Enter sale id you want to return back: ");
                int saleId = scan.nextInt();
                saleInter.returnSale(saleId);
                Menu.showMenu();
                break;
            }
            case 4 : {
                saleInter.getSales().values().stream().forEach(System.out::println);
                Menu.showMenu();
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
                saleInter.salesFromTimeToTime(from, to).stream().forEach(System.out::println);
                Menu.showMenu();
                break;
            }
            case 6 : {
                System.out.print("Enter first price: ");
                BigDecimal from = scan.nextBigDecimal();
                System.out.print("Enter second price: ");
                BigDecimal to = scan.nextBigDecimal();
                saleInter.salesDueToPrice(from, to).stream().forEach(System.out::println);
                Menu.showMenu();
                break;
            }
            case 7 : {
                System.out.print("Enter the day of month you want to get sales: ");
                int day = scan.nextInt();
                LocalDate today = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), day);
                saleInter.salesInDate(today).stream().forEach(System.out :: println);
                Menu.showMenu();
                break;
            }
            case 8 : {
                System.out.print("Enter sale id: ");
                int saleId = scan.nextInt();
                System.out.println(saleInter.saleDueToSaleNumber(saleId));
                Menu.showMenu();
                break;
            }
        }
    }
}
