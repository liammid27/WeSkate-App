package com.vegaschool.s10110678.weskate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    //Declaring views
    private TextView newUser;
    private EditText etEmail, etPassword;
    private Button signIn;
    private boolean loginSuc = false;

    private FirebaseAuth mAuth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Finding views by Id
        newUser = (TextView) findViewById(R.id.newUser_tv);
        newUser.setOnClickListener(this);
        signIn = (Button) findViewById(R.id.logIn_btn);
        signIn.setOnClickListener(this);

        etEmail = (EditText) findViewById(R.id.email_log_tv);
        etPassword = (EditText) findViewById(R.id.password_log_tv);

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            //When Create New User is pressed, open the Register User activity
            case R.id.newUser_tv:
                startActivity(new Intent(this, RegisterUser.class));
                break;

            case R.id.logIn_btn:
                loginUser();
                if(loginSuc == true){
                    startActivity(new Intent(this, HomeScreenActivity.class));
                }
                loginSuc = false;
                break;
        }

    }

    private void loginUser() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if(email.isEmpty()){
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Please enter a valid email");
            etEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            etPassword.setError("Password must be longer than 6 characters");
            etPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    user = mAuth.getCurrentUser();
                    loginSuc = true;
                }
                else{
                    Toast.makeText(LoginActivity.this, "Login failed, please check your information", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void LoadUserPrefs(){

    }
}