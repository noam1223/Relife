package com.example.relife;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.relife.Activities.HomePageActivity;
import com.example.relife.Activities.LoginActivity;
import com.example.relife.Activities.SignUpActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    ImageView relifeLogoImageView;
    Button signInBtn, loginBtn;
    believe.cht.fadeintextview.TextView logoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0f4c75")));
        }


        auth = FirebaseAuth.getInstance();
        relifeLogoImageView = findViewById(R.id.relife_logo_image_view);
        signInBtn = findViewById(R.id.sign_up_btn);
        loginBtn = findViewById(R.id.login_btn);
        logoTextView = findViewById(R.id.text_view_relife);

        logoTextView.setText(logoTextView.getText().toString());
        logoTextView.setLetterDuration(333);

        signInBtn.setVisibility(View.INVISIBLE);
        loginBtn.setVisibility(View.INVISIBLE);

        relifeLogoImageView.startAnimation(animateRoationLayout(animationListener));


    }

    final Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (auth.getCurrentUser() != null) {

                Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
                startActivity(intent);
                finish();

            } else {
                signInBtn.setVisibility(View.VISIBLE);
                loginBtn.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    public void loginBtnClicked(View view) {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }


    public void signUpBtn(View view) {

        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);

    }

    private Animation animateRoationLayout(Animation.AnimationListener animationListener) {
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        anim.setInterpolator(new LinearInterpolator()); // for smooth animation
        anim.setAnimationListener(animationListener);
        return anim;
    }
}
