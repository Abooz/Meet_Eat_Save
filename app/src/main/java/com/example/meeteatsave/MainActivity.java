package com.example.meeteatsave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRed, buttonYellow;
    private TextView welcomeTextView;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonRed = findViewById(R.id.buttonRed);
        buttonYellow = findViewById(R.id.buttonYellow);
        welcomeTextView = findViewById(R.id.textViewTitle);

        buttonRed.setOnClickListener(this);
        buttonYellow.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonRed) {
            firebaseAuth.signOut();
            startActivity(new Intent(this, LoginActivity.class));

        }
        if (v.getId() == R.id.buttonYellow) {
            welcomeTextView.setTextColor(getColor(R.color.colorYellow));
            Toast.makeText(this, " the text is now Yellow", Toast.LENGTH_LONG).show();
        }

    }
}
