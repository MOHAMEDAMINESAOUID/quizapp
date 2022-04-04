package com.example.quizzz_app;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser  extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    EditText etLogin;
    EditText etPassword,etConfirm;
    Button et;
    EditText etName;
    TextView AlreadyHaveAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etLogin =findViewById(R.id.etLogin);
        etPassword =findViewById(R.id.etPassword);
        etConfirm=findViewById(R.id.etConfirm);
        btnregister=findViewById(R.id.registerBtn);
        etName=findViewById(R.id.etName);
        AlreadyHaveAccount=findViewById(R.id.AlreadyHaveAccount);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnregister.setOnClickListener(view -> {createUser();
        });
        AlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSignIn();
            }
        });

    }
    public void createUser(){
        String email1=email.getText().toString();
        String password1=password.getText().toString();
        String name1=name.getText().toString();
        String password2=confirmpassword.getText().toString();
        if(TextUtils.isEmpty(email1)){
            email.setError("Email field is empty");
            email.requestFocus();
        }
        if(TextUtils.isEmpty(password1)){
            password.setError("Password field is empty");
            password.requestFocus();
        }
        if(TextUtils.isEmpty(name1)){
            name.setError("Name field is empty");
            name.requestFocus();
        }
        if(!password2.equals(password1)){
            confirmpassword.setError("Password not equals please check the fields");
            confirmpassword.requestFocus();
        }
        else{
            mAuth.createUserWithEmailAndPassword(email1, password1)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String currentUserID = mAuth.getCurrentUser().getUid();
                                Toast.makeText(SignUp.this, "Account Created", Toast.LENGTH_SHORT).show();
                                User user = new User();
                                user.setName1(name1);
                                user.setEmail1(email1);
                                user.setPassword1(password1);
                                mDatabase.child("users").child(currentUserID).setValue(user);
                                startActivity(new Intent(SignUp.this, MainActivity.class));
                            } else {
                                // If sign in fails, display a message to the user
                                Toast.makeText(SignUp.this, "Account not Created"+task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



        }
    }
    void goToSignIn() {
        Intent intent = new Intent(SignUp.this,MainActivity.class);
        startActivity(intent);
    }
}
}
