package Robtek.weatherapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements cityDataRetrieved {

    private ArrayList<CityDataHandler> CityData;

//    private ArrayList<String> byNavne;
//    private ArrayList<String> iconer;
    public String save1;
    private String save2;
    private OnCityClicked ParentCallBack;
    public String getSave1()
    {
        return save1;
    }
    public String getSave2()
    {
        return save2;
    }
    private ArrayList<WeatherInfo> CurrentWeatherData = new ArrayList<>();

    private Context context;


    //Test constructor
    public MyAdapter(ArrayList<String>  byNavne,  Context context, OnCityClicked ParentCallBack) {
        CityData = new ArrayList<>();
        for (String by : byNavne) {
            CityData.add(new CityDataHandler(by, "dk", context));
        }
        this.context = context;
        this.ParentCallBack = ParentCallBack;
    }
    //Test constructor
    public MyAdapter( ArrayList<String>  byNavne, Context context, OnCityClicked ParentCallBack, ArrayList<WeatherInfo>  stateWeathers) {
        CityData = new ArrayList<>();
        for (String by : byNavne) {
            CityData.add(new CityDataHandler(by, "dk", context));
        }
        int i = 0;
        //stupid loop to assign the weather data to the correct city.
        for (WeatherInfo weather : stateWeathers){
            if(CityData.get(i) != null &&
                CityData.get(i).cityName == weather.getCity().getName() &&
                CityData.get(i).country == weather.getCity().getCountry())
            {
                CityData.get(i).Weather = weather;
            }

                i++;
        }

        this.context = context;
        this.ParentCallBack = ParentCallBack;
    }

    public ArrayList<WeatherInfo> getCurrentWeatherData() {
        return CurrentWeatherData;
    }

    private void setCurrentWeatherData(ArrayList<WeatherInfo> currentWeatherData) {
        CurrentWeatherData = currentWeatherData;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView1;
        public ImageView imageView;
        public ImageView imageView2;
        public ImageView imageView3;
        private TextView TextDayOne;
        private TextView TextDayTwo;
        private TextView TextDayThree;
        private TextView TextTmp3;
        private TextView TextTmp1;
        private TextView TextTmp2;


        RecyclerView recycler_view;
        public ConstraintLayout cl;



        public MyViewHolder(View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.my_text_view);
            imageView = itemView.findViewById(R.id.my_image_view);
            imageView2 = itemView.findViewById(R.id.my_image_view2);
            imageView3 = itemView.findViewById(R.id.my_image_view3);
            TextDayOne =  itemView.findViewById(R.id.dayname1);
            TextDayTwo = itemView.findViewById(R.id.dayname2);
            TextDayThree = itemView.findViewById(R.id.dayname3);
            TextTmp1 = itemView.findViewById(R.id.daytmp1);
            TextTmp2 = itemView.findViewById(R.id.daytmp2);
            TextTmp3 = itemView.findViewById(R.id.daytmp3);
            cl = itemView.findViewById(R.id.ConstraintLayout);
            recycler_view = itemView.findViewById(R.id.recycler_view);

        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        ConstraintLayout view = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list, parent, false);

        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i){
        CityDataHandler CurrentCD = CityData.get(i);
        if(CurrentCD.getDaysCount() == 3) //When we scroll we should not reinitialize the data handler
            return;
        CurrentCD.addDay(myViewHolder.imageView, myViewHolder.TextDayOne,myViewHolder.TextTmp1);
        CurrentCD.addDay(myViewHolder.imageView2, myViewHolder.TextDayTwo,myViewHolder.TextTmp2);
        CurrentCD.addDay(myViewHolder.imageView3, myViewHolder.TextDayThree,myViewHolder.TextTmp3);
        CurrentCD.set_textView1( myViewHolder.textView1);

        final int idx = i;
        myViewHolder.cl.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
        if(ParentCallBack != null)
                    ParentCallBack.onCityBannerClicked(CityData.get(idx).Weather, idx);
            }
        });
        if(CurrentCD.Weather != null){
            CurrentCD.setCityDataUpdateCallback(this);
            CurrentCD.updateWeather(CurrentCD.Weather);
        }
        else
            CurrentCD.getWeather(this);

    }

    @Override
    public void OncityDataRetrieved(CityDataHandler CityData) {
        //Will be called whenever a new Weather info has been retrived from the Web
        if( !this.getCurrentWeatherData().contains(CityData.Weather))
            this.getCurrentWeatherData().add(CityData.Weather);
    }

    public interface OnCityClicked{
        void onCityBannerClicked(WeatherInfo CityWeatherData, int idx);
    }

    @Override
    public int getItemCount() {
        return CityData.size();
    }

}
