package com.example.meeteatsave;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
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

    DatabaseReference databaseAds;

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


        addButton = findViewById(R.id.addButton);

        timeET.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AdActivity.this, (TimePicker timePicker, int selectedHour, int selectedMinute) -> {
                    timeET.setText(selectedHour + ":" + selectedMinute);
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select the Time");
                mTimePicker.show();
            }
        });

        dateET.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                DatePickerDialog datePickerDialog;
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datePickerDialog = new DatePickerDialog(AdActivity.this,
                        (view1, year1, monthOfYear, dayOfMonth) -> dateET.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1), year, month, day);
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
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);


                        // [START_EXCLUDE]
                        if (user == null) {
                            // User is null, error out

                        } else {
                            // Write new post

                        }

                        // Finish this Activity, back to the stream
                        // [END_EXCLUDE]
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
                });
        // [END single_value_read]

        addButton.setOnClickListener((View v) -> {

                writeNewPost(userId, titleET.getText().toString(), FoodET.getText().toString(), cityET.getText().toString(), Double.parseDouble(priceET.getText().toString()), Integer.parseInt(seatsET.getText().toString()), dateET.getText().toString(), timeET.getText().toString());
        });

        addButton.setEnabled(false);

    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    private void writeNewPost(String uid, String title, String foodArt, String city, double price, int numberOfSeats, String date, String time) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = databaseAds.child("Database").push().getKey();
        Ads ads = new Ads(uid, title, foodArt, city, price, numberOfSeats, date, time);
        Map<String, Object> postValues = ads.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Ads/" + key, postValues);
        childUpdates.put("/user-ads/" + uid + "/" + key, postValues);

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

                if (cityET.getText().toString().length()< 3){
                    cityET.setError("Please fill this field");
                }else{
                    addButton.setEnabled(true);
                }
                if (FoodET.getText().toString().length()< 4){
                    FoodET.setError("Please fill this field");
                }else{
                    addButton.setEnabled(true);
                }

                if (priceET.getText().toString().length()< 1){
                    priceET.setError("Please fill this field");
                }else{
                    addButton.setEnabled(true);
                }
                if (titleET.getText().toString().length()< 4){
                    titleET.setError("Please fill this field");
                }else{
                    addButton.setEnabled(true);
                }
                if (seatsET.getText().toString().length()< 1){
                    seatsET.setError("Please fill this field");
                }else{
                    addButton.setEnabled(true);
                }
        }


}
