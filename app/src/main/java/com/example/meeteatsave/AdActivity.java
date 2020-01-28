package com.example.meeteatsave;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdActivity extends AppCompatActivity {

    EditText editTextName;
    Button addButton;

    DatabaseReference databaseAds;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);


        databaseAds = FirebaseDatabase.getInstance().getReference("Ads");

        addButton = findViewById(R.id.addButton);
        editTextName = findViewById(R.id.editTextName);

        addButton.setOnClickListener(v -> addAd());
    }
    private void addAd(){

    String title = editTextName.getText().toString().trim();


    if (!TextUtils.isEmpty(title)){
        String id = databaseAds.push().getKey();

        Ads ads = new Ads(id, title);

        databaseAds.child(id).setValue(ads);
        Toast.makeText(this, "Ad added", Toast.LENGTH_LONG).show();
    }else{
        Toast.makeText(this, "you should add a title", Toast.LENGTH_LONG).show();
    }
    }





}
