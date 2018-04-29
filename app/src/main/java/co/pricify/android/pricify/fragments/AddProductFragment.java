package co.pricify.android.pricify.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import co.pricify.android.pricify.AuthentificationActivity;
import co.pricify.android.pricify.R;
import co.pricify.android.pricify.RegisterActivity;


public class AddProductFragment extends Fragment {
    private EditText inputProductPageURL;
    private Button buttonAddProduct;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);

        this.getActivity().setTitle("Add Product");

        inputProductPageURL = (EditText) view.findViewById(R.id.editText_addProduct);
        buttonAddProduct = (Button) view.findViewById(R.id.button_addProduct);

        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddProductFragment.this.getContext(), "Product added successfully", Toast.LENGTH_SHORT).show();
                inputProductPageURL.setText("");
            }
        });
        return  view;
    }
}
