package com.example.relife.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.example.relife.Helpers.FirebaseHelper;
import com.example.relife.Helpers.MyTurnsCallback;
import com.example.relife.Model.OrderQueue;
import com.example.relife.Model.TurnsClass;
import com.example.relife.Model.User;
import com.example.relife.Model.Adapter.MyTurnAdapter;
import com.example.relife.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class MyTurnsActivity extends AppCompatActivity implements MyTurnsCallback {

    DatabaseReference db;
    FirebaseHelper firebaseHelper;
    FirebaseAuth firebaseAuth;
    Intent intent;
    ArrayList<TurnsClass> turnsClasses = new ArrayList<>();
    Calendar calendar;
    MyTurnAdapter turnAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_turns);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0f4c75")));
        }

        intent = getIntent();
        calendar = Calendar.getInstance();

        db = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseHelper = new FirebaseHelper(db, this, firebaseAuth);

        User user = (User) intent.getSerializableExtra("user");
        OrderQueue orderQueue = new OrderQueue(calendar.get(Calendar.DAY_OF_WEEK), calendar.get(Calendar.WEEK_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
        firebaseHelper.retrieveMyTurnsByWeek(orderQueue, this, user);


    }

    @Override
    public void myTurnsWeekCallback(ArrayList<TurnsClass> turnsClasses) {

        if (turnsClasses != null) {
            this.turnsClasses = turnsClasses;

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_turn_recycler_view);
            turnAdapter = new MyTurnAdapter(this, turnsClasses, this, firebaseHelper);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(turnAdapter);

        }

    }


}
