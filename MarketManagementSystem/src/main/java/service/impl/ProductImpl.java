package service.impl;

import entity.Category;
import entity.Product;
import entity.Sale;
import lombok.Data;
import lombok.Getter;
import service.inter.ProductInter;

import javax.swing.plaf.TableHeaderUI;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Getter
public class ProductImpl implements ProductInter {

    private final List<Product> products = new ArrayList<>();

    @Override
    public boolean addProduct(Product product) {
        if(products.size() == 0) {
            products.add(product);
            return true;
        }
        else {
            for(Product theProduct : products) {
                if (product.getProductCode().equals(theProduct.getProductCode())) {
                    theProduct.setCount(theProduct.getCount() + product.getCount());
                    return true;
                }
                else if (!product.getProductCode().equals(theProduct.getProductCode()) &&
                                products.get(products.size()-1).equals(theProduct)) {
                    products.add(product);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Product changeProductFeature(String productCode,Product product) {
       Product theProduct = getProductDueToCode(productCode);
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
       return theProduct;
    }

    private Product getProductDueToCode(String productCode){
        Product theProduct = null;
        for (Product product : products){
            if(product.getProductCode().equals(productCode)) {
                theProduct = product;
                break;
            }
        }
        return theProduct;
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
    public List<Product> productsBetweenPrice(BigDecimal from, BigDecimal to) {
        List<Product> theProducts = new ArrayList<>();
        for(Product product : products){
            if(product.getPrice().compareTo(from) > 0 && product.getPrice().compareTo(to) <0)
                theProducts.add(product);
        }
        return theProducts;
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
