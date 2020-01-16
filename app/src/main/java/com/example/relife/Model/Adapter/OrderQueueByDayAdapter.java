package com.example.relife.Model.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.relife.Helpers.WeekDayHelper;
import com.example.relife.Model.OrderQueue;
import com.example.relife.Model.User;
import com.example.relife.Activities.OrderQueueHourActivity;
import com.example.relife.R;

import java.util.ArrayList;
import java.util.Calendar;

public class OrderQueueByDayAdapter extends RecyclerView.Adapter<OrderQueueByDayAdapter.ViewHolderByDay>{

    int day, week, month, dayInMonth, year, dayInYear;
    WeekDayHelper weekDayHelper  = new WeekDayHelper();

    Context context;
    Activity activity;
    ArrayList<OrderQueue> orderQueueArrayList;
    Calendar calendar = Calendar.getInstance();
    User user;
    String type;

    public OrderQueueByDayAdapter(Context context, Activity activity, User user, String type) {
        this.context = context;
        this.activity = activity;
        this.user = user;
        this.type = type;
        this.day = calendar.get(Calendar.DAY_OF_WEEK);
        this.week = calendar.get(Calendar.WEEK_OF_MONTH);
        this.month = calendar.get(Calendar.MONTH);
        this.dayInMonth = calendar.get(Calendar.DAY_OF_MONTH);
        this.year = calendar.get(Calendar.YEAR);
        this.dayInYear = calendar.get(Calendar.DAY_OF_YEAR);
        this.orderQueueArrayList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolderByDay onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_queue_by_day, parent, false);
        return new OrderQueueByDayAdapter.ViewHolderByDay(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderByDay holder, final int position) {

        if (day == 8){
            week++;
            day = 1;
        }

        if (dayInMonth == calendar.getActualMaximum(Calendar.DAY_OF_MONTH)){
            month++;
        }

        if (dayInYear == calendar.getActualMaximum(Calendar.DAY_OF_YEAR)){
            year++;
        }

        OrderQueue orderQueue = new OrderQueue(day, week, month, year);
        orderQueue.setDayInMonth(dayInMonth);
        orderQueueArrayList.add(orderQueue);
        holder.orderByDayTextView.setText(weekDayHelper.dayByPosition(day - 1));
        dayInMonth++;
        day++;

        holder.orderByDayLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, OrderQueueHourActivity.class);
                intent.putExtra("order", orderQueueArrayList.get(position));
                intent.putExtra("user", user);
                intent.putExtra("type", type);
                if (position == 0){
                    intent.putExtra("dayone", true);
                }
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public class ViewHolderByDay extends RecyclerView.ViewHolder {

        TextView orderByDayTextView;
        LinearLayout orderByDayLinearLayout;

        public ViewHolderByDay(@NonNull View itemView) {
            super(itemView);
            orderByDayTextView = itemView.findViewById(R.id.order_queue_by_day_text_view);
            orderByDayLinearLayout = itemView.findViewById(R.id.order_queue_by_day_linear_layout);
        }
    }

}
