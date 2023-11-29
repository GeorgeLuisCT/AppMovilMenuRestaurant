package com.gaedet.menujosue.views;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gaedet.menujosue.R;

import java.util.Objects;


public class ProfileFragment extends Fragment {

    String nombre;
    String apellido;
    String email;
    String telefono;
    String direccion;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MisPreferencias", MODE_PRIVATE);

             nombre = sharedPreferences.getString("nombre", "");
             apellido = sharedPreferences.getString("apellido", "");
             email = sharedPreferences.getString("email", "");
             telefono = sharedPreferences.getString("telefono", "");
             direccion = sharedPreferences.getString("direccion", "");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView name = view.findViewById(R.id.name);
        TextView firstName = view.findViewById(R.id.firstName);
        TextView email = view.findViewById(R.id.email);
        TextView phone = view.findViewById(R.id.phone);
        TextView address = view.findViewById(R.id.address);

        TextView textViewName = view.findViewById(R.id.textViewName);
        TextView textViewEmail = view.findViewById(R.id.textViewEmail);


        name.setText(nombre);
        firstName.setText(apellido);
        email.setText(this.email);
        phone.setText(telefono);
        address.setText(direccion);

        textViewName.setText(nombre);
        textViewEmail.setText(this.email);
        return view;


    }
}