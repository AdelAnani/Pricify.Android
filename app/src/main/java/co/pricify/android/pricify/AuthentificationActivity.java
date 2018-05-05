package co.pricify.android.pricify;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.text.TextUtils;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import co.pricify.android.pricify.models.User;

public class AuthentificationActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private Button buttonLogin, buttonToRegister;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);
        setTitle("");

        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null){
            finish();
            User user;
            user = User.getInstance();
            user.userEmail = auth.getCurrentUser().getEmail();;
            startActivity(new Intent(getApplicationContext(), ProfilActivity.class));
        }

        inputEmail = (EditText) findViewById(R.id.editText_authentification_email);
        inputPassword = (EditText) findViewById(R.id.editText_authentification_password);

        buttonLogin = (Button) findViewById(R.id.button_authentification_login);
        buttonToRegister = (Button) findViewById(R.id.button_authentification_toRegister);

        buttonToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(AuthentificationActivity.this, RegisterActivity.class));
            }
        });


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please enter an email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please enter a password", Toast.LENGTH_SHORT).show();
                    return;
                }

//                final Bundle bundleUser = new Bundle();
//                bundleUser.putString("userEmail", email);

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(AuthentificationActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(AuthentificationActivity.this, "Authentification failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(AuthentificationActivity.this, "Connexion succesful.",
                                            Toast.LENGTH_SHORT).show();
                                    finish();

                                    User user;
                                    user = User.getInstance();
                                    user.userEmail = auth.getCurrentUser().getEmail();

                                    Context authContext = (Context) AuthentificationActivity.this;
                                    Intent intent = new Intent(authContext, ProfilActivity.class);
//                                    intent.putExtras(bundleUser);
                                    authContext.startActivity(intent);
                                    //startActivity(new Intent(AuthentificationActivity.this, ProfilActivity.class));
                                }
                            }
                        });
            }
        });
    }
}
