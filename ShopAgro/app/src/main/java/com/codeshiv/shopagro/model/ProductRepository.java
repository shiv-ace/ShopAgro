package com.codeshiv.shopagro.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

public class ProductRepository {

    @Inject
    public ProductRepository() {

    }

    public List<Product> getProductsList() {
        final List<Product> productList = new ArrayList<>(10);

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

}
