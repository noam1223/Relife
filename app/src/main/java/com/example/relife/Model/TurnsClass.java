package com.example.relife.Model;

import com.example.relife.Model.OrderQueue;
import com.example.relife.Model.User;

import java.io.Serializable;
import java.util.ArrayList;

public class TurnsClass implements Serializable {

    OrderQueue orderQueue;
    String hour;
    User user;
    String typeOfTurn;



    public TurnsClass() {

    }

    public TurnsClass(OrderQueue orderQueue, String hour ,User user, String typeOfTurn) {

        this.hour = hour;
        this.user = user;
        this.orderQueue = orderQueue;
        this.typeOfTurn = typeOfTurn;
    }


    public OrderQueue getOrderQueue() {
        return orderQueue;
    }

    public void setOrderQueue(OrderQueue orderQueue) {
        this.orderQueue = orderQueue;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getTypeOfTurn() {
        return typeOfTurn;
    }

    public void setTypeOfTurn(String typeOfTurn) {
        this.typeOfTurn = typeOfTurn;
    }


    @Override
    public String toString() {
        return "TurnsClass{" +
                "typeOfTurn='" + typeOfTurn + '\'' +
                ", orderQueue=" + orderQueue.toString() +
                ", hour='" + hour + '\'' +
                ", user=" + user.toString() +
                '}';
    }
}
