package dev.jmsaez.concesionariofragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dev.jmsaez.concesionariofragment.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    //Variables para poder conectar a la base de datos.
    private static final String URL = "jdbc:mariadb://146.59.237.189/dam208_jmsgconcesionario";
    private static final String USER = "dam208_jmsg";
    private static final String PASSWORD = "dam208_jmsg";

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        new InfoAsyncTask().execute();
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //Clase interna donde se hace la consulta de la base de datos de forma asíncrona y carga el recycler-view
    @SuppressLint("StaticFieldLeak")
    public class InfoAsyncTask extends AsyncTask<Void, Void, List<Car>> {
        @Override
        protected List<Car> doInBackground(Void... voids) {
            List<Car> info = new ArrayList<>();

            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "SELECT * from coches";
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Car c = new Car(resultSet.getInt("ref"), resultSet.getString("titulo"),
                            resultSet.getString("descripcion"), resultSet.getString("tags"), resultSet.getString("url"), resultSet.getString("imagenes"),
                            resultSet.getInt("precio"));
                    info.add(c);
                }
            } catch (Exception e) {
                Log.e("InfoAsyncTask", "Error reading coches information", e);
            }

            return info;
        }

        @Override //Carga del recycler view.
        protected void onPostExecute(List<Car> result) {
            if (!result.isEmpty()) {
                RecyclerView rv = getView().findViewById(R.id.rvCars);
                rv.setLayoutManager(new LinearLayoutManager(getContext()));
                RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getContext());
                rv.setAdapter(recyclerAdapter);
                recyclerAdapter.setCarList(result);
            }
        }
    }
}