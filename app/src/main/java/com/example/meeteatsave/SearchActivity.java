package com.example.meeteatsave;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;


public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity_layout);



        String[] COUNTRIES = new String[] {"München", "Freising", "Berlin", "Köln", "Frankfurt am Main", "Bonn", "Hamburg", "Bremen"};

        ArrayAdapter<String> ad = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_menu_popup_item, COUNTRIES);

        AutoCompleteTextView editTextFilledExposedDropdown = findViewById(R.id.filled_exposed_dropdown);
        editTextFilledExposedDropdown.setAdapter(ad);
    }
}
