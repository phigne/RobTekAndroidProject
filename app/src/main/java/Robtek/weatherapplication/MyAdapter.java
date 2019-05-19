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

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements cityDataRetrieved {

    private ArrayList<CityDataHandler> CityData;

//    private ArrayList<String> byNavne;
//    private ArrayList<String> iconer;
    public String save1;
    private String save2;

    public String getSave1()
    {
        return save1;
    }
    public String getSave2()
    {
        return save2;
    }

    private Context context;


    //Test constructor
    public MyAdapter(ArrayList<String>  byNavne,  Context context)
    {
        CityData = new ArrayList<>();
        for (String by:byNavne) {
            CityData.add(new CityDataHandler(by, "dk", context));
        }
        this.context = context;
    }




    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView1;
        public ImageView imageView;
        public ImageView imageView2;
        public ImageView imageView3;
        RecyclerView recycler_view;
        public ConstraintLayout cl;




        public MyViewHolder(View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.my_text_view);
            imageView = itemView.findViewById(R.id.my_image_view);
            imageView2 = itemView.findViewById(R.id.my_image_view2);
            imageView3 = itemView.findViewById(R.id.my_image_view3);
            cl = itemView.findViewById(R.id.ConstraintLayout);
            recycler_view = itemView.findViewById(R.id.recycler_view);

        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        ConstraintLayout view = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
/*
        Glide.with(view)
                .load("https://picsum.photos/200/200/?random")
                .apply(requestOptions)
                .into(holder.imageView);
*/

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i){
        CityDataHandler CurrentCD =         CityData.get(i);
        CurrentCD.todayImage = ((MyViewHolder) myViewHolder).imageView;
        CurrentCD.todayPlusOneImage = ((MyViewHolder) myViewHolder).imageView2;
        CurrentCD.todayPlusTwoImage = ((MyViewHolder) myViewHolder).imageView3;
        CurrentCD.set_textView1( myViewHolder.textView1);

        CityData.get(i).getWeather(this);
    }

    @Override
    public void OncityDataRetrieved(CityDataHandler CityData) {

    }



    @Override
    public int getItemCount() {
        return CityData.size();
    }

}