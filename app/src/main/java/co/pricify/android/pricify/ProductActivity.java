package co.pricify.android.pricify;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import co.pricify.android.pricify.R;

public class ProductActivity extends Activity {

    private TextView product_productName, product_companyUrl, product_currentPrice;
    private TextView product_highestPrice, product_lowestPrice;
    private ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        product_productName = (TextView) findViewById(R.id.textView_product_productName);
        product_companyUrl = (TextView) findViewById(R.id.textView_product_companyUrl);
        product_currentPrice = (TextView) findViewById(R.id.textView_product_currentPrice);
        product_highestPrice = (TextView) findViewById(R.id.textView_product_highestPrice);
        product_lowestPrice = (TextView) findViewById(R.id.textView_product_lowestPrice);
        picture = (ImageView) findViewById(R.id.imageView_product_picture);
        Intent bundle = getIntent();
        Bundle bundleExtra = bundle.getExtras();

        product_productName.setText("Product : " + bundleExtra.getString("productName"));
        product_companyUrl.setText("Company : " + bundleExtra.getString("productCompanyUrl"));
        product_currentPrice.setText("Current Price : " + bundleExtra.getString("productCurrentPrice"));
        product_highestPrice.setText(bundleExtra.getString("productHighestPrice"));
        product_lowestPrice.setText(bundleExtra.getString("productLowestPrice"));
        product_lowestPrice.setText(bundleExtra.getString("productLowestPrice"));

        Picasso.get().load(bundleExtra.getString("productImageUrl")).into(picture);

    }

}
