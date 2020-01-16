package com.example.relife.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import com.example.relife.Activities.OrderQueueByDayActivity;
import com.example.relife.Model.User;
import com.example.relife.R;

public class TypeOfTurnActivity extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_of_turn);

        intent = getIntent();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0f4c75")));
        }
    }

    public void hydroTurn(View view) {

        goToOrderQueueByDay("הידרותרפיה");

    }

    public void watsuTurn(View view) {

        goToOrderQueueByDay("וואטסו");

    }

    private void goToOrderQueueByDay(String type) {
        Intent intent = new Intent(this, OrderQueueByDayActivity.class);
        intent.putExtra("user", (User) this.intent.getSerializableExtra("user"));
        intent.putExtra("type", type);
        startActivity(intent);
    }
}
