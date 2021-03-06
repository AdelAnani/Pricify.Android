package co.pricify.android.pricify.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.GridLayoutManager;
import android.widget.Toast;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

import co.pricify.android.pricify.R;
import co.pricify.android.pricify.adapters.ProductsAdapter;
import co.pricify.android.pricify.models.Product;
import co.pricify.android.pricify.models.User;


public class MyProductsFragment extends Fragment {

    private RecyclerView productsRecyclerView;
    private ProductsAdapter productsAdapter;
    private List<Product> productList;
    private String userEmail;

    private SwipeRefreshLayout refreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.getActivity().setTitle("My Products");
        View view = inflater.inflate(R.layout.fragment_my_products, container, false);

//        userEmail = getArguments().getString("userEmail");

        User user;
        user = User.getInstance();
        userEmail = user.userEmail;

        productsRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_myProducts);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);

        refreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getProducts();
            }
        });

        productList = new ArrayList<>();
        productsAdapter = new ProductsAdapter(this.getContext(), productList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this.getContext(), 1);
        productsRecyclerView.setLayoutManager(mLayoutManager);
        productsRecyclerView.setAdapter(productsAdapter);
        getProducts();

        return  view;
    }

    private void getProducts() {
        Toast.makeText(MyProductsFragment.this.getContext(), "Updating...", Toast.LENGTH_SHORT).show();

        AndroidNetworking.get("http://pricify.co/items")
                .addQueryParameter("email", userEmail)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        productList.clear();
                        System.out.println("response");
                        try {
                            JSONArray itemsArray = response.getJSONArray("items");
                            for (int i = 0; i < itemsArray.length(); i++) {
                                JSONObject row = itemsArray.getJSONObject(i);
                                String titre = row.getString("title");
                                URI uri = new URI(row.getString("url"));
                                String domain = uri.getHost();
                                String imageUrl = row.getString("productImageUrl");
                                BigDecimal currentPrice = new BigDecimal(row.getString("productPriceAmount"));
                                productList.add(new Product(titre, currentPrice, domain, imageUrl));
                            }
                            productsAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MyProductsFragment.this.getContext(), "Erreur lors de l'actualisation pour l'utilisateur" + userEmail, Toast.LENGTH_SHORT).show();
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                            Toast.makeText(MyProductsFragment.this.getContext(), "Erreur lors de l'actualisation pour l'utilisateur" + userEmail, Toast.LENGTH_SHORT).show();
                        }
                        refreshLayout.setRefreshing(false);
                        Toast.makeText(MyProductsFragment.this.getContext(), "Update completed for user " + userEmail, Toast.LENGTH_SHORT).show();

                        return;
                    }
                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(MyProductsFragment.this.getContext(), "Erreur lors de l'actualisation pour l'utilisateur" + userEmail, Toast.LENGTH_SHORT).show();
                        refreshLayout.setRefreshing(false);
                        System.out.println(error);
                    }
                });
    }
}
