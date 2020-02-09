package com.example.meeteatsave;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class UserActivity extends AppCompatActivity {

    private TextView fnTV;
    private TextView lnTV;
    private TextView ageTV;
    private TextView telephoneNumberTV;
    private TextView streetAndHouseNumberTV;
    private TextView plzTV;
    private TextView cityTV;
    private TextView genderTV;
    private DatabaseReference mDatabase;
    private final String userId = getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        fnTV = findViewById(R.id.textViewFirstName);
        lnTV = findViewById(R.id.textViewLastName);
        ageTV = findViewById(R.id.textViewAge);
        genderTV = findViewById(R.id.genderTV);
        telephoneNumberTV = findViewById(R.id.textViewTelephoneNumber);
        streetAndHouseNumberTV = findViewById(R.id.textViewStreetAndHouseNumber);
        plzTV = findViewById(R.id.textViewPlz);
        cityTV = findViewById(R.id.textViewCity);



        setClickable();

        onClickListeners();

        mDatabase = FirebaseDatabase.getInstance().getReference("Database/Users/");
        mDatabase.child("users").child(userId).child("uId").setValue(userId).addOnFailureListener(e -> Log.d("log", Objects.requireNonNull(e.getLocalizedMessage())));
        mDatabase = FirebaseDatabase.getInstance().getReference("Database/Users/");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    User user = dataSnapshot1.getValue(User.class);
                    String usrtr=user.getuId();
                    String aaa = user.getUserFirstName();
                    if (user.getuId().equals(userId)){
                        fnTV.setText(user.getUserFirstName());
                        lnTV.setText(user.getUserLastName());
                        ageTV.setText(user.getAge());
                        telephoneNumberTV.setText(user.getTelephoneNumber());
                        streetAndHouseNumberTV.setText(user.getStreetAndHouseNumber());
                        plzTV.setText(user.getPlz());
                        genderTV.setText(user.getSex());
                        cityTV.setText(user.getCity());
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
                mDatabase.child("users").child(userId).child(child).setValue(getText);
                tv.setText(getText);
                dialogBuilder.dismiss();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    public void setClickable() {
        fnTV.setClickable(true);
        ageTV.setClickable(true);
        cityTV.setClickable(true);
        lnTV.setClickable(true);
        plzTV.setClickable(true);
        genderTV.setClickable(true);
        streetAndHouseNumberTV.setClickable(true);
        telephoneNumberTV.setClickable(true);
    }

    public void onClickListeners() {
        fnTV.setOnClickListener(v -> {
            dialogView("First Name", "Please Enter your First Name", "userFirstName", fnTV);
        });
        ageTV.setOnClickListener(v -> {
            dialogView("Age", "How Old are you?", "age", ageTV);
        });
        cityTV.setOnClickListener(v -> {
            dialogView("City", "Please Enter your City", "city", cityTV);
        });
        lnTV.setOnClickListener(v -> {
            dialogView("Last Name", "Please Enter your Last Name", "userLastName", lnTV);
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
