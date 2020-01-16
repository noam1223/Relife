package com.example.relife.Model.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.relife.Helpers.FirebaseHelper;
import com.example.relife.Helpers.WeekDayHelper;
import com.example.relife.Model.OrderQueue;
import com.example.relife.Model.TurnsClass;
import com.example.relife.Model.User;
import com.example.relife.R;

import java.util.ArrayList;
import java.util.Calendar;

public class OrderQueueAdapter extends RecyclerView.Adapter<OrderQueueAdapter.ViewHolderOrderQueue> {

    WeekDayHelper weekDayHelper = new WeekDayHelper();
    Context context;
    Activity activity;
    OrderQueue orderQueue;
    FirebaseHelper firebaseHelper;
    ArrayList<TurnsClass> turnsClasses = new ArrayList<>();
    User user;
    boolean isTurnCheck = true;
    ArrayList<String> hoursLeft = new ArrayList<>();
    ArrayList<String> dayHours = new ArrayList<>();
    String type;
    boolean currentDay;

    public OrderQueueAdapter(Context context, Activity activity, OrderQueue orderQueue, FirebaseHelper firebaseHelper, ArrayList<TurnsClass> turnsClasses, User user, String type, boolean currentDay) {

        this.context = context;
        this.activity = activity;
        this.orderQueue = orderQueue;
        this.firebaseHelper = firebaseHelper;
        this.turnsClasses = turnsClasses;
        this.user = user;
        this.type = type;
        this.currentDay = currentDay;
        this.onCheckTurn();
        this.hoursLeft = onCheckHoursLeft();
    }


    private void onCheckTurn() {

        for (int i = 0; i < turnsClasses.size(); i++) {
            if (turnsClasses.get(i).getUser().getPhoneNumber().equals(user.getPhoneNumber())) {
                isTurnCheck = false;
            }
        }
    }


    private ArrayList<String> onCheckHoursLeft() {

        ArrayList<String> arrangeHourDay = new ArrayList<>();

        if (currentDay){

            int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

            for (String eachHour :
                    weekDayHelper.getHours()) {

                String[] string = eachHour.split(":");
                if (Integer.valueOf(string[0]) >= hour){

                    arrangeHourDay.add(eachHour);

                }

            }

        } else arrangeHourDay = weekDayHelper.hoursToArrayList();

        ArrayList<String> strings = new ArrayList<>();

        for (int i = 0; i < arrangeHourDay.size() ; i++) {

            boolean isExist = false;

            for (int j = 0; j < turnsClasses.size(); j++) {

                if (turnsClasses.get(j).getHour().equals(arrangeHourDay.get(i))) {

                    isExist = true;

                }

            }

            if (!isExist) {

                strings.add(arrangeHourDay.get(i));

            }

        }

        return strings;

    }


    @NonNull
    @Override
    public ViewHolderOrderQueue onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hour_order_queue, parent, false);
        return new ViewHolderOrderQueue(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderOrderQueue holder, final int position) {

        if (!hoursLeft.isEmpty()) {
            holder.hourTextView.setText(hoursLeft.get(position));
        } else holder.hourTextView.setText(weekDayHelper.hourByPosition(position));


        holder.linearLayoutOrderQueueHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TurnsClass turnsClass = new TurnsClass(orderQueue, weekDayHelper.hourByPosition(position), user, type);

                if (hoursLeft != null) {

                    if (isTurnCheck) {

                        firebaseHelper.saveTurn(turnsClass, hoursLeft.get(position));
                        activity.finish();

                    } else {
                        Toast.makeText(context, "Can't save 2 turns in a day", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    firebaseHelper.saveTurn(turnsClass, weekDayHelper.hourByPosition(position));

                }


            }
        });

    }

    @Override
    public int getItemCount() {



        if (hoursLeft.size() == 0) {
            return weekDayHelper.getWeekdaysSize();
        } else return hoursLeft.size();
    }


    public class ViewHolderOrderQueue extends RecyclerView.ViewHolder {

        TextView hourTextView;
        LinearLayout linearLayoutOrderQueueHour;

        public ViewHolderOrderQueue(@NonNull View itemView) {
            super(itemView);
            hourTextView = itemView.findViewById(R.id.item_hour_text_view);
            linearLayoutOrderQueueHour = itemView.findViewById(R.id.linear_layout_order_queue_hour);
        }
    }

}
