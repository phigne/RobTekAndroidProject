package Robtek.weatherapplication;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements CityWeatherList.OnListItemPressed, daybanner.OnFragmentInteractionListener, cityDataRetrieved {
    private CityWeatherList Listfragment; // implements daybanner.OnFragmentInteractionListener {
    private daybanner DayFragment;
    private FragmentManager fragMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragMan = this.getSupportFragmentManager();
    }

    @Override
    public void onCityClicked(WeatherInfo CityWeatherData, int idx) {
        if(getResources().getDisplayMetrics().densityDpi <= getResources().getDisplayMetrics().DENSITY_MEDIUM){
            if(DayFragment == null)
                DayFragment = (daybanner) fragMan.findFragmentById(R.id.fragmentDayB);
            DayFragment.updateCityData(CityWeatherData);
        }else{
            FragmentTransaction fragTrans = fragMan.beginTransaction();
            if(DayFragment == null)
                DayFragment = daybanner.newInstance();

            fragTrans.hide(fragMan.findFragmentById(R.id.fragmentList));
            fragTrans.add(R.id.FragmentContainer, DayFragment);
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
    boolean IsDayBannerInitialized = false;
    @Override
    public void OncityDataRetrieved(final CityDataHandler CityData) {
            if (!IsDayBannerInitialized && findViewById(R.id.FragmentContainerRigth) != null ) {
                if (DayFragment == null)
                    DayFragment = (daybanner) fragMan.findFragmentById(R.id.fragmentDayB);

                Handler mainHandler = new Handler(this.getMainLooper());
                //Needs to run on main thread since the views are touched
                Runnable myRunnable = new Runnable() {
                    @Override
                    public void run() {
                        DayFragment.updateCityData(CityData.Weather);
                    } // This is your code
                };
                mainHandler.post(myRunnable);
                IsDayBannerInitialized = true;
            }
    }
}
