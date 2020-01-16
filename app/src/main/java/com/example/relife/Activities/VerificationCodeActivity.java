package com.example.relife.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.relife.Helpers.FirebaseHelper;
import com.example.relife.Model.User;
import com.example.relife.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class VerificationCodeActivity extends AppCompatActivity {

    EditText verificationCodeEditText;
    User user;
    FirebaseAuth firebaseAuth;
    DatabaseReference db;
    String verificationID;
    FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0f4c75")));
        }

        verificationCodeEditText = findViewById(R.id.verification_code_edit_text);
        user = (User) getIntent().getSerializableExtra("user");

        db = FirebaseDatabase.getInstance().getReference();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseHelper = new FirebaseHelper(db, this, null);

        sendVerificationCode();

    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            verificationCodeEditText.setText(phoneAuthCredential.getSmsCode());
            signInWithPhoneAuthCredential(phoneAuthCredential);
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            Log.i("VerificationFailed", e.getMessage() + " + " + e.toString());

        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationID = s;
        }
    };


    private void sendVerificationCode() {


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+972" + user.getPhoneNumber(),        // Phone number to verify
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

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            firebaseHelper.setAuth(firebaseAuth);

                            if (firebaseHelper.save(user)) {
                                Toast.makeText(VerificationCodeActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(VerificationCodeActivity.this, HomePageActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(VerificationCodeActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                                Toast.makeText(VerificationCodeActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                });
    }

    public void sendVerificationCodeBtn(View view) {

        sendVerificationCode();

    }
}
