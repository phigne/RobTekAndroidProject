package Robtek.weatherapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CityWeatherList} interface
 * to handle interaction events.
 * Use the {@link CityWeatherList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CityWeatherList extends Fragment implements MyAdapter.OnCityClicked {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnListItemPressed mListener;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private MyAdapter mAdapter;

    private Parcelable mListState;


    ArrayList<String> cities = new ArrayList<String>(){
        {
            add("Copenhagen");
            add("Ribe");
            add("Aalborg");
            add("Odense");
            add("Aarhus");
            add("Skagen");
        }
    };

    public CityWeatherList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CityWeatherList.
     */
    // TODO: Rename and change types and number of parameters
    public static CityWeatherList newInstance(/*String param1, String param2*/) {
        CityWeatherList fragment = new CityWeatherList();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        setRetainInstance(true);


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (savedInstanceState != null) {
            mListState = savedInstanceState.getParcelable("liste");
        }

        return inflater.inflate(R.layout.fragment_city_weather_list, container, false);

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstance){
        super.onViewCreated(view, savedInstance);




        recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        //mAdapter = new MyAdapter(byNavne, list,getApplicationContext());

        mAdapter = new MyAdapter(cities,this.getContext(), this);
        recyclerView.setAdapter(mAdapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListItemPressed) {
            mListener = (OnListItemPressed) context;
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
    public void onCityBannerClicked(WeatherInfo CityWeatherData, int idx) {
        Log.w("ehekajsdkjasdhdasjhdas","aklhsdljahsd,ashd");
        mListener.onCityClicked(CityWeatherData, idx);
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
    public interface OnListItemPressed {
        // TODO: Update argument type and name
        void onCityClicked(WeatherInfo CityWeatherData, int idx);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Retrieve list state and list/item positions
        if(savedInstanceState != null)
            mListState = savedInstanceState.getParcelable("liste");
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        mListState = layoutManager.onSaveInstanceState();
        outState.putParcelable("liste", mListState);
        super.onSaveInstanceState(outState);


    }




}
