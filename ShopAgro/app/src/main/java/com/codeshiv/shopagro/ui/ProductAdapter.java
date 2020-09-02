package com.codeshiv.shopagro.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codeshiv.shopagro.R;
import com.codeshiv.shopagro.model.Product;
import com.codeshiv.shopagro.utils.ProductItemClickListener;
import com.codeshiv.shopagro.utils.ProductLikesClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private ProductItemClickListener productItemClickListener;
    private ProductLikesClickListener productLikesClickListener;

    public ProductAdapter(List<Product> products, ProductItemClickListener clickListener, ProductLikesClickListener likesClickListener) {
        productList = products;
        productItemClickListener = clickListener;
        productLikesClickListener = likesClickListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.product_list_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        final ProductViewHolder viewHolder = (ProductViewHolder) holder;
        final Product product = productList.get(position);

        Glide.with(viewHolder.productImage).load(product.getImageUrl()).into(viewHolder.productImage);
        viewHolder.productName.setText(product.getProductName());
        viewHolder.itemView.setOnClickListener(view -> productItemClickListener.onProductClicked(product));
        viewHolder.productLikes.setOnClickListener(view -> productLikesClickListener.onProductLikeClickListener(product, true));
        viewHolder.productDisLikes.setOnClickListener(view -> productLikesClickListener.onProductLikeClickListener(product, false));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.product_image_view)
        ImageView productImage;

        @BindView(R.id.product_name)
        TextView productName;

        @BindView(R.id.product_likes)
        TextView productLikes;

        @BindView(R.id.product_dis_likes)
        TextView productDisLikes;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
