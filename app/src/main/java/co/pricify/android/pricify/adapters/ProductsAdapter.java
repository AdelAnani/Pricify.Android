package co.pricify.android.pricify.adapters;

import co.pricify.android.pricify.AuthentificationActivity;
import co.pricify.android.pricify.ProductActivity;
import co.pricify.android.pricify.R;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

import java.util.List;

import co.pricify.android.pricify.RegisterActivity;
import co.pricify.android.pricify.fragments.MyProductsFragment;
import co.pricify.android.pricify.models.Product;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Product> productList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView productName, productUrlCompany, productPrice;
        public ImageView picture;
        public LinearLayout productLinearLayout;


        public MyViewHolder(View view) {
            super(view);

            //picture = (ImageView) view.findViewById(R.id.product_card_Picture);
            productName = (TextView) view.findViewById(R.id.product_card_productName);
            productUrlCompany = (TextView) view.findViewById(R.id.product_card_productUrlCompany);
            productPrice = (TextView) view.findViewById(R.id.product_card_productPrice);
            productLinearLayout = (LinearLayout) view.findViewById(R.id.product_card_linearLayout);
        }
    }


    public ProductsAdapter(Context mContext, List<Product> productList) {
        this.mContext = mContext;
        this.productList = productList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Product product = productList.get(position);
        holder.productName.setText("Product : " + product.getName());
        holder.productUrlCompany.setText("Company : " + product.getCompanyUrl());
        holder.productPrice.setText("Current price : " + (product.getCurrentPrice()).toString());

        //Glide.with(mContext).load(product.getPicture()).into(holder.picture);

/*        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });*/

        final Bundle bundle = new Bundle();

        bundle.putString("productName", product.getName());
        bundle.putString("productCompanyUrl", product.getCompanyUrl());
        bundle.putString("productCurrentPrice", product.getCurrentPrice().toString());
        bundle.putString("productHighestPrice", product.getHighestPrice().toString());
        bundle.putString("productLowestPrice", product.getLowestPrice().toString());

        holder.productLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProductActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}