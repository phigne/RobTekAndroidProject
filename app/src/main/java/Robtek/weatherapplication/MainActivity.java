package Robtek.weatherapplication;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements CityWeatherList.OnListItemPressed, daybanner.OnFragmentInteractionListener {
    private CityWeatherList Listfragment; // implements daybanner.OnFragmentInteractionListener {
    private daybanner DayFragment;
    private FragmentManager fragMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragMan = this.getSupportFragmentManager();
        if(savedInstanceState == null){ // Jos 29/5 11:53
            Log.i("Jos", "onCreate: savedInstanceState null");

            DayFragment = DayFragment.newInstance("hej", "hej");

            FragmentTransaction fragmentTransaction = fragMan.beginTransaction();

            // Create FragmentOne instance.
            // Add fragment one with tag name.
            fragmentTransaction.add(R.id.FragmentContainer, CityWeatherList.newInstance("22"), "Fragment One");


            if(findViewById(R.id.FragmentContainerRigth) != null)
                fragmentTransaction.add(R.id.FragmentContainerRigth, DayFragment, "Fragment Two");
                fragmentTransaction.commit();
        }
        else{
            Log.i("Jos", "onCreate: savedInstanceState not null");
            Listfragment = (CityWeatherList)getSupportFragmentManager().findFragmentByTag("weather_infos");// Jos 29/5 11:53

            if(DayFragment != null){
                Log.i("Jos", "onCreate: dayfrag not null");
                DayFragment = (daybanner)getSupportFragmentManager().findFragmentByTag("ARG_weather");

            }
            else{
                DayFragment = DayFragment.newInstance("hej", "hej");
                Log.i("Jos", "onCreate: dayfrag null");
            }

        }
    }

    @Override
    public void onCityClicked(WeatherInfo CityWeatherData, int idx) {
        int conf =  getResources().getConfiguration().orientation;
        if(getResources().getDisplayMetrics().densityDpi <= getResources().getDisplayMetrics().DENSITY_MEDIUM){
            DayFragment.updateCityData(CityWeatherData);
        }else{
            FragmentTransaction fragTrans = fragMan.beginTransaction();
            if(DayFragment == null)
                DayFragment = daybanner.newInstance("hej", "he");

            fragTrans.replace(R.id.FragmentContainer, DayFragment);
            fragTrans.addToBackStack(null);
            fragTrans.commit();
            getSupportFragmentManager().executePendingTransactions();
            DayFragment.updateCityData(CityWeatherData);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
