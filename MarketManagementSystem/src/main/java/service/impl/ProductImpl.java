package service.impl;

import entity.Category;
import entity.Product;
import entity.Sale;
import service.inter.ProductInter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductImpl implements ProductInter {

    private final List<Product> products = new ArrayList<>();

    @Override
    public boolean addProduct(Product product) {
        return products.add(product);
    }

    @Override
    public Product changeProductFeature(String productCode,Product product) {
        for (Product theProduct : products){
            if(theProduct.getProductCode().equals(productCode)){
                products.remove(theProduct);
                theProduct = product;
                addProduct(theProduct);
                return theProduct;
            }
        }
        return null;
    }

    @Override
    public List<Product> productDueToCategory(Category category) {
        List<Product> sameCategoryProducts = new ArrayList<>();
        for(Product product : products){
            if(product.getCategory().equals(category)){
                sameCategoryProducts.add(product);
            }
        }
        return sameCategoryProducts;
    }

    @Override
    public List<Product> productsBetweenPrice(long from, long to) {
        List<Product> products1 = new ArrayList<>();
        for (Product product : products) {
            if(product.getPrice() > from && product.getPrice() < to){
                products1.add(product);
            }
        }
        return products1;
    }

    @Override
    public Product getProductDueToName(String productName) {
        Product product = null;
        for (Product product1 : products){
            if(product1.getProductName().equals(productName)){
                product = product1;
            }
        }
        return product;
    }
}
