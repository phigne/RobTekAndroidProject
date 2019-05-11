package Robtek.weatherapplication;

import android.content.res.Resources;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by slapocolypse on 3/12/18.
 */

public interface WeatherWebService {
    @GET("forecast?id={cityid}&appid={api_key}")
    Call<WeatherInfo> weather_call_byid(@Path("cityid") int cityId, @Path("api_key") String APIKEY);
    @GET("forecast?q={city_name},{country_code}&appid={api_key}")
    Call<WeatherInfo> weather_call_bycityCountry(@Path("city_name") String cityName, @Path("country_code") String country_code, @Path("api_key") String APIKEY);
}
