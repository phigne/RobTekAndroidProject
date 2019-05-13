package Robtek.weatherapplication;

import android.content.res.Resources;
import android.util.Log;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
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
    private String API = "26ced23cd65cdb146a6a6e106e6c05d2";
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
            call = weatherService.weather_call_byid(cityId, API);
        else
            call = weatherService.weather_call_bycityCountry(cityName + ","+country, API);
        try {
            call = weatherService.weather_call_bycityCountry("Copenhagen,DK", API); // cph
            Response<WeatherInfo> result = call.execute();
            Log.w("The request: ",call.request().toString());
            Log.w("Response errorcode: ", Integer.toString(result.code()));
            setWeather(result.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

interface WeatherListener {
    void OnWeatherChange(WeatherInfo newWeather);
}