package Robtek.weatherapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link daybanner.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link daybanner#newInstance} factory method to
 * create an instance of this fragment.
 */


public class daybanner extends Fragment implements  WeatherListener, Serializable {

    private TextView TextCity;
    private WeatherInfo Weather;
    private ImageView bigImage;
    private TextView bigTmp;

    private ArrayList<ImageView>  todayImages = new ArrayList<>();
    private ArrayList<TextView>   todayImNames = new ArrayList<>();
    private ArrayList<TextView>   todayImTmps = new ArrayList<>();

    private ArrayList<ImageView>  WeekImages = new ArrayList<>();
    private ArrayList<TextView>  Weeknames = new ArrayList<>();
    private ArrayList<TextView>  Weektmps = new ArrayList<>();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_weather = "weather";

    // TODO: Rename and change types of parameters
    private String mCityName;
    private int mCityId;
    private String mCountry;

    private OnFragmentInteractionListener mListener;

    public daybanner() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters

    public static daybanner newInstance() {
        daybanner fragment = new daybanner();
        Bundle args = new Bundle();



        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_daybanner, container, false);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            //We use the saved instance in the onViewsCreated to ensure that we have inflated the views before assigning data to them
            savedInstanceState.getSerializable(ARG_weather);
        } else {
            if (todayImTmps != null) {
                //returning from backstack, data is fine, do nothing
            }
        }


    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(ARG_weather, Weather);
        //Save the fragment's state here

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
            Weeknames = new ArrayList<>();
            WeekImages = new ArrayList<>();
            Weektmps = new ArrayList<>();
            todayImages = new ArrayList<>();
            todayImNames = new ArrayList<>();
            todayImTmps = new ArrayList<>();

            TextCity = (TextView)this.getView().findViewById(R.id.upperCityName);
            TextCity.setText("city");

            Weeknames.add ((TextView)this.getView().findViewById(R.id.daytext1));
            Weeknames.add( (TextView)this.getView().findViewById(R.id.daytext2));
            Weeknames.add ( (TextView)this.getView().findViewById(R.id.daytext3));

            WeekImages.add( (ImageView) this.getView().findViewById(R.id.dayimage1));
            WeekImages.add( (ImageView) this.getView().findViewById(R.id.dayimage2));
            WeekImages.add( (ImageView) this.getView().findViewById(R.id.dayimage3));

            Weektmps.add ( (TextView)this.getView().findViewById(R.id.daytmp1));
            Weektmps.add ( (TextView)this.getView().findViewById(R.id.daytmp2));
            Weektmps.add ( (TextView)this.getView().findViewById(R.id.daytmp3));
            //Today inits
            todayImages.add((ImageView)this.getView().findViewById(R.id.todayIm1));
            todayImages.add((ImageView)this.getView().findViewById(R.id.todayIm));
            todayImages.add((ImageView)this.getView().findViewById(R.id.todayIm2));
            todayImages.add((ImageView)this.getView().findViewById(R.id.todayIm3));

            todayImNames.add((TextView)this.getView().findViewById(R.id.todayIm1name));
            todayImNames.add((TextView)this.getView().findViewById(R.id.todayIm1name2));
            todayImNames.add((TextView)this.getView().findViewById(R.id.todayIm1name3));
            todayImNames.add((TextView)this.getView().findViewById(R.id.todayIm1name4));

            todayImTmps.add((TextView)this.getView().findViewById(R.id.todayIm1tmp));
            todayImTmps.add((TextView)this.getView().findViewById(R.id.todayIm1tmp2));
            todayImTmps.add((TextView)this.getView().findViewById(R.id.todayIm1tmp3));
            todayImTmps.add((TextView)this.getView().findViewById(R.id.todayIm1tmp4));


            bigImage = (ImageView)this.getView().findViewById((R.id.ImageToday));
            bigTmp = (TextView)this.getView().findViewById((R.id.todayBigTmp));

            if(savedInstanceState != null && savedInstanceState.getSerializable(ARG_weather) != null){
                WeatherInfo tmp = (WeatherInfo) savedInstanceState.getSerializable(ARG_weather);
                updateCityData(tmp);
            }
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
    private void glideIconLoad(final String icon, final ImageView im){
        final Context tmp = this.getContext();

        Handler mainHandler = new Handler(tmp.getMainLooper());

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                Glide.with(tmp).load( WeatherHelper.getIconURL(icon))
                        .into(im);
            } // This is your code
        };
        mainHandler.post(myRunnable);
    }
    public void updateCityData(WeatherInfo Weather){
        if(Weather == null)
            return;
        this.Weather = Weather;
        TextCity.setText(Weather.getCity().getName());
        bigTmp.setText(WeatherHelper.getTempString(Weather.getList().get(0)));

        glideIconLoad(Weather.getList().get(0).getWeather().get(0).getIcon(),bigImage);

        ArrayList<List> todayWeather = new ArrayList<>();
        todayWeather.add(Weather.getList().get(1));
        todayWeather.add(Weather.getList().get(2));
        todayWeather.add(Weather.getList().get(3));
        todayWeather.add(Weather.getList().get(4));
        ArrayList<List> WeekWeahter = new ArrayList<>();
        WeekWeahter.add(Weather.getList().get(8)); // tommorw
        WeekWeahter.add(Weather.getList().get(16)); // +2days
        WeekWeahter.add(Weather.getList().get(24));

        updateImTxtTxtTodayList(todayWeather, todayImages,todayImTmps,todayImNames);
        updateImTxtTxtWeekList(WeekWeahter,WeekImages,Weektmps,Weeknames);
    }



    private void updateImTxtTxtTodayList(ArrayList<List> WeatherDataSegments,ArrayList<ImageView> IconImages, ArrayList<TextView>  tempViews, ArrayList<TextView>  nameViews){
        int i = 0;
        for (List weatherData: WeatherDataSegments) {
            if(nameViews.size() < i +1)
                break;

            nameViews.get(i).setText("kl " + WeatherHelper.getTodayHoursFormat(
                    Objects.requireNonNull(this.getContext()),
                    weatherData.getDt()
            ));
            i++;
        }
        updateImTmpList(WeatherDataSegments,IconImages, tempViews );

    }
    private void updateImTxtTxtWeekList(ArrayList<List> WeatherDataSegments,ArrayList<ImageView> IconImages, ArrayList<TextView>  tempViews, ArrayList<TextView>  nameViews){

        int i = 0;
        for (List weatherData: WeatherDataSegments) {
            if(nameViews.size() < i +1)
                break;

            nameViews.get(i).setText(WeatherHelper.getDayNameFromDT(
                    Objects.requireNonNull(this.getContext()),
                    weatherData.getDt()
            ));
            i++;
        }
        updateImTmpList(WeatherDataSegments,IconImages, tempViews );

    }
    private void updateImTmpList(ArrayList<List> WeatherDataSegments,ArrayList<ImageView> IconImages, ArrayList<TextView>  tempViews){

        int idx = 0;
        for (List WeatherData : WeatherDataSegments) {
            if(IconImages.size() < idx+1 || tempViews.size() < idx+1)
                break;
            ImageView imageV = IconImages.get(idx);
            TextView tmpV = tempViews.get(idx);

            String icon = WeatherData.getWeather().get(0).getIcon();
            tmpV.setText(WeatherHelper.getTempString(WeatherData));

            glideIconLoad(icon,imageV);

            idx++;
        }
    }

    @Override
    public void OnWeatherChange(WeatherInfo newWeather) {

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
}

