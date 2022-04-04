    package com.example.quizzz_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.content.Intent;
import com.google.firebase.auth.FirebaseUser;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ProgressBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
    public class MainActivity extends AppCompatActivity {
        EditText etLogin, etPassword;
        Button etButton;
        TextView etRegister;
        ProgressBar etprogressbar;
        FirebaseAuth mAuth;
        EditText username;
        EditText password;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            etLogin = findViewById(R.id.etLogin);
            etPassword = findViewById(R.id.etPassword);
           etButton = findViewById(R.id.etButton);
           etRegister=findViewById(R.id.etRegister);
           etprogressbar=findViewById(R.id.etprogressbar);
            // Initialize Firebase Auth
            mAuth = FirebaseAuth.getInstance();

            etButton.setOnClickListener(view -> {
                loginUser();
            });


            etRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this,RegisterUser.class));
                }
            });
        }





        public void loginUser(){
            String email1=username.getText().toString();
            String password1=password.getText().toString();
            if(TextUtils.isEmpty(email1)){
                username.setError("Email field is empty");
                username.requestFocus();
            }
            else if(TextUtils.isEmpty(password1)){
                password.setError("Password field is empty");
                password.requestFocus();
            }
            else{
                mAuth.signInWithEmailAndPassword(email1, password1)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success
                                    Toast.makeText(MainActivity.this,"Log in successful",Toast.LENGTH_LONG).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    navigateToSecondActivity();
                                } else {
                                    // If sign in fails, display a message to the user
                                    Toast.makeText(MainActivity.this, "Authentication failed."+task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
        void navigateToSecondActivity(){
            Intent intent = new Intent(MainActivity.this, RegisterUser.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        @Override
        protected void onStart() {
            super.onStart();
            FirebaseUser currentuser=mAuth.getCurrentUser();
            if(currentuser!=null){
                navigateToSecondActivity();
            }
        }
    }
