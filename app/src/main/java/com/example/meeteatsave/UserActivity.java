package com.example.meeteatsave;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

public class UserActivity extends AppCompatActivity {

    private EditText fnET;
    private EditText lnET;
    private EditText ageET;
    private EditText telephonenumberET;
    private EditText streetandhousenumberET;
    private EditText plzET;
    private EditText cityET;
    private TextView fnTV;
    private TextView lnTV;
    private TextView ageTV;
    private TextView telephonenumberTV;
    private TextView streetandhousenumberTV;
    private TextView plzTV;
    private TextView cityTV;
    private Button userButton;
    DatabaseReference databaseUser;
    private AlertDialog dialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        fnET = findViewById(R.id.editTextUserFirstName);
        lnET = findViewById(R.id.editTextLastName);
        ageET = findViewById(R.id.editTextAge);
        telephonenumberET = findViewById(R.id.editTextTelephoneNumber);
        streetandhousenumberET = findViewById(R.id.editTextStreetAndHouseNumber);
        plzET = findViewById(R.id.editTextPlz);
        cityET = findViewById(R.id.editTextCity);
        fnTV = findViewById(R.id.textViewFirstName);
        lnTV = findViewById(R.id.textViewLastName);
        ageTV = findViewById(R.id.textViewAge);
        telephonenumberTV = findViewById(R.id.textViewTelephoneNumber);
        streetandhousenumberTV = findViewById(R.id.textViewStreetAndHouseNumber);
        plzTV = findViewById(R.id.textViewPlz);
        cityTV = findViewById(R.id.textViewCity);
        userButton =findViewById(R.id.userButton);
        dialog = new AlertDialog.Builder(this).create();


        dialog.setTitle("Edit first name");
        dialog.setView(fnET);

        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Save", (dialog, which) -> {
            fnTV.setText(fnET.getText());
        });
        fnTV.setOnClickListener(v -> {

                fnET.setText(fnTV.getText());
                dialog.show();

            if(fnTV.getParent() != null) {
                ((ViewGroup)fnTV.getParent()).removeView(fnTV); // <- fix
            }
        });



    }
}
