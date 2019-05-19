package Robtek.weatherapplication;

import android.content.Context;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class CityDataHandler implements WeatherListener{
    public String cityName;
    public String country;
    public WeatherInfo Weather;
    private WeatherInfoRetriever Wretriever;
    private Thread WeatherThread;
    public ImageView todayImage;
    public ImageView todayPlusOneImage;
    public ImageView todayPlusTwoImage;
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

    public void setImageIcon(final Context context, final String  iconId, final ImageView imageview){

        //A trick to run the glide on the main thread, since this function is called through a
        //eventhandler on a background thread
        Handler mainHandler = new Handler(context.getMainLooper());

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                Glide.with(context).load( "http://openweathermap.org/img/w/" + iconId + ".png")
                        .into(imageview);
            } // This is your code
        };
        mainHandler.post(myRunnable);

    }

    public void setTodayIcon(){
        if(Weather != null && todayImage != null){
            String icon = Weather.getList().get(0).getWeather().get(0).getIcon();
            setImageIcon(this.context,icon,todayImage);
        }
    }
    public void setTodayPlusOneIcon(){
        if(Weather != null && todayPlusOneImage != null){
            //Date date = new Date();
            //Too simple way to find tommorows weather
            String icon = Weather.getList().get(8).getWeather().get(0).getIcon();
            setImageIcon(this.context,icon,todayPlusOneImage);
        }
    }
    public void setTodayPlusTwoIcon(){
        if(Weather != null && todayPlusTwoImage != null){
            //Date date = new Date();
            //Too simple way to find tommorows weather
            String icon = Weather.getList().get(16).getWeather().get(0).getIcon();
            setImageIcon(this.context,icon,todayPlusTwoImage);
        }
    }


    @Override
    public void OnWeatherChange(WeatherInfo newWeather) {
        Weather  = newWeather;
        if(weatherListener != null)
            weatherListener.OncityDataRetrieved(this);
        setTodayIcon();
        setTodayPlusOneIcon();
        setTodayPlusTwoIcon();
    }
}



interface cityDataRetrieved {
    void OncityDataRetrieved(CityDataHandler CityData);
}
