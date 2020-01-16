package com.example.relife.Helpers;

import com.example.relife.Model.OrderQueue;
import com.example.relife.Model.TurnsClass;

import java.util.ArrayList;

public interface DayTurnCallback {
    void dayTurnCallback(ArrayList<TurnsClass> turnsClasses);
}
