package com.example.meeteatsave;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
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


public class AdActivity extends AppCompatActivity {

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
                            writeNewPost(userId, titleET.getText().toString(), FoodET.getText().toString());

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

        addButton.setOnClickListener(v -> writeNewPost(userId, titleET.getText().toString(), FoodET.getText().toString()));


    }


    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    private void addAd() {

        String title = cityET.getText().toString().trim();


        if (!TextUtils.isEmpty(title)) {
            String id = databaseAds.push().getKey();
            Toast.makeText(this, "Ad added", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "you should add a title", Toast.LENGTH_LONG).show();
        }
    }

    private void writeNewPost(String userId, String title, String body) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = databaseAds.child("Database").push().getKey();
        Ads ads = new Ads(userId, title, body);
        Map<String, Object> postValues = ads.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Ads/" + key, postValues);
        childUpdates.put("/user-ads/" + userId + "/" + key, postValues);

        databaseAds.updateChildren(childUpdates);
    }


}
