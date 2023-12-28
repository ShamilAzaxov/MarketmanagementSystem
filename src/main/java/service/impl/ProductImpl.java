package service.impl;

import entity.Category;
import entity.Product;
import service.inter.ProductInter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


public class ProductImpl implements ProductInter {

    private final List<Product> products = new ArrayList<>();

    @Override
    public boolean addProduct(Product product) {
        if(products.isEmpty()) {
            products.add(product);
            return true;
        }
        else {
            for(Product theProduct : products) {
                if (product.getProductCode().equals(theProduct.getProductCode())) {
                    if (!theProduct.getProductName().equals(product.getProductName())) {
                        System.err.println("\nBarcodes are same but names are not: Please correct it.\n");
                        break;
                    }
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
       Product theProduct = getProductDueToBarcode(productCode);
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

    @Override
    public Product getProductDueToBarcode(String productCode){
        return products.stream().filter(product -> product.getProductCode().
                equals(productCode)).findFirst().orElseThrow(() -> new NoSuchElementException("There is no such element: "));
    }

    @Override
    public String deleteProduct(String productCode) {
        Product product = getProductDueToBarcode(productCode);
        if(products.remove(product))
            return product.toString();
        return "\nOccurred a problem.\n";
    }

    @Override
    public List<Product> productDueToCategory(Category category) {
        return products.stream().filter(product -> product.getCategory().equals(category)).toList();
    }

    @Override
    public List<Product> productsBetweenPrice(BigDecimal from, BigDecimal to) {
        return products.stream().filter(product -> product.getPrice().compareTo(from)>0 &&
                product.getPrice().compareTo(to)<0).toList();
    }

    @Override
    public Product getProductDueToName(String productName) {
        Product theProduct = null;
        try {
            theProduct = products.stream().filter(product -> product.getProductName().
                    equals(productName)).findFirst().get();
        } catch (NoSuchElementException ex) {
            System.out.print("The " + ex.getMessage().substring(3) + " is ");
        }
            return theProduct;
    }

    @Override
    public List<Product> getProducts() {
        return products;
    }
}
