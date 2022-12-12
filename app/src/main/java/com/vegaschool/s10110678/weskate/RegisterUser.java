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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener{

    //Declaring views and variables
    private FirebaseAuth mAuth;
    private TextView newUserbtn;
    private ImageView label;
    private EditText editTextFullName, editTextEmail, editTextUsername, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        //Getting reference to Firebase authentication
        mAuth = FirebaseAuth.getInstance();

        //Setting variables to views and buttons
        label = (ImageView) findViewById(R.id.logo_image_create_user);
        label.setOnClickListener(this);
        newUserbtn = (Button) findViewById(R.id.logIn_btn);
        newUserbtn.setOnClickListener(this);

        editTextFullName = (EditText) findViewById(R.id.email_log_tv);
        editTextEmail = (EditText) findViewById(R.id.password_log_tv);
        editTextUsername = (EditText) findViewById(R.id.username_tv);
        editTextPassword = (EditText) findViewById(R.id.password_tv);
    }

    @Override
    public void onClick(View view) {
        //If the label is pressed go back to home screen, if button is pressed add a new user
        switch(view.getId()){
            case R.id.logo_image_create_user:
                startActivity(new Intent(this, LoginActivity.class) );
                break;
            case R.id.logIn_btn:
                AddNewUser();
                finish();
                break;
        }


    }

    private void AddNewUser() {
        //Creating string variables for information from edit text fields
        String fullName = editTextFullName.getText().toString();
        String email = editTextEmail.getText().toString();
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        //Checking if any fields are empty and telling user that they are required
        if(fullName.isEmpty()){
            editTextFullName.setError("A name is required");
            editTextFullName.requestFocus();
            return;
        }

        if(username.isEmpty()){
            editTextUsername.setError("A Username is required");
            editTextUsername.requestFocus();
            return;
        }

        if(username.isEmpty()){
            editTextEmail.setError("A email address is required");
            editTextEmail.requestFocus();
            return;
        }

        if(username.isEmpty()){
            editTextPassword.setError("A password is required");
            editTextPassword.requestFocus();
            return;
        }

        //Checking if email address is valid
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Provide a valid email address");
            editTextEmail.requestFocus();
            return;
        }

        //Firebase requires password length greater than 6
        if(password.length() < 6){
            editTextPassword.setError("Password must be longer than 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        //Creating user and sending their info to Firebase
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()) {

                            UserInfo user = new UserInfo(fullName, username, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.i("MyActivity", "Here");
                                        Toast.makeText(getApplicationContext(), "New user has been created!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Failed to create account. Please try again!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }     else {
                            Toast.makeText(RegisterUser.this, "Failed to create account. Please try again!", Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }
}