package Robtek.weatherapplication;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements daybanner.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w("HEJ2222","HEN");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AddFragment("hej", "hej");
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void AddFragment(String city, String country){
        // First get FragmentManager object.
        FragmentManager fragmentManager = this.getSupportFragmentManager();

        // Begin Fragment transaction.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment NewFrag = daybanner.newInstance(city,country);
        fragmentTransaction.add(R.id.rec, NewFrag);

        // Commit the Fragment replace action.
        fragmentTransaction.commit();
    }
}
