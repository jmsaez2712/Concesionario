package dev.jmsaez.concesionariofragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> { //Adapter y ViewHolder necesarios para mostrar los datos en el recyclerview
    private Context context;

    public RecyclerAdapter(Context context){
        this.context = context;
    }

    private List<Car> carList;
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item, parent, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Car car = carList.get(position);
        holder.car = car;
        String carImgs = car.getImgs();
        try {
            JSONObject json = new JSONObject(carImgs);
            String first_img = json.getString("0");
            Log.v("CAR_IMG",first_img);
            Glide.with(context).load(first_img).into(holder.ivCar);

        } catch (JSONException e) {
            e.printStackTrace();
        }
            holder.tvTitle.setText(car.getTitle());
            holder.tvContent.setText(car.getDesc());
    }

    @Override
    public int getItemCount() {
        if(carList == null) {
            return 0;
        }

        return carList.size();
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        Car car;
        ImageView ivCar;
        TextView tvTitle;
        TextView tvContent;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCar = itemView.findViewById(R.id.ivCar);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvContent = itemView.findViewById(R.id.tvContent);

            itemView.setOnClickListener((view) ->{
                Bundle bundle = new Bundle();
                bundle.putParcelable("car", car);
                Navigation.findNavController(itemView).navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
            });
        }
    }
}

