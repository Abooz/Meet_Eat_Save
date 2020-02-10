package com.example.meeteatsave;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserActivity extends AppCompatActivity {

    private TextView firstNameTV;
    private TextView lastNameTV;
    private TextView ageTV;
    private TextView telephoneNumberTV;
    private TextView streetAndHouseNumberTV;
    private TextView plzTV;
    private TextView cityTV;
    private TextView genderTV;
    private DatabaseReference mDatabase;
    private final String userId = getUid();
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        firstNameTV = findViewById(R.id.textViewFirstName);
        lastNameTV = findViewById(R.id.textViewLastName);
        ageTV = findViewById(R.id.textViewAge);
        genderTV = findViewById(R.id.genderTV);
        telephoneNumberTV = findViewById(R.id.textViewTelephoneNumber);
        streetAndHouseNumberTV = findViewById(R.id.textViewStreetAndHouseNumber);
        plzTV = findViewById(R.id.textViewPlz);
        cityTV = findViewById(R.id.textViewCity);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setOnClickListener(v -> startActivity(new Intent(UserActivity.this, MainActivity.class)));

        setClickable();

        onClickListeners();

        mDatabase = FirebaseDatabase.getInstance().getReference("Database/Users/");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    User user = dataSnapshot1.getValue(User.class);
                    if (user.getUid().equals(userId)) {
                        if (user.getSex().equals("") || user.getPlz().equals("") || user.getAge().equals("") || user.getTelephoneNumber().equals("")
                                || user.getStreetAndHouseNumber().equals("") || user.getCity().equals("") || user.getUserLastName().equals("")
                                || user.getUserFirstName().equals("") ){
                        }else{
                            firstNameTV.setText(user.getUserFirstName());
                            lastNameTV.setText(user.getUserLastName());
                            ageTV.setText(user.getAge());
                            telephoneNumberTV.setText(user.getTelephoneNumber());
                            streetAndHouseNumberTV.setText(user.getStreetAndHouseNumber());
                            plzTV.setText(user.getPlz());
                            genderTV.setText(user.getSex());
                            cityTV.setText(user.getCity());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UserActivity.this, "Something went wrong, please try again later", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    public void dialogView(String title, String hint, String child, TextView tv) {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(UserActivity.this).create();
        LayoutInflater inflater = UserActivity.this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_layout, null);

        TextView textView = dialogView.findViewById(R.id.titleTextView);
        EditText editText = dialogView.findViewById(R.id.edt_comment);
        Button submitButton = dialogView.findViewById(R.id.buttonSubmit);
        Button cancelButton = dialogView.findViewById(R.id.buttonCancel);
        if (tv.getId() == ageTV.getId()) {
            InputFilter[] fArray = new InputFilter[1];
            fArray[0] = new InputFilter.LengthFilter(2);
            editText.setFilters(fArray);
            editText.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        } else if (tv.getId() == telephoneNumberTV.getId()) {
            editText.setKeyListener(DigitsKeyListener.getInstance("0123456789+"));
        } else if (tv.getId() == plzTV.getId()) {
            editText.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        }

        textView.setText(title);
        editText.setHint(hint);

        cancelButton.setOnClickListener(view -> dialogBuilder.dismiss());
        submitButton.setOnClickListener(v -> {
            String getText = editText.getText().toString();
            if (getText.equals("")) {
                editText.setError("This Field cant be Empty");
            } else {
                mDatabase.child(userId).child(child).setValue(getText);
                tv.setText(getText);
                dialogBuilder.dismiss();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    public void setClickable() {
        firstNameTV.setClickable(true);
        ageTV.setClickable(true);
        cityTV.setClickable(true);
        lastNameTV.setClickable(true);
        plzTV.setClickable(true);
        genderTV.setClickable(true);
        streetAndHouseNumberTV.setClickable(true);
        telephoneNumberTV.setClickable(true);
    }

    public void onClickListeners() {
        firstNameTV.setOnClickListener(v -> {
            dialogView("First Name", "Please Enter your First Name", "userFirstName", firstNameTV);
        });
        ageTV.setOnClickListener(v -> {
            dialogView("Age", "How Old are you?", "age", ageTV);
        });
        cityTV.setOnClickListener(v -> {
            dialogView("City", "Please Enter your City", "city", cityTV);
        });
        lastNameTV.setOnClickListener(v -> {
            dialogView("Last Name", "Please Enter your Last Name", "userLastName", lastNameTV);
        });
        plzTV.setOnClickListener(v -> {
            dialogView("PLZ", "Please Enter your PLZ", "plz", plzTV);
        });
        streetAndHouseNumberTV.setOnClickListener(v -> {
            dialogView("Street and House number", "Please Enter your Street and House number", "streetAndHouseNumber", streetAndHouseNumberTV);
        });
        telephoneNumberTV.setOnClickListener(v -> {
            dialogView("Phone Number", "Please Enter your Phone Number", "telephoneNumber", telephoneNumberTV);
        });
        genderTV.setOnClickListener(v -> {
            dialogView("Gender", "Please Enter your Gender", "sex", genderTV);
        });
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

}
