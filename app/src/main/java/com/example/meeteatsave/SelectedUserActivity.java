package com.example.meeteatsave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SelectedUserActivity extends AppCompatActivity {

    TextView tvUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_user);

        tvUser = findViewById(R.id.selectedUser);

        Intent intent =getIntent();

        if(intent.getExtras() != null){
            Ads ads= (Ads) intent.getSerializableExtra("data");
            tvUser.setText(ads.getFoodArt());
        }

    }
}
