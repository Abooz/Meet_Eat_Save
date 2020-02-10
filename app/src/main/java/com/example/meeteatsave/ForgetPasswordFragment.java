package com.example.meeteatsave;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ForgetPasswordFragment extends Fragment implements View.OnClickListener {

    private EditText emailET;
    private Button send;
    private Button backToLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.forgot_pass_fragment, parent, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        emailET = view.findViewById(R.id.emailET);
        send = view.findViewById(R.id.sendButton);
        backToLogin = view.findViewById(R.id.backToLoginBT);

        send.setOnClickListener(this);
        backToLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backToLoginBT) {
            ForgetPasswordFragment.this.startActivity(new Intent(ForgetPasswordFragment.this.getContext(), LoginActivity.class));

        } else if (v.getId() == R.id.sendButton) {
            FirebaseAuth.getInstance().sendPasswordResetEmail(emailET.getText().toString())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            new AlertDialog.Builder(Objects.requireNonNull(ForgetPasswordFragment.this.getContext()))
                                    .setTitle("Message Send")
                                    .setMessage("we send have send you a message to your Email, please follow the instructions in the Email")
                                    .setCancelable(false)
                                    .setPositiveButton("ok", (dialog, which) -> ForgetPasswordFragment.this.startActivity(new Intent(ForgetPasswordFragment.this.getContext(), LoginActivity.class))).show();
                        }
                    });
        }
    }
}
