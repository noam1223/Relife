package com.example.relife.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.relife.Helpers.FirebaseHelper;
import com.example.relife.Helpers.UserCallback;
import com.example.relife.Model.User;
import com.example.relife.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    EditText phoneNumberEditText, verificationCodeEditText;
    Button phoneNumberBtn;
    FirebaseAuth mAuth;
    FirebaseHelper firebaseHelper;
    String verificationID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0f4c75")));
        }

        mAuth = FirebaseAuth.getInstance();
        phoneNumberEditText = (EditText) findViewById(R.id.phone_number_edit_text);
        phoneNumberBtn = (Button) findViewById(R.id.phone_number_btn);

    }


    public void checkPhoneNumber(View view) {


        String phoneNumber = phoneNumberEditText.getText().toString();

        if (!phoneNumber.equals("")){

            sendVerificationCode(phoneNumber);

        }

    }


    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            signInWithPhoneAuthCredential(phoneAuthCredential);
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {


        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationID = s;
        }
    };


    private void sendVerificationCode(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+972" + phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                callbacks);        // OnVerificationStateChangedCallbacks
    }


    public void verifyCode(View view) {

        String verificationCode = verificationCodeEditText.getText().toString();

        if (verificationCode.equals("")){

            Toast.makeText(this, "Please set verification code", Toast.LENGTH_SHORT).show();

        } else {

            PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificationID, verificationCode);
            signInWithPhoneAuthCredential(phoneAuthCredential);

        }

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {


                            }
                        }
                    }
                });
    }
}
