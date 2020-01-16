package com.example.relife.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.relife.Helpers.FirebaseHelper;
import com.example.relife.Helpers.MyCallback;
import com.example.relife.Model.User;
import com.example.relife.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomePageActivity extends AppCompatActivity implements MyCallback {

    DatabaseReference db;
    FirebaseHelper firebaseHelper;
    FirebaseAuth firebaseAuth;
    Intent intent;
    User user;
    VideoView videoView;
    ImageView facebook, instagram;
    ScrollView scrollView;
    ImageView arrowImage;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        intent = getIntent();
        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0f4c75")));
        }
        arrowImage = findViewById(R.id.image_arrow_up);

        arrowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                scrollView.scrollTo(0,0);

            }
        });


        scrollView = findViewById(R.id.home_page_scroll_view);
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {

                if ((i == 0) && (i1 == 0)){

                    arrowImage.setVisibility(View.INVISIBLE);


                } else arrowImage.setVisibility(View.VISIBLE);

            }
        });

        db = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        facebook = findViewById(R.id.facebook_icon);

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.facebook.com/relifetherapy/"));
                startActivity(intent);

            }
        });

        instagram = findViewById(R.id.instagram_icon);
        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.instagram.com/relife_therapy/?hl=en"));
                startActivity(intent);

            }
        });

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
        } else {
            firebaseHelper = new FirebaseHelper(db, this, firebaseAuth);
            firebaseHelper.getUser(this);
        }

        videoView = (VideoView) findViewById(R.id.relife_video_home_page);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoView.start();
            }
        });


            actionBar.hide();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Uri path = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.relife_home_page_video);
        videoView.setVideoURI(path);
        videoView.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.item_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == R.id.order_queue) {
                Intent intent = new Intent(this, TypeOfTurnActivity.class);
                intent.putExtra("user", this.user);
                startActivity(intent);

        } else if (item.getItemId() == R.id.logout) {

            if (firebaseAuth.getCurrentUser() != null) {
                firebaseAuth.signOut();
                finish();
            }

        } else if (item.getItemId() == R.id.my_turns_queue) {

                Intent intent = new Intent(this, MyTurnsActivity.class);
                intent.putExtra("user", this.user);
                startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void callback(User user) {

        if (user != null) {

            this.user = user;
            actionBar.show();

        } else finish();

    }

    public void orderTurn(View view) {

        Intent intent = new Intent(this, TypeOfTurnActivity.class);
        intent.putExtra("user", this.user);
        startActivity(intent);

    }

    public void orderHydroTurn(View view) {

        Intent intent = new Intent(this, OrderQueueByDayActivity.class);
        intent.putExtra("user", this.user);
        intent.putExtra("type", "הידרותרפיה");
        startActivity(intent);

    }

    public void orderWatsuTurn(View view) {

        Intent intent = new Intent(this, OrderQueueByDayActivity.class);
        intent.putExtra("user", this.user);
        intent.putExtra("type", "וואטסו");
        startActivity(intent);

    }
}
