package com.example.relife.Model.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.relife.Helpers.FirebaseHelper;
import com.example.relife.Helpers.WeekDayHelper;
import com.example.relife.Model.TurnsClass;
import com.example.relife.R;

import java.util.ArrayList;

public class MyTurnAdapter extends RecyclerView.Adapter<MyTurnAdapter.ViewHolderMyTurns> {

    ArrayList<TurnsClass> turnsClasses = new ArrayList<>();
    WeekDayHelper weekDayHelper = new WeekDayHelper();
    Context context;
    Activity activity;
    FirebaseHelper firebaseHelper;

    public MyTurnAdapter(Context context, ArrayList<TurnsClass> turnsClasses, Activity activity, FirebaseHelper firebaseHelper) {

        this.context = context;
        this.activity = activity;
        this.turnsClasses = turnsClasses;
        this.firebaseHelper = firebaseHelper;

    }

    @NonNull
    @Override
    public ViewHolderMyTurns onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_turns, parent, false);
        return new MyTurnAdapter.ViewHolderMyTurns(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderMyTurns holder, final int position) {

        holder.myTurnTextView.setText("תור בשעה " + turnsClasses.get(position).getHour() + "\n"
                + "ביום " + weekDayHelper.dayByPosition(turnsClasses.get(position).getOrderQueue().getDay()) + "\n"
                + weekDayHelper.formatDateOfTurn(turnsClasses.get(position).getOrderQueue()) + "\n"
                + "לטיפול: " + turnsClasses.get(position).getTypeOfTurn() + "");

        holder.linearLayoutMyTurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom_alert_dialog);

                Button btn1 = (Button) dialog.findViewById(R.id.btn_no);


                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        firebaseHelper.deleteTurn(turnsClasses.get(position).getOrderQueue(), turnsClasses.get(position).getHour());
                        dialog.dismiss();
                        activity.finish();

                    }
                });

                Button btn2 = (Button) dialog.findViewById(R.id.btn_yes);

                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return turnsClasses.size();
    }

    public class ViewHolderMyTurns extends RecyclerView.ViewHolder {

        TextView myTurnTextView;
        LinearLayout linearLayoutMyTurn;

        public ViewHolderMyTurns(@NonNull View itemView) {
            super(itemView);
            myTurnTextView = itemView.findViewById(R.id.item_my_turn_text_view);
            linearLayoutMyTurn = itemView.findViewById(R.id.linear_layout_my_turn);
        }
    }

}
