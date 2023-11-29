package com.gaedet.menujosue.views;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaedet.menujosue.R;
import com.gaedet.menujosue.adapters.ShoppingCartAdapter;
import com.gaedet.menujosue.database.DatabaseManager;
import com.gaedet.menujosue.interfaces.DataUpdateListener;
import com.gaedet.menujosue.interfaces.OnLongPressListener;
import com.gaedet.menujosue.models.ShoppingCart;

import java.util.ArrayList;

public class MyOrderFragment extends Fragment {

    RecyclerView recyclerShoppingCart;
    private ArrayList<ShoppingCart> shoppingCartsList;
    DatabaseManager databaseManager;
    OnLongPressListener onLongPressListener;
    int cantSelect;

    public MyOrderFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frgment_my_order, container, false);

        //Mostrar Lista de comida
        databaseManager = new  DatabaseManager(getContext());
        recyclerShoppingCart = view.findViewById(R.id.recyclerViewShoppingCart);
        databaseManager.open();
        shoppingCartsList = databaseManager.getAllShoppingCartItems();
        databaseManager.close();

        ShoppingCartAdapter foodAdapter = new ShoppingCartAdapter(getContext(), shoppingCartsList);
        foodAdapter.setOnItemLongClickListener(new ShoppingCartAdapter.OnItemLongPressListener() {
            @Override
            public void onItemLongPress(int position, int canSelect) {
                MyOrderFragment.this.updateShopping(position, canSelect);
                System.out.println("soy yo la cantidad seleccionada es " + canSelect);
                cantSelect = canSelect;
            }
            }, cantSelect
        );
        recyclerShoppingCart.setAdapter(foodAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerShoppingCart.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DataUpdateListener) {
            onLongPressListener = (OnLongPressListener) context;
        } else {
            throw new RuntimeException("La actividad no implementa la interfaz MiInterfaz");
        }
    }

    public void updateShopping(int position, int cantSelect) {
        // Llama al método en la página 2
        if (onLongPressListener != null) {
            onLongPressListener.onItemLongPress(position, cantSelect);
            System.out.println("el escuchador no es nulo");
        }else {
            System.out.println("el escuchador es nulo");
        }
    }
}