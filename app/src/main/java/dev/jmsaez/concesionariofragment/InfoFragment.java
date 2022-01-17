package dev.jmsaez.concesionariofragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class InfoFragment extends Fragment { //Fragmento que muestra la información del vendedor cuando se hace click en la opcion del menú

    private TextView tvInfo;
    private ImageView ivLogo;

    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvInfo = view.findViewById(R.id.tvInfo);
        tvInfo.setText(getText(R.string.info_text));

        ivLogo = view.findViewById(R.id.ivLogo);
        Glide.with(view).load(R.drawable._7b3dd98_ecd0_4bad_9d6f_edc99f629753).into(ivLogo);
    }
}