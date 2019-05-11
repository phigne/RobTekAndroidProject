package Robtek.weatherapplication;
import android.renderscript.Sampler;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class WeatherInfoRetriever implements Runnable{
    private String country;
    private int cityId;
    private String cityName;
    public WeatherInfoRetriever(String country, String cityName){
        this.country = country;
        this.cityName = cityName;
    }
    public WeatherInfoRetriever(int cityId){
        this.cityId = cityId;
    }
    @Override
    public void run() {

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
    }
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
/**
 * Created by slapocolypse on 3/12/18.
 */

interface JokeWebService {
    @GET("weather/info")
    Call<Info> weather_call();
}


