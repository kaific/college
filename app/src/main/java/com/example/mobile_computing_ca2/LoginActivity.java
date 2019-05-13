package com.example.mobile_computing_ca2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobile_computing_ca2.model.Model;
import com.example.mobile_computing_ca2.model.User;
import com.example.mobile_computing_ca2.model.api.AbstractAPIListener;

/**
 * When the application starts, this activity is called and displayed on the screen
 * where it displays a login input fields for the user to login
 */
public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText emailField = findViewById(R.id.emailText);
        final EditText passwordField = findViewById(R.id.passwordText);
        Button loginBtn = findViewById(R.id.loginBtn);

        // Create event listener for click on login button
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();

                final Model model = Model.getInstance(LoginActivity.this.getApplication());

                // Passport email and password values to the model used to log in for the user and access the CRUD operation
                // an instance of the AbstractAPIListener with onLogin method that is executed when login is successful.
                model.login(email, password, new AbstractAPIListener() {
                    @Override
                    public void onLogin(User user) {
                        if(user != null) {
                            model.setUser(user);
                            Intent intent = new Intent(LoginActivity.this, EnrolmentsActivity.class);
                            //Toast.makeText(LoginActivity.this, "User " + user.getName() + " has logged in!", Toast.LENGTH_LONG).show();
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Invalid login", Toast.LENGTH_LONG).show();
                        }
                        //super.onLogin(user);
                    }
                });
            }
        });
    }
}
