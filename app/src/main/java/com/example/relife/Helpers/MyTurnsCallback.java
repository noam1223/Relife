package com.example.relife.Helpers;

import com.example.relife.Model.OrderQueue;
import com.example.relife.Model.TurnsClass;
import com.example.relife.Model.User;

import java.util.ArrayList;

public interface MyTurnsCallback {
    void myTurnsWeekCallback(ArrayList<TurnsClass> turnsClasses);
}
