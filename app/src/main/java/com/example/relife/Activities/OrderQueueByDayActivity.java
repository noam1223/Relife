package com.example.relife.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.relife.Model.Adapter.OrderQueueByDayAdapter;
import com.example.relife.Model.User;
import com.example.relife.R;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.CyclicBarrier;

public class OrderQueueByDayActivity extends AppCompatActivity {

    Calendar calendar = Calendar.getInstance();
    OrderQueueByDayAdapter orderQueueByDayAdapter;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_queue);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0f4c75")));
        }

        intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");
        String type = intent.getStringExtra("type");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.order_queue_by_day_recycler_view);
        orderQueueByDayAdapter = new OrderQueueByDayAdapter(this, this, user, type);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(orderQueueByDayAdapter);

    }
}
