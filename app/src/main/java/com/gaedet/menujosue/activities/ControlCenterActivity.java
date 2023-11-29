package com.gaedet.menujosue.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.gaedet.menujosue.R;
import com.gaedet.menujosue.adapters.ShoppingCartAdapter;
import com.gaedet.menujosue.database.DatabaseManager;
import com.gaedet.menujosue.interfaces.DataUpdateListener;
import com.gaedet.menujosue.interfaces.OnLongPressListener;
import com.gaedet.menujosue.models.ShoppingCart;
import com.gaedet.menujosue.views.ContainerShoppingCartFragment;
import com.gaedet.menujosue.views.ContainerMenuFragment;
import com.gaedet.menujosue.views.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ControlCenterActivity extends AppCompatActivity implements DataUpdateListener, OnLongPressListener {

    float totalGeneral = 0;
    DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_center);

        validateLogin();

        DatabaseManager databaseManager = new DatabaseManager(this);
        databaseManager.InsertInitialData(this);

        // Cargar el primer fragmento por defecto
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new ContainerMenuFragment())
                    .commit();
        }

        // Configurar el listener para la barra de navegación inferior
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_view);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_home) {
                replaceFragment(new ContainerMenuFragment());
                return true;
            } else if (item.getItemId() == R.id.menu_shopping_cart) {
                replaceFragment(new ContainerShoppingCartFragment());
                return true;
            } else if (item.getItemId() == R.id.menu_profile) {
                replaceFragment(new ProfileFragment());
                return true;
            }
            return false;
        });
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void onDataUpdated() {
        totalGeneral = 0;
        TableLayout tableLayout = findViewById(R.id.TableDetailsOrder);
         TextView costoServicioTextView = findViewById(R.id.costoServicioTextView);
        TextView totalGeneralTextView = findViewById(R.id.totalGeneralTextView);

        int childCount = tableLayout.getChildCount();

        for (int i = childCount - 1; i >= 1; i--) {
            View childView = tableLayout.getChildAt(i);
            if (childView instanceof TableRow) {
                tableLayout.removeViewAt(i);
            }
        }

        DatabaseManager databaseManager = new DatabaseManager(this);
        databaseManager.open();

        List<ShoppingCart> shoppingCarts = databaseManager.getAllShoppingCartItems();
        databaseManager.close();

        for (ShoppingCart fila : shoppingCarts) {
            View customRow = getLayoutInflater().inflate(R.layout.custom_table_row, null);
            TextView textView1 = customRow.findViewById(R.id.text1);
            TextView textView2 = customRow.findViewById(R.id.text2);
            TextView textView3 = customRow.findViewById(R.id.text3);
            TextView textView4 = customRow.findViewById(R.id.text4);
            textView1.setText(fila.getNameProducto());
            textView2.setText(String.valueOf(fila.getQuantity()));
            textView3.setText(String.valueOf(fila.getPrice()));
            textView4.setText(String.format("S/ %.2f", fila.getTotal()));
            totalGeneral += fila.getTotal();
            tableLayout.addView(customRow);
        }

        // Actualizar el costo del servicio y el total general

        costoServicioTextView.setText("S/. Gratis");

        String formattedTotal = String.format("S/ %.2f", totalGeneral);
        totalGeneralTextView.setText(formattedTotal);

    }

    @Override
    public void onItemLongPress(int position, int cantSelect) {
        System.out.println("posicion que me llego" + position);
        ConstraintLayout LLBarrerTopOptions = findViewById(R.id.barrerTopOptions);
        RecyclerView recyclerShoppingCart = findViewById(R.id.recyclerViewShoppingCart);
        ShoppingCartAdapter foodAdapter = (ShoppingCartAdapter) recyclerShoppingCart.getAdapter();

        if (cantSelect > 0){
            LLBarrerTopOptions.setVisibility(View.VISIBLE);

            Button buttonCancel = LLBarrerTopOptions.findViewById(R.id.buttonCancel);
            ImageButton imageButtonDeleteItem = LLBarrerTopOptions.findViewById(R.id.imageButtonDeleteItem);
            buttonCancel.setOnClickListener(view -> {
                assert foodAdapter != null;
                foodAdapter.hideAllLayoutBackgrounds();
                LLBarrerTopOptions.setVisibility(View.GONE);
                System.out.println("se a precionado cancelar");
            });

            imageButtonDeleteItem.setOnClickListener(view -> {
                assert foodAdapter != null;
                foodAdapter.removeItem();
                LLBarrerTopOptions.setVisibility(View.GONE);

                databaseManager = new DatabaseManager(this);
                databaseManager.open();
                boolean isShoppingCartEmpty = databaseManager.isShoppingCartEmpty();
                databaseManager.close();

                if (isShoppingCartEmpty){

                    TextView info = findViewById(R.id.info);
                    ConstraintLayout content = findViewById(R.id.contet);

                    info.setVisibility(View.VISIBLE);
                    content.setVisibility(View.GONE);
                }
            });

        }else {
            LLBarrerTopOptions.setVisibility(View.GONE);
        }
        System.out.println("la cantidad que me llego es" + cantSelect);
    }

    public void validateLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);

        // Obtener los valores de las preferencias
        String nombre = sharedPreferences.getString("nombre", "");
        String apellido = sharedPreferences.getString("apellido", "");

        // Verificar si el nombre está presente y no es una cadena vacía
        if (nombre.isEmpty() && apellido.isEmpty()) {
            Intent intent = new Intent(ControlCenterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

}