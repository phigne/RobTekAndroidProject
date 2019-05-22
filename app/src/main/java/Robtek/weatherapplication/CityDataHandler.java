package Robtek.weatherapplication;

import android.content.Context;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CityDataHandler implements WeatherListener{
    public String cityName;
    public String country;
    public WeatherInfo Weather;
    private WeatherInfoRetriever Wretriever;
    private Thread WeatherThread;

    private ArrayList<ImageView> Images = new ArrayList<>();
    private ArrayList<TextView> DayNames= new ArrayList<>();
    private ArrayList<TextView> Daytmps= new ArrayList<>();

    private TextView _textView1;
    private Context context;

    public TextView get_textView1() {
        return _textView1;
    }

    public void set_textView1(TextView _textView1) {
        this._textView1 = _textView1;
        _textView1.setText(this.cityName);
    }



    private cityDataRetrieved weatherListener;
    public void listenOnWeatherUpdate(cityDataRetrieved listener){
        weatherListener = listener;
    }

    public CityDataHandler(String cityName,String country, Context context){
        this.cityName = cityName;
        this.country = country;
        this.context = context;
        Wretriever = new  WeatherInfoRetriever(country, cityName);
        Wretriever.listenOnWeatherUpdate(this);


    }

    public void getWeather(cityDataRetrieved CallbackHandler){
        WeatherThread = new Thread(Wretriever);
        WeatherThread.start();
        weatherListener = CallbackHandler;
    }

    public void setDay(final Context context, final List WeatherData, final int idx ){
        if(Images.size() < idx+1 || Daytmps.size() < idx+1 || DayNames.size() < idx+1 )
            return;

        final ImageView imageV = Images.get(idx);
        final TextView nameV = DayNames.get(idx);
        final TextView tmpV = Daytmps.get(idx);
        final String dayTxt = WeatherHelper.getDayNameFromDT(context, WeatherData.getDt());

        //A trick to run the glide on the main thread, since this function is called through a
        //eventhandler on a background thread
        Handler mainHandler = new Handler(context.getMainLooper());

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                Double Kelvin = 273.1500;
                String icon = WeatherData.getWeather().get(0).getIcon();
                tmpV.setText(   (Long.toString(Math.round(WeatherData.getMain().getTemp() - Kelvin))  + "°"  ));
                nameV.setText(dayTxt);


                Glide.with(context).load( WeatherHelper.getIconURL(icon))
                        .into(imageV);
            } // This is your code
        };
        mainHandler.post(myRunnable);

    }

    private void setDays(){
        int i = 0;
        if(Weather != null){
            for (ImageView im : Images) {
                List daydata = Weather.getList().get(i*8); // way too simple way to get tommorws weather - 3hours*8 = 24 hours
                setDay(this.context,daydata,i);
                i++;
            }
        }
    }


    public void addDay(ImageView im, TextView daytxt, TextView tmpTxt){
        Images.add((im)); DayNames.add(daytxt); Daytmps.add(tmpTxt);
    }

    @Override
    public void OnWeatherChange(WeatherInfo newWeather) {
        Weather  = newWeather;
        if(weatherListener != null)
            weatherListener.OncityDataRetrieved(this);
        setDays();
    }
}



interface cityDataRetrieved {
    void OncityDataRetrieved(CityDataHandler CityData);
}
