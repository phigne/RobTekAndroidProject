package Robtek.weatherapplication;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity{ // implements daybanner.OnFragmentInteractionListener {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;





    ArrayList<String> byNavne = new ArrayList<String>();



    Random random = new Random();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        for(int i = 0; i < 1000; i++)
        {
            int r = random.nextInt(1000);
            String result1 = "" + r;
            byNavne.add(result1);

        }

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<String> list = new ArrayList<String>();
        loadImages(list);

        mAdapter = new MyAdapter(byNavne, list,getApplicationContext());
        recyclerView.setAdapter(mAdapter);
    }

    public void loadImages(ArrayList<String> list) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);



        for(int i = 0; i < 1000; i++)
        {

            list.add(i,"https://picsum.photos/200/200?image="+i);
        }
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
