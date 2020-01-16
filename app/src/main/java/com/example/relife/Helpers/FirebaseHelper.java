package com.example.relife.Helpers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.relife.Model.TurnsClass;
import com.example.relife.Model.User;
import com.example.relife.Model.OrderQueue;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class FirebaseHelper {

    DatabaseReference db;
    FirebaseAuth auth;
    Context context;
    boolean saved;


    public FirebaseHelper(DatabaseReference db, Context context, FirebaseAuth auth) {
        this.db = db;
        this.context = context;
        this.auth = auth;
    }

    public DatabaseReference getDb() {
        return db;
    }

    public void setDb(DatabaseReference db) {
        this.db = db;
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public void setAuth(FirebaseAuth auth) {
        this.auth = auth;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }


    public boolean save(User user) {

        if (user == null) {
            saved = false;
        } else {

            try {
                if (auth.getCurrentUser() != null) {
                    db.child("Users").child(auth.getCurrentUser().getUid()).setValue(user);
                    saved = true;
                }
            } catch (DatabaseException e) {
                e.printStackTrace();
                ;
                saved = false;
            }

        }
        return saved;
    }

    public void getUser(final MyCallback callback) {

        if (auth.getCurrentUser() != null) {

            db.child("Users").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        callback.callback(user);
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    public void saveTurn(TurnsClass turnsClass, String hour) {

        if (turnsClass == null) {
            //
        } else {

            try {

                HashMap<String, TurnsClass> hashMap = new HashMap<>();
                hashMap.put(hour, turnsClass);

                db.child("Turn").child(becomeToString(turnsClass.getOrderQueue().getYear()))
                        .child(becomeToString(turnsClass.getOrderQueue().getMonth())).child(becomeToString(turnsClass.getOrderQueue().getWeek()))
                        .child(becomeToString(turnsClass.getOrderQueue().getDay())).setValue(hashMap);//TODO:

            } catch (DatabaseException e) {
                e.printStackTrace();
            }
        }


    }

    public void retrieveTurnsOfToday(final OrderQueue orderQueue, final DayTurnCallback callback, final ArrayList<TurnsClass> turnsClasses) {

        if (auth.getCurrentUser() != null) {

            final HashMap<String, TurnsClass> turnsClassHashMap = new HashMap<>();
            db.child("Turn").child(becomeToString(orderQueue.getYear())).child(becomeToString(orderQueue.getMonth())).
                    child(becomeToString(orderQueue.getWeek())).child(becomeToString(orderQueue.getDay()))
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            turnsClasses.clear();

                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                                if (dataSnapshot1.exists()) {

                                    turnsClasses.add(dataSnapshot1.getValue(TurnsClass.class));

                                }

                            }


                            callback.dayTurnCallback(turnsClasses);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {


                        }
                    });

        }

    }


    public void retrieveMyTurnsByWeek(final OrderQueue orderQueue, final MyTurnsCallback callback, final User user) {

        final ArrayList<TurnsClass> turnsClasses = new ArrayList<>();

        if (auth.getCurrentUser() != null) {
//
//            db.child("Turn").child(becomeToString(orderQueue.getYear())).child(becomeToString(orderQueue.getMonth())).child(becomeToString(orderQueue.getWeek()))
//                    .addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//
//                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//
//
//                                for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
//
//                                    if (dataSnapshot2.exists()) {
//
//                                        TurnsClass turnsClass = dataSnapshot2.getValue(TurnsClass.class);
//
//                                        if (turnsClass != null) {
//
//                                            if (turnsClass.getUser().getPhoneNumber().equals(user.getPhoneNumber())){
//
//                                                turnsClasses.add(dataSnapshot2.getValue(TurnsClass.class));
//
//                                            }
//                                        }
//                                    }
//                                }
//
//                            }
//
//
//                            callback.myTurnsWeekCallback(turnsClasses);
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });


            db.child("Turn").child(becomeToString(orderQueue.getYear())).child(becomeToString(orderQueue.getMonth())).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (int i = orderQueue.getWeek(); i < orderQueue.getWeek() + 2; i++) {


                        for (DataSnapshot dataSnapshot1 : dataSnapshot.child(becomeToString(i)).getChildren()) {


                            for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {

                                TurnsClass turnsClass = dataSnapshot2.getValue(TurnsClass.class);

                                if (turnsClass != null) {

                                    if (turnsClass.getUser().getPhoneNumber().equals(user.getPhoneNumber())) {

                                        turnsClasses.add(turnsClass);

                                    }
                                }

                            }

                        }
                    }

                    callback.myTurnsWeekCallback(turnsClasses);

                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }

    }


    public void deleteTurn(OrderQueue orderQueue, String hour) {

        if (auth.getCurrentUser() != null) {

            try {
                db.child("Turn").child(becomeToString(orderQueue.getYear())).child(becomeToString(orderQueue.getMonth()))
                        .child(becomeToString(orderQueue.getWeek())).child(becomeToString(orderQueue.getDay())).child(hour).removeValue();

            } catch (DatabaseException e) {

                e.printStackTrace();
            }

        }

    }

    public String becomeToString(int value) {

        return String.valueOf(value);

    }

}
