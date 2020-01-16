package com.example.relife.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.relife.Model.User;
import com.example.relife.Helpers.UserHelper;
import com.example.relife.R;

public class SignUpActivity extends AppCompatActivity {

    EditText phoneNumberEditText, userNameEditText;
    UserHelper user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0f4c75")));
        }

        userNameEditText = findViewById(R.id.user_name_edit_text_sign_up);
        phoneNumberEditText = findViewById(R.id.phone_number_edit_text_sign_up);
    }

    public void signUp(View view) {

        user = new UserHelper(new User(userNameEditText.getText().toString(), phoneNumberEditText.getText().toString()));
        if (user.isValidData()) {
            Toast.makeText(this, "The information is not fulfilled properlly", Toast.LENGTH_SHORT).show();
        } else {

            Intent intent = new Intent(this, VerificationCodeActivity.class);
            intent.putExtra("user", user.getUser());
            startActivity(intent);

        }

    }


}
