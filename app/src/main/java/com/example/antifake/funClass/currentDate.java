package com.example.antifake.funClass;

import java.util.Calendar;

public class currentDate {
    public String getcurrentDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String cDate=year+"-"+month+"-"+day+" "+hour+":"+minute;
        return cDate;
    }
}
