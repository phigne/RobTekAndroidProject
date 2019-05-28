package Robtek.weatherapplication;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CityWeatherList.OnListItemPressed, daybanner.OnFragmentInteractionListener {
    private CityWeatherList Listfragment; // implements daybanner.OnFragmentInteractionListener {
    private daybanner DayFragment;
    private FragmentManager fragMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragMan = this.getSupportFragmentManager();
        DayFragment = DayFragment.newInstance("hej", "hej");
            Log.i("Jos", "onCreate: Hallojjjj?");
        FragmentTransaction fragmentTransaction = fragMan.beginTransaction();

        // Create FragmentOne instance.

        // Add fragment one with tag name.
        fragmentTransaction.add(R.id.FragmentContainer, CityWeatherList.newInstance(), "Fragment One");
        fragmentTransaction.commit();}



    @Override
    public void onCityClicked(WeatherInfo CityWeatherData, int idx) {
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
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
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's instance
       // getSupportFragmentManager().putFragment(outState, "myFragmentName",Listfragment );
        getSupportFragmentManager().putFragment(outState,"fragmentInstanceSaved",getSupportFragmentManager().findFragmentById(R.id.FragmentContainer));
    }



 /*   @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void AddFragment(String city, String country){
        // First get FragmentManager object.
        FragmentManager fragmentManager = this.getSupportFragmentManager();

        // Begin Fragment transaction.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment NewFrag = daybanner.newInstance(city,country);
        fragmentTransaction.replace(R.id.fragment2, NewFrag);

        // Commit the Fragment replace action.
        fragmentTransaction.commit();
    }*/
}
