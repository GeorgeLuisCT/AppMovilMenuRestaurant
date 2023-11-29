package com.gaedet.menujosue.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.gaedet.menujosue.R;
import com.gaedet.menujosue.database.DatabaseManager;
import com.gaedet.menujosue.interfaces.DataUpdateListener;
import com.gaedet.menujosue.models.ShoppingCart;

import java.util.List;
import java.util.Objects;

public class DetailsOrderFragment extends Fragment {

    TableLayout tableLayout;
    TextView costoServicioTextView;
    TextView totalGeneralTextView;
    public DetailsOrderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_details_order, container, false);

        Spinner spinner = rootView.findViewById(R.id.spinnerOrderType);
        // Define una lista de opciones para el Spinner
        String[] opciones = {"Reservar mesa", "Delivery"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.custom_spinner_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Configurar un listener para manejar los cambios de selección en el Spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Obtener el valor seleccionado del Spinner
                String opcionSeleccionada = opciones[position];

                // Guardar el valor seleccionado en SharedPreferences
                SharedPreferences preferences = requireActivity().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("tipoPedido", opcionSeleccionada);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // En este ejemplo, no se hace nada cuando no hay selección
            }
        });


        // Configurar la tabla con datos de ejemplo
        tableLayout = rootView.findViewById(R.id.TableDetailsOrder);
        costoServicioTextView = rootView.findViewById(R.id.costoServicioTextView);
        totalGeneralTextView = rootView.findViewById(R.id.totalGeneralTextView);

        return rootView;
    }
}
