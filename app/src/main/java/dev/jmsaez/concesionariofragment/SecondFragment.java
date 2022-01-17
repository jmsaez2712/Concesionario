package dev.jmsaez.concesionariofragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;

import dev.jmsaez.concesionariofragment.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    //Creamos las variables necesarias para poder mostrar los datos en pantalla
    private ImageView ivPhotos;
    private TextView tvTitle, tvContent, tvPrice, tvTags, tvNumPhotos;
    private Car car;
    private JSONObject jsonImg;
    private JSONObject jsonTags;
    int index = 0;
    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();

        initComps(view);
        try{//Intentamos obtener el parcelable del bundle dentro del try-catch y obtenemos los datos que necesitamos del objeto y los usamos
            car = bundle.getParcelable("car");
            jsonImg = new JSONObject(car.getImgs());

            tvTitle.setText(car.getTitle());
            tvContent.setText(car.getDesc());
            tvNumPhotos.setText(index+1 +"/"+ jsonImg.length());

            writeTags();
            writePrice();


            //Instanciamos y definimos una lambda para usar el listener de los eventos de los botones para cargar las imagenes siguientes
            //region imagesbuttons
            Button btSiguiente = view.findViewById(R.id.btSiguiente);
            btSiguiente.setOnClickListener((v) ->{
                index+=1;
                try {
                    String imgs2 = jsonImg.getString(String.valueOf(index));
                    Glide.with(this).load(imgs2).into(ivPhotos);
                    tvNumPhotos.setText(index+1 +"/"+ jsonImg.length());
                } catch(Exception e){}

            });

            Button btAtras = view.findViewById(R.id.btAtras);
            btAtras.setOnClickListener((v) ->{
                index-=1;
                try {
                    String imgs2 = jsonImg.getString(String.valueOf(index));
                    Glide.with(this).load(imgs2).into(ivPhotos);
                    tvNumPhotos.setText(index+1 +"/"+ jsonImg.length());
                } catch(Exception e){}

            });
            //endregion

        } catch (Exception e){}

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //Método donde obtenemos, formateamos y escribimos en el textview los tags del objeto
    public void writeTags(){
        try {
            jsonTags = new JSONObject(car.getTags());
            for (int i = 0; i < jsonTags.length(); i++) {
                switch (i) {
                    case 0:
                        tvTags.append("Color: " + jsonTags.getString(String.valueOf(i)) + "\n");
                        break;
                    case 1:
                        tvTags.append("Doors: " + jsonTags.getString(String.valueOf(i)) + "\n");
                        break;
                    case 2:
                        tvTags.append("Fuel: " + jsonTags.getString(String.valueOf(i)) + "\n");
                        break;
                    case 3:
                        tvTags.append("Horsepower: " + jsonTags.getString(String.valueOf(i)) + "\n");
                        break;
                    case 4:
                        tvTags.append("Mileage: " + jsonTags.getString(String.valueOf(i)) + "\n");
                        break;
                    case 5:
                        tvTags.append("Transmission: " + jsonTags.getString(String.valueOf(i)) + "\n");
                        break;
                    case 6:
                        tvTags.append("Garranty: " + jsonTags.getString(String.valueOf(i)) + "\n");
                        break;
                    case 7:
                        tvTags.append("Year: " + jsonTags.getString(String.valueOf(i)) + "\n");
                        break;
                }

            }
        } catch(Exception e){}
    }

    //Método para inicializar los componentes
    public void initComps(View view){
        tvTitle = view.findViewById(R.id.tvTitleDetails);
        tvContent = view.findViewById(R.id.tvContentDetails);
        tvPrice = view.findViewById(R.id.tvPrice);
        tvTags = view.findViewById(R.id.tvTags);
        tvNumPhotos = view.findViewById(R.id.tvNumPhotos);
        ivPhotos = view.findViewById(R.id.ivDetails);
    }

    //Método para formatear y escribir el precio del objeto obtenido
    public void writePrice(){
        Locale locale = new Locale("es","ES");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        String price = format.format(car.getPrice());
        tvPrice.setText(price);
    }
}