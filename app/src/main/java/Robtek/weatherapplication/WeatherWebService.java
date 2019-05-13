package Robtek.weatherapplication;

import android.content.res.Resources;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by slapocolypse on 3/12/18.
 */

public interface WeatherWebService {
    @GET("forecast")
    Call<WeatherInfo> weather_call_byid(@Query("cityid") int cityId, @Query("appid") String APIKEY);
    @GET("forecast")
    Call<WeatherInfo> weather_call_bycityCountry(@Query("q") String cityNameCSVcountry_code, @Query("appid") String APIKEY);
}
