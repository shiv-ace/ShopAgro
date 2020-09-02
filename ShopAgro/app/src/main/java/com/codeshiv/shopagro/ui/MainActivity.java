package com.codeshiv.shopagro.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codeshiv.shopagro.R;
import com.codeshiv.shopagro.model.Product;
import com.codeshiv.shopagro.model.ProductRepository;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.hilt.android.AndroidEntryPoint;


import static com.codeshiv.shopagro.utils.PreferenceKeys.IS_LOGGED_IN;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.my_recycler_view)
    protected RecyclerView recyclerView;
    @Inject
    protected ProductRepository productRepository;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        final SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_preference_name), MODE_PRIVATE);
        if (!sharedPreferences.getBoolean(IS_LOGGED_IN, false)) {
            final Intent intent = new Intent(this, SignInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ProductAdapter(productRepository.getProductsList(), this::onProductClicked, this::onProductLikeClickListener));
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    public void onProductClicked(Product product) {

        final Product updatedProduct = productRepository.getUpdatedProduct(product);
        if (updatedProduct == null) {
            return;
        }

        final Intent intent = new Intent(this, ProductDetails.class);
        intent.putExtra("PRODUCT", updatedProduct);
        startActivity(intent);
    }

    public void onProductLikeClickListener(Product product, boolean isLiked) {
        productRepository.updateProductLikes(product, isLiked);
    }
}