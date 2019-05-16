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
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Random;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<String> byNavne;
    private ArrayList<String> iconer;
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



    public MyAdapter(ArrayList<String>  byNavne, ArrayList<String>  iconer, Context context)
    {
        this.byNavne= byNavne;
        this.iconer = iconer;
        this.context = context;
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView1;
        public ImageView imageView;
        RecyclerView recycler_view;
        public ConstraintLayout cl;




        public MyViewHolder(View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.my_text_view);
            imageView = itemView.findViewById(R.id.my_image_view);
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

        Glide.with(view)
                .load("https://picsum.photos/200/200/?random")
                .apply(requestOptions)
                .into(holder.imageView);


        return holder;
    }

    public void fillUp()
    {
        for(int i = 0; i < 1000; i++)
        {
            Random random = new Random();
            int r = random.nextInt(1000);
            String result1 = "" + r;
            byNavne.add(result1);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i){

        myViewHolder.textView1.setText(byNavne.get(i));
        ImageView imageView = ((MyViewHolder) myViewHolder).imageView;
        String currentUrl = iconer.get(i);

        Glide.with(context).load(currentUrl)
                .into(myViewHolder.imageView);

        fillUp();
    }





    @Override
    public int getItemCount() {
        return byNavne.size();
    }





}
