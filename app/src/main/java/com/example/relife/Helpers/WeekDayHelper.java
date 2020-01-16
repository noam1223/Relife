package com.example.relife.Helpers;

import com.example.relife.Model.OrderQueue;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class WeekDayHelper {


    String[] weekdays = {"ראשון", "שני", "שלישי", "רביעי", "חמישי", "שישי", "שבת"};
    String[] hours = {"8:00", "9:00", "10:00", "11:00", "12:00",
            "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"};

    public int size(){
        return weekdays.length;
    }


    public String dayByPosition(int position){
        return weekdays[position];
    }

    public String formatDateOfTurn(OrderQueue orderQueue){


        return "בתאריך " + orderQueue.getDayInMonth() + "/" + orderQueue.getMonth() + 1;
    }

    public String hourByPosition(int position){

        return hours[position];
    }

    public String[] getWeekdays() {
        return weekdays;
    }

    public String[] getHours() {
        return hours;
    }

    public int getWeekdaysSize() {
        return weekdays.length;
    }


    public ArrayList<String> hoursToArrayList(){

        return new ArrayList<String>(Arrays.asList(hours));
    }

}
