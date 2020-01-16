package com.example.relife.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.relife.Helpers.DayTurnCallback;
import com.example.relife.Model.Adapter.OrderQueueAdapter;
import com.example.relife.Helpers.FirebaseHelper;
import com.example.relife.Helpers.MyCallback;
import com.example.relife.Model.OrderQueue;
import com.example.relife.Model.TurnsClass;
import com.example.relife.Model.User;
import com.example.relife.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class OrderQueueHourActivity extends AppCompatActivity implements DayTurnCallback {


    DatabaseReference db;
    FirebaseHelper firebaseHelper;
    FirebaseAuth firebaseAuth;
    Intent intent;
    OrderQueueAdapter orderQueueAdapter;
    ArrayList<TurnsClass> turnsClasses = new ArrayList<>();
    OrderQueue orderQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_queue_hour);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0f4c75")));
        }

        intent = getIntent();

        db = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseHelper = new FirebaseHelper(db, this, firebaseAuth);
        orderQueue = (OrderQueue) intent.getSerializableExtra("order");
        firebaseHelper.retrieveTurnsOfToday(orderQueue, this, turnsClasses);


    }


    @Override
    public void dayTurnCallback(ArrayList<TurnsClass> turnsClasses) {

        User user = (User) intent.getSerializableExtra("user");
        String type = intent.getStringExtra("type");
        boolean currentHour = intent.getBooleanExtra("dayone", false);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_order_hour_queue_list);
        orderQueueAdapter = new OrderQueueAdapter(this, this, orderQueue, firebaseHelper, turnsClasses, user, type, currentHour);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(orderQueueAdapter);

    }
}

