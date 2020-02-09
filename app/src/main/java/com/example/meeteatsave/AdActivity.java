package com.example.meeteatsave;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AdActivity extends AppCompatActivity implements TextWatcher {

    private EditText cityET;
    private EditText titleET;
    private EditText FoodET;
    private EditText priceET;
    private EditText dateET;
    private EditText timeET;
    private EditText seatsET;
    private Button addButton;
    private DatabaseReference databaseAds;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);

        databaseAds = FirebaseDatabase.getInstance().getReference("Database");

        titleET = findViewById(R.id.editTextTitle);
        FoodET = findViewById(R.id.editTextFood);
        priceET = findViewById(R.id.editTextPrice);
        dateET = findViewById(R.id.editTextDate);
        timeET = findViewById(R.id.editTextTime);
        seatsET = findViewById(R.id.editTextSeats);
        cityET = findViewById(R.id.editTextCity);

        AutoCompleteTextView autoCompleteTextView1 = findViewById(R.id.editTextCity);
        String[] countries = getResources().getStringArray(R.array.list_of_countries);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, countries);
        autoCompleteTextView1.setAdapter(adapter);

        addButton = findViewById(R.id.userButton);

        timeET.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AdActivity.this, (TimePicker timePicker, int selectedHour, int selectedMinute) -> { timeET.setText(selectedHour + ":" + selectedMinute); }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select the Time");
                mTimePicker.show();
            }
        });

        dateET.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                DatePickerDialog datePickerDialog;
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(AdActivity.this, (view1, year1, monthOfYear, dayOfMonth) -> dateET.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1), year, month, day);
                datePickerDialog.show();
            }
        });



        titleET.addTextChangedListener(this);
        seatsET.addTextChangedListener(this);
        cityET.addTextChangedListener(this);
        timeET.addTextChangedListener(this);
        priceET.addTextChangedListener(this);
        FoodET.addTextChangedListener(this);
        dateET.addTextChangedListener(this);

        final String userId = getUid();
        databaseAds.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user == null) {
                        } else {
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

        addButton.setOnClickListener((View v) -> writeNewPost(userId, titleET.getText().toString(), FoodET.getText().toString(), cityET.getText().toString(), Double.parseDouble(priceET.getText().toString()), Integer.parseInt(seatsET.getText().toString()), dateET.getText().toString(), timeET.getText().toString()));

        addButton.setEnabled(false);

    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    private void writeNewPost(String uid, String title, String foodArt, String city, double price, int numberOfSeats, String date, String time) {
        String key = databaseAds.child("Database").push().getKey();
        Ads ads = new Ads(uid, title, foodArt, city, price, numberOfSeats, date, time);
        Map<String, Object> postValues = ads.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Ads/" + key, postValues);
        childUpdates.put("/users/" + uid + "/" ,uid);

        databaseAds.updateChildren(childUpdates).addOnSuccessListener(aVoid -> {
            Toast.makeText(AdActivity.this, "Your ad has been successfully published", Toast.LENGTH_SHORT).show();
            finish();
        })
                .addOnFailureListener(e -> {
                    Toast.makeText(AdActivity.this, "Something went wrong, please try again later", Toast.LENGTH_SHORT).show();
                    finish();
                });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if (cityET.getText().toString().isEmpty()) {
            cityET.setError("Please fill this field");
        } else {
            addButton.setEnabled(true);
        }
        if (FoodET.getText().toString().isEmpty()) {
            FoodET.setError("Please fill this field");
        } else {
            addButton.setEnabled(true);
        }

        if (priceET.getText().toString().isEmpty()) {
            priceET.setError("Please fill this field");
        } else {
            addButton.setEnabled(true);
        }
        if (titleET.getText().toString().isEmpty()) {
            titleET.setError("Please fill this field");
        } else {
            addButton.setEnabled(true);
        }
        if (seatsET.getText().toString().isEmpty()) {
            seatsET.setError("Please fill this field");
        } else {
            addButton.setEnabled(true);
        }
    }


}
