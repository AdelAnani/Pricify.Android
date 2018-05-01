package co.pricify.android.pricify.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import co.pricify.android.pricify.R;
import co.pricify.android.pricify.adapters.ProductsAdapter;
import co.pricify.android.pricify.models.Product;

public class TrendingsFragment extends Fragment {
    private RecyclerView productsRecyclerView;
    private ProductsAdapter productsAdapter;
    private List<Product> productList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.getActivity().setTitle("Trendings");

        View view = inflater.inflate(R.layout.fragment_trendings, container, false);

        productsRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_trendings);
        productList = new ArrayList<>();
        productsAdapter = new ProductsAdapter(this.getContext(), productList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this.getContext(), 1);
        productsRecyclerView.setLayoutManager(mLayoutManager);
        //productsRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        //productsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        productsRecyclerView.setAdapter(productsAdapter);
        getTrendingsProducts();

        return  view;
    }

    private void getTrendingsProducts() {

        BigDecimal currentPrice01 = new BigDecimal("123.99");
        BigDecimal currentPrice02 = new BigDecimal("223.99");
        BigDecimal currentPrice03 = new BigDecimal("433.99");
        BigDecimal currentPrice04 = new BigDecimal("13.99");
        BigDecimal currentPrice05 = new BigDecimal("23.99");
        BigDecimal currentPrice06 = new BigDecimal("201.99");
        BigDecimal highestPrice01 = new BigDecimal("123.02");
        BigDecimal highestPrice02 = new BigDecimal("123.02");
        BigDecimal highestPrice03 = new BigDecimal("123.02");
        BigDecimal highestPrice04 = new BigDecimal("123.02");
        BigDecimal highestPrice05 = new BigDecimal("123.02");
        BigDecimal highestPrice06 = new BigDecimal("123.02");
        BigDecimal lowestPrice01 = new BigDecimal("123.02");
        BigDecimal lowestPrice02 = new BigDecimal("123.02");
        BigDecimal lowestPrice03 = new BigDecimal("123.02");
        BigDecimal lowestPrice04 = new BigDecimal("123.02");
        BigDecimal lowestPrice05 = new BigDecimal("123.02");
        BigDecimal lowestPrice06 = new BigDecimal("123.02");


        /*Product product01 = new Product("Beats By Dre", currentPrice01, highestPrice01, lowestPrice01, "www.amazon.ca");
        productList.add(product01);

        Product product02 = new Product("Nike Cap", currentPrice02, highestPrice02, lowestPrice02, "www.nike.ca");
        productList.add(product02);

        Product product03 = new Product("Iphone 6", currentPrice03, highestPrice03, lowestPrice03, "www.apple.ca");
        productList.add(product03);

        Product product04 = new Product("Banana", currentPrice04, highestPrice04, lowestPrice04, "www.banana.ca");
        productList.add(product04);

        Product product05 = new Product("Sunglasses", currentPrice05, highestPrice05, lowestPrice05, "www.zara.ca");
        productList.add(product05);

        Product product06 = new Product("7R", currentPrice06, highestPrice06, lowestPrice06, "www.7r.ca");
        productList.add(product06);*/

        productsAdapter.notifyDataSetChanged();
    }
}
