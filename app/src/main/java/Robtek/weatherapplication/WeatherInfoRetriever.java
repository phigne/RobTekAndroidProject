package Robtek.weatherapplication;

import android.content.res.Resources;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherInfoRetriever implements Runnable{
    private String country;
    private int cityId;
    private String cityName;
    private WeatherInfo weather;
    private WeatherWebService weatherService;
    private Retrofit retrofit;
    private boolean isIdMode;
    public WeatherInfo getWeather() {
        return weather;
    }

    private void setWeather(WeatherInfo weather) {
        this.weather = weather;
        weatherListener.OnWeatherChange(weather);
    }

    private WeatherListener weatherListener;

    public void listenOnWeatherUpdate(WeatherListener listener){
        weatherListener = listener;
    }

    public WeatherInfoRetriever(String country, String cityName){
        this.country = country;
        this.cityName = cityName;
        initialize();
        this.isIdMode = false;
    }
    public WeatherInfoRetriever(int cityId){

        this.cityId = cityId;
        isIdMode = true;
        initialize();
    }

    private void initialize() {
        //initialize retrofit and weather retrivers
        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherService = retrofit.create(WeatherWebService.class);
    }

    @Override
    public void run() {
        Call<WeatherInfo> call;
        if(isIdMode)
            call = weatherService.weather_call_byid(cityId, Resources.getSystem().getString(R.string.API));
        else
            call = weatherService.weather_call_bycityCountry(cityName,country, Resources.getSystem().getString(R.string.API));
        try {
            setWeather(call.execute().body());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

interface WeatherListener {
    void OnWeatherChange(WeatherInfo newWeather);
}

/**
 * Created by slapocolypse on 3/12/18.
 */

class Value {
    int id;
    String joke;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }
}



class Info {
    String type;
    Value value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }}