package co.pricify.android.pricify;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.app.ProgressDialog;
import android.util.Base64;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

import co.pricify.android.pricify.fragments.AddProductFragment;
import co.pricify.android.pricify.fragments.MyProductsFragment;
import co.pricify.android.pricify.fragments.SettingsFragment;
import co.pricify.android.pricify.fragments.TrendingsFragment;
import co.pricify.android.pricify.models.User;

public class ProfilActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String userProfilPic;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Pricify");

        User user;
        user = User.getInstance();
        userEmail = user.userEmail;

        //storage = FirebaseStorage.getInstance();
        //storageReference = storage.getReference();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profil, menu);


        ImageView myImage = (ImageView)findViewById(R.id.ProfileImage);

        File directory = getApplicationContext().getCacheDir();
        // Create imageDir
        Log.d("Directory= ",directory.getAbsolutePath());
        File mypath=new File(directory,"profile.jpg");

        File imgFile = mypath;

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            myImage.setImageBitmap(myBitmap);

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_addProduct) {
            AddProductFragment addProductFragment = new AddProductFragment();
            Bundle bundle = new Bundle();
            bundle.putString("userEmail", userEmail);
            addProductFragment.setArguments(bundle);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.profil_layout, addProductFragment, addProductFragment.getTag()).commit();

        } else if (id == R.id.nav_myProducts) {
            MyProductsFragment myProductsFragment = new MyProductsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("userEmail", userEmail);
            myProductsFragment.setArguments(bundle);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.profil_layout, myProductsFragment, myProductsFragment.getTag()).commit();

        } else if (id == R.id.nav_trending) {
            TrendingsFragment trendingsFragment = new TrendingsFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.profil_layout, trendingsFragment, trendingsFragment.getTag()).commit();

        } else if (id == R.id.nav_settings) {
            SettingsFragment settingsFragment = new SettingsFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.profil_layout, settingsFragment, settingsFragment.getTag()).commit();

        } else if (id == R.id.nav_signOut) {
            FirebaseAuth.getInstance().signOut();
            Context profilContext = (Context) ProfilActivity.this;
            Intent intent = new Intent(profilContext, AuthentificationActivity.class);
            profilContext.startActivity(intent);
        } else {
            TrendingsFragment trendingsFragment = new TrendingsFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.profil_layout, trendingsFragment, trendingsFragment.getTag()).commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void UpdateProfilePicture(View view){
        Intent camera_intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera_intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 0:
                if(resultCode==RESULT_OK){
                    ImageView profilPic = (ImageView) findViewById(R.id.ProfileImage);
                    Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                    userProfilPic = encodeTobase64(thumbnail);

                    profilPic.setImageBitmap(thumbnail);
                    saveToInternalStorage(thumbnail);

                }
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        //String path = cw.getCacheDir().getAbsolutePath();
        File directory = cw.getCacheDir();
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }


    public static String encodeTobase64(Bitmap image)
    {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

}
