package Robtek.weatherapplication;

import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public  class WeatherHelper {
    public static String getDayNameFromDT(Context context, int Dt){
        Locale locale = context.getResources().getConfiguration().locale;
        Date Time = new Date((long)Dt*1000);
        SimpleDateFormat dataform = new SimpleDateFormat("EEEE", locale); // the day of the week spelled out completely
        return dataform.format(Time);

    }
    public static String getDayNameFromDTShort(Context context, int Dt){
        Locale locale = context.getResources().getConfiguration().locale;
        Date Time = new Date((long)Dt*1000);
        SimpleDateFormat dataform = new SimpleDateFormat("EEE", locale); // the day of the week spelled out completely
        return dataform.format(Time);
    }

    public static String getTodayHoursFormat(Context context, int Dt){
        //long diff = Calendar.getInstance().getTimeInMillis() - Dt;
        //long hours = diff/1000 /60 / 60;
        //return Long.toString(hours);
        Locale locale = context.getResources().getConfiguration().locale;
        Date Time = new Date((long)Dt*1000);
        SimpleDateFormat dataform = new SimpleDateFormat("HH:mm", locale); // Hours in this format: 20:00
        return dataform.format(Time);

    }

    public static String getIconURL(String icon){
        return "http://openweathermap.org/img/w/" + icon + ".png";

    }

    public static String getTempString(List WeatherData){

        return Long.toString(Math.round(WeatherData.getMain().getTemp() - 273.1500)) + "°";
    }
}
