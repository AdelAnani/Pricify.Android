package co.pricify.android.pricify.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import co.pricify.android.pricify.R;
import co.pricify.android.pricify.models.User;


public class AddProductFragment extends Fragment {
    private EditText inputProductPageURL;
    private Button buttonAddProduct;
    private String userEmail;
    ProgressDialog progressDialog;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);

        User user;
        user = User.getInstance();
        userEmail = user.userEmail;

        this.getActivity().setTitle("Add Product");

        inputProductPageURL = (EditText) view.findViewById(R.id.editText_addProduct);
        buttonAddProduct = (Button) view.findViewById(R.id.button_addProduct);

        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddProductFragment.this.getContext(), "Ajout du produit..." + userEmail, Toast.LENGTH_SHORT).show();
                progressDialog = ProgressDialog.show(getActivity(),"","Ajout du produit...",false);

                //inputProductPageURL.setText("");
                buttonAddProduct.setEnabled(false);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("email", userEmail);
                    jsonObject.put("url", inputProductPageURL.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                AndroidNetworking.post("https://www.pricify.co/bot/items")
                        .addQueryParameter("email", userEmail)
                        .addJSONObjectBody(jsonObject) // posting json
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // do anything with response
                                inputProductPageURL.setText("");
                                Toast.makeText(AddProductFragment.this.getContext(), "Produit ajouté !", Toast.LENGTH_SHORT).show();
                                buttonAddProduct.setEnabled(true);
                                progressDialog.dismiss();

                            }
                            @Override
                            public void onError(ANError error) {
                                // handle error
                                Toast.makeText(AddProductFragment.this.getContext(), "Error: Product not added", Toast.LENGTH_SHORT).show();
                                buttonAddProduct.setEnabled(true);
                                System.out.println("error");
                                System.out.println(error);
                                progressDialog.dismiss();
                            }
                        });



            }
        });
        return  view;
    }
}
