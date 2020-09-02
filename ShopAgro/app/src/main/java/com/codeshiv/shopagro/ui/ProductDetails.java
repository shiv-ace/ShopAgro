package com.codeshiv.shopagro.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.codeshiv.shopagro.R;
import com.codeshiv.shopagro.model.Product;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProductDetails extends AppCompatActivity {

    @BindView(R.id.product_name)
    TextView productName;
    @BindView(R.id.product_info)
    TextView productInfo;
    @BindView(R.id.product_price)
    TextView productPrice;
    @BindView(R.id.product_estimated_date)
    TextView productDate;
    @BindView(R.id.product_likes)
    TextView productLikes;
    private Unbinder unbinder;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);
        unbinder = ButterKnife.bind(this);

        final Product product = (Product) getIntent().getSerializableExtra("PRODUCT");

        if (product != null) {
            productName.setText("Product Name " + product.getProductName());
            productInfo.setText("Product Info " + product.getInfo());
            productPrice.setText("Product Price " + product.getPrice());
            productDate.setText("Product Date " + product.getEstimatedDate());
            productLikes.setText("Product Likes " + product.getLikes());
        }
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
