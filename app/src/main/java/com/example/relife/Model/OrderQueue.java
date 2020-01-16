package com.example.relife.Model;

import com.example.relife.Model.User;

import java.io.Serializable;
import java.util.Calendar;

public class OrderQueue implements Serializable {


    int day, week, month, year, dayInMonth;

    public OrderQueue() {
    }

    public OrderQueue(int day, int week, int month, int year) {
        this.day = day;
        this.week = week;
        this.month = month;
        this.year = year;

    }

    public int getDayInMonth() {
        return dayInMonth;
    }

    public void setDayInMonth(int dayInMonth) {
        this.dayInMonth = dayInMonth;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public int getWeek() {
        return week;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "OrderQueue{" +
                "day=" + day +
                ", week=" + week +
                ", month=" + month +
                ", year=" + year +
                '}';
    }
}




