package com.example.meeteatsave;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailET;
    private EditText passwordET;
    private Button registerButton;
    private Button loginButton;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseAds;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        emailET = findViewById(R.id.uyeEmail);
        passwordET = findViewById(R.id.uyeParola);
        registerButton = findViewById(R.id.registerButton);
        loginButton = findViewById(R.id.backToLoginViewButton);

        databaseAds = FirebaseDatabase.getInstance().getReference("Database");

        firebaseAuth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(v -> {
            String email = emailET.getText().toString();
            String password = passwordET.getText().toString();

            if(TextUtils.isEmpty(email)){
                Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(password)){
                Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
            }

            if(password.length()<6){
                Toast.makeText(getApplicationContext(),"Password must be at least 6 characters",Toast.LENGTH_SHORT).show();
            }

            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            writeNewPost(userId,"","","","","","","","");
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"E-mail or password is wrong",Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        loginButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),LoginActivity.class)));

        if(firebaseAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }


    private void writeNewPost(String uid, String title, String foodArt, String city, String price, String numberOfSeats, String date, String time, String firstname) {
        User user = new User(uid, title, foodArt, city, price, numberOfSeats, date, time, firstname);
        Map<String, Object> postValues = user.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Users/" + uid, postValues);

        databaseAds.updateChildren(childUpdates).addOnSuccessListener(aVoid -> {
            finish();
        })
                .addOnFailureListener(e -> {
                    Toast.makeText(RegisterActivity.this, "Something went wrong, please try again later", Toast.LENGTH_SHORT).show();
                    finish();
                });
    }
}