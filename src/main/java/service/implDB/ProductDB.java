package service.implDB;

import config.MarketConfig;
import entity.Category;
import entity.Product;
import service.inter.ProductInter;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

public class ProductDB implements ProductInter {

    @Override
    public boolean addProduct(Product product) {
        EntityManager manager = MarketConfig.manager();
        List<Product> products = manager.createQuery("select p from Product  p").getResultList();
        manager.getTransaction().begin();
        if (products.isEmpty()) {
            manager.persist(product);
            manager.getTransaction().commit();
            manager.close();
            return true;
        }
        else{
            for (Product theProduct : products){
                if (product.getProductCode().equals(theProduct.getProductCode())){
                    if (!theProduct.getProductName().equals(product.getProductName())) {
                        System.err.println("\nBarcodes are same but names are not: Please correct it.\n");
                        break;
                    }
                    theProduct.setCount(theProduct.getCount() + product.getCount());
                    manager.merge(theProduct);
                    manager.getTransaction().commit();
                    manager.close();
                    return true;
                }
                else if (theProduct.equals(products.get(products.size()-1))){
                    manager.persist(product);
                    manager.getTransaction().commit();
                    manager.close();
                    return true;
                }
            }
        }
        manager.close();
        return false;
    }

    @Override
    public Product changeProductFeature(String productCode, Product product) {
        EntityManager manager = MarketConfig.manager();
        int productId = getProductDueToBarcode(productCode).getId();
        manager.getTransaction().begin();
        Product theProduct = manager.find(Product.class, productId);
        if(product.getProductName() != null)
            theProduct.setProductName(product.getProductName());
        if(product.getCount() != null)
            theProduct.setCount(product.getCount());
        if(product.getProductCode() != null)
            theProduct.setProductCode(product.getProductCode());
        if(product.getPrice() != null)
            theProduct.setPrice(product.getPrice());
        if(product.getCategory() != null)
            theProduct.setCategory(product.getCategory());
        manager.getTransaction().commit();
        manager.close();
        return theProduct;
    }

    @Override
    public Product getProductDueToBarcode(String productCode) {
        return getProducts().stream().filter(product -> product.getProductCode().
                equals(productCode)).findFirst().
                orElseThrow(() -> new NoSuchElementException("There is no such element: "));
    }

    @Override
    public String  deleteProduct(String productCode) {
        EntityManager manager = MarketConfig.manager();
        Product product = (Product) manager.createQuery("select p from Product p where p.productCode = :p").
                setParameter("p", productCode).getSingleResult();
        manager.getTransaction().begin();
        manager.createQuery("update SaleItem s set s.product = null where s.product = :p").
                setParameter("p", product).executeUpdate();
        manager.remove(product);
        manager.getTransaction().commit();
        manager.close();
        return product.toString();
    }

    @Override
    public List<Product> productDueToCategory(Category category) {
        return getProducts().stream().filter(product -> product.getCategory().equals(category)).toList();
    }

    @Override
    public List<Product> productsBetweenPrice(BigDecimal from, BigDecimal to) {
        return getProducts().stream().filter(product -> product.getPrice().compareTo(from)>0 &&
                product.getPrice().compareTo(to)<0).toList();
    }

    @Override
    public Product getProductDueToName(String productName) {
        Product theProduct = null;
        try {
            theProduct = getProducts().stream().filter(product -> product.getProductName().
                    equals(productName)).findFirst().get();
        } catch (NoSuchElementException ex) {
            System.out.print("The " + ex.getMessage().substring(3) + " is ");
        }
        return theProduct;
    }

    @Override
    public List<Product> getProducts() {
        EntityManager manager = MarketConfig.manager();
        manager.getTransaction().begin();
        List<Product> products = manager.createQuery("select p from  Product p").getResultList();
        manager.getTransaction().commit();
        manager.close();
        return products;
    }
}
