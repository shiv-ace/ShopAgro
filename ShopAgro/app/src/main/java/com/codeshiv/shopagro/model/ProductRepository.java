package com.codeshiv.shopagro.model;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

public class ProductRepository {

    private List<Product> productList;

    @Inject
    public ProductRepository() {

    }

    public List<Product> getProductsList() {
        productList = new ArrayList<>(10);

        for (int i = 0; i < 10; i++) {

            final Product product = new Product();
            product.setInfo("Agro");
            product.setPrice(new Random().nextInt(10000));
            product.setProductName("Apple " + new Random().nextInt(100));
            product.setEstimatedDate(new Date());
            product.setLikes(new Random().nextInt(10));
            product.setImageUrl("https://i0.wp.com/loseitblog.com/wp-content/uploads/2014/09/istock_000014459318_double.jpg?ssl=1");

            productList.add(product);
        }
        return productList;
    }

    public void updateProductLikes(Product updatedProduct, boolean isLiked) {
        for (Product product : productList) {
            if (product.getProductName().equalsIgnoreCase(updatedProduct.getProductName())) {
                if (isLiked) {
                    product.setLikes(updatedProduct.getLikes() + 1);
                } else {
                    product.setLikes(updatedProduct.getLikes() - 1);
                }
                break;
            }
        }
    }

    @Nullable
    public Product getUpdatedProduct(Product staleProduct) {
        Product product = null;
        for (Product productInList : productList) {
            if (productInList.getProductName().equalsIgnoreCase(staleProduct.getProductName())) {
                product = productInList;
                break;
            }
        }
        return product;
    }
}
