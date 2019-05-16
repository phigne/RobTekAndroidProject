package Robtek.weatherapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.IOException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import android.support.v4.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link daybanner.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link daybanner#newInstance} factory method to
 * create an instance of this fragment.
 */
public class daybanner extends Fragment implements  WeatherListener{

    private Thread WeatherThread;
    private TextView TextCity;
    private TextView TextDayOne;
    private TextView TextDayTwo;
    private TextView TextDayThree;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CityName = "CityName";
    private static final String ARG_CityId = "CityId";
    private static final String ARG_Country = "Country";

    // TODO: Rename and change types of parameters
    private String mCityName;
    private int mCityId;
    private String mCountry;

    private OnFragmentInteractionListener mListener;

    public daybanner() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param cityname Parameter 1.
     * @param country Parameter 2.
     * @return A new instance of fragment daybanner.
     */
    // TODO: Rename and change types and number of parameters
    public static daybanner newInstance(String cityname, String country) {

        return newInstance(cityname, country, -1);
    }

    public static daybanner newInstance(String cityname, String country, int cityId) {
        daybanner fragment = new daybanner();
        Bundle args = new Bundle();
        args.putString(ARG_CityName, cityname);
        args.putInt(ARG_CityId, cityId);
        args.putString(ARG_Country, country);


        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCityName = getArguments().getString(ARG_CityName);
            mCityId = getArguments().getInt(ARG_CityId);
            mCountry = getArguments().getString(ARG_Country);
        }

        WeatherInfoRetriever Wretriver;
        if(mCityId == -1 && !mCityName.isEmpty() && !mCountry.isEmpty()) //Country city mode
            Wretriver =  new WeatherInfoRetriever(mCityName,mCountry);
        else if(mCityId >= 0 )
            Wretriver =  new WeatherInfoRetriever(mCityId);
        else
            Wretriver = new WeatherInfoRetriever(313);

        Wretriver.listenOnWeatherUpdate(this);
        WeatherThread = new Thread(Wretriver);

        WeatherThread.start();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_daybanner, container, false);

        return v;
    }
    @Override
    public void onActivityCreated (Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        TextCity = (TextView)this.getView().findViewById(R.id.cityName);
        TextCity.setText("city");
        TextDayOne = (TextView)this.getView().findViewById(R.id.daytext1);
        TextDayTwo = (TextView)this.getView().findViewById(R.id.daytext2);
        TextDayThree = (TextView)this.getView().findViewById(R.id.daytext3);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void OnWeatherChange(WeatherInfo newWeather) {
        Log.w("HEHRHEKARJBARKJ",newWeather.getCity().getName());
        for (List Listobj: newWeather.getCity().getList() ) {
            for (Weather WeathInterval : Listobj.getWeather()){
                WeathInterval.getIcon();
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void updateWeatherData(){
        WeatherThread.start();
    }
}

