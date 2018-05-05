package co.pricify.android.pricify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;
import android.content.Intent;
import android.text.TextUtils;
import android.support.annotation.NonNull;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import co.pricify.android.pricify.fragments.AddProductFragment;

public class RegisterActivity extends AppCompatActivity {

    private EditText inputUsername, inputEmail, inputPassword, inputConfirmPassword;
    private Button buttonSignUp, buttonToAuthentification;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("");

        auth = FirebaseAuth.getInstance();

        inputUsername = (EditText) findViewById(R.id.editText_register_username);
        inputEmail = (EditText) findViewById(R.id.editText_register_email);
        inputPassword = (EditText) findViewById(R.id.editText_register_password);
        inputConfirmPassword = (EditText) findViewById(R.id.editText_register_confirmPassword);

        buttonSignUp = (Button) findViewById(R.id.button_register_signUp);
        buttonToAuthentification = (Button) findViewById(R.id.button_register_toAuthentification);

        buttonToAuthentification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(RegisterActivity.this, AuthentificationActivity.class));
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = inputUsername.getText().toString().trim();
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

                if (password.length() < 5) {
                    Toast.makeText(getApplicationContext(), "The password is too short. Minimum 5 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(RegisterActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Registration failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Account created.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("username", username);
                    jsonObject.put("email", email);
                    jsonObject.put("emailNotification", "true");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                AndroidNetworking.post("https://www.pricify.co/users")
                        .addJSONObjectBody(jsonObject) // posting json
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // do anything with response
                                Toast.makeText(RegisterActivity.this, "User added successfully", Toast.LENGTH_SHORT).show();
                                buttonSignUp.setEnabled(true);
                            }
                            @Override
                            public void onError(ANError error) {
                                // handle error
                                Toast.makeText(RegisterActivity.this, "Error: User not added", Toast.LENGTH_SHORT).show();
                                buttonSignUp.setEnabled(true);
                                System.out.println("error");
                                System.out.println(error);
                            }
                        });


            }
        });
    }
}
