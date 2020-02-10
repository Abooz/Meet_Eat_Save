package com.example.meeteatsave;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SelectedAdActivity extends AppCompatActivity {

    public static final String SELECTED_AD = "selectedAd";
    private Ads a = new Ads();

    public static Intent createIntent(Context context, Ads selectedAd) {
        Intent intent = new Intent(context, SelectedAdActivity.class);
        intent.putExtra(SELECTED_AD, selectedAd);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_user);

        TextView titleTV = findViewById(R.id.selectedTitleView);
        TextView cityTV = findViewById(R.id.selectedCityView);
        TextView dateTV = findViewById(R.id.selectedDateView);
        TextView timeTV = findViewById(R.id.selectedTimeView);
        TextView priceTV = findViewById(R.id.selectedPriceView);
        TextView seatsTV = findViewById(R.id.selectedSeatsView);
        TextView foodTV = findViewById(R.id.selectedFoodView);
        TextView titleToolbarTV = findViewById(R.id.titleToolbar);
        Button reserve = findViewById(R.id.reserve);
        reserve.setOnClickListener(v -> {// get the Upload ID from the database and show the content in recyclerview
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOnClickListener(v -> onSupportNavigateUp());


        Intent intent = getIntent();

        if (intent.hasExtra(SELECTED_AD)) {
            Ads ads = (Ads) intent.getSerializableExtra(SELECTED_AD);
            titleToolbarTV.setText(ads.getTitle());
            titleTV.setText(ads.getTitle());
            cityTV.setText(ads.getCity());
            dateTV.setText(ads.getDate());
            timeTV.setText(ads.getTime() + " Uhr");
            priceTV.setText(ads.getPrice() + " €");
            seatsTV.setText(ads.getNumberOfSeats());
            foodTV.setText(ads.getFoodArt());
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}