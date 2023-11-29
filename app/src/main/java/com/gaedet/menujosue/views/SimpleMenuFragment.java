package com.gaedet.menujosue.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaedet.menujosue.R;
import com.gaedet.menujosue.activities.FoodDetailsActivity;
import com.gaedet.menujosue.adapters.FoodAdapter;
import com.gaedet.menujosue.database.DatabaseManager;
import com.gaedet.menujosue.models.Food;

import java.util.ArrayList;


public class SimpleMenuFragment extends Fragment {

    RecyclerView listMenu;
    private ArrayList<Food> foodList;
    DatabaseManager databaseManager;
    //private SearchView search;


    public SimpleMenuFragment() {
    }

    public static SimpleMenuFragment newInstance() {
        return new SimpleMenuFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_simple_menu, container, false);

       // search = view.findViewById(R.id.searchView);


        //Mostrar Lista de comida
        databaseManager = new DatabaseManager(getContext());
        listMenu = view.findViewById(R.id.listFoodMenu);
        databaseManager.open();
        foodList = databaseManager.getAllFood();
        databaseManager.close();

        FoodAdapter foodAdapter = new FoodAdapter(getContext(), foodList);
        listMenu.setAdapter(foodAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager (getContext(), 2);
        listMenu.setLayoutManager(layoutManager);

        foodAdapter.setOnItemClickListener(position -> {
            Food clickedFood = foodList.get(position);
            int foodId = clickedFood.getId();

            Intent intent = new Intent(getContext(), FoodDetailsActivity.class);
            intent.putExtra("foodId", foodId);
            intent.putExtra("type", "simplemenu");
            startActivity(intent);
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

       // InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
       // imm.hideSoftInputFromWindow(search.getWindowToken(), 0);

       // search.clearFocus();
        listMenu.requestFocus();

        System.out.println("regrese");
    }
}
