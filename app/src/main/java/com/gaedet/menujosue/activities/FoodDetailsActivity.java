package com.gaedet.menujosue.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaedet.menujosue.R;
import com.gaedet.menujosue.database.DatabaseManager;
import com.gaedet.menujosue.models.Food;
import com.gaedet.menujosue.models.ShoppingCart;

import java.util.Objects;

public class FoodDetailsActivity extends AppCompatActivity {


    int numCant = 1;
    int foodId;
    DatabaseManager databaseManager;
    Food food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        // Habilitar la flecha de retroceso en la barra de herramientas
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.string_product_details);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_home_24);

        //Optener referencias
        ImageView ico = findViewById(R.id.imageViewIcon);
        TextView name = findViewById(R.id.textViewName);
        TextView price = findViewById(R.id.textViewPrice);
        TextView cant = findViewById(R.id.textViewCantFood);
        TextView description = findViewById(R.id.textViewDescription);
        ImageButton buttonMore = findViewById(R.id.imageButtonMore);
        ImageButton buttonLess = findViewById(R.id.imageButtonLess);
        Button buttonAddCart = findViewById(R.id.buttonAddCart);

        foodId = getIntent().getIntExtra("foodId", 0);
        String type = getIntent().getStringExtra("type");

        databaseManager = new DatabaseManager(this);
        databaseManager.open();

        if (type != null) {
            switch (type) {
                case "simplemenu":
                    food = databaseManager.getFoodById(foodId);
                    break;
                case "extramenu":
                    food = databaseManager.getExtraById(foodId);
                    break;
                case "bevergesmenu":
                    food = databaseManager.getBeveragesById(foodId);
                    break;
                default:
                    System.out.println("no llego ID");
            }
        }



        ico.setImageResource(food.getIcon());
        name.setText(food.getName());
        price.setText("S/. " + String.valueOf(food.getPrice()));
        description.setText(food.getDescription());


        cant.setText(String.valueOf(numCant));

        buttonMore.setOnClickListener(view -> {
            if (numCant < 100) {
                numCant++;
                cant.setText(String.valueOf(numCant));
            }
        });

        buttonLess.setOnClickListener(view -> {
            if (numCant > 1) {
                numCant--;
                cant.setText(String.valueOf(numCant));
            }
        });

        buttonAddCart.setOnClickListener(view -> {
            databaseManager.open();
            float total = food.getPrice() * numCant;
            ShoppingCart shoppingCart = new ShoppingCart(foodId,food.getName(), food.getIcon(), food.getPrice(), numCant, total );
            databaseManager.insertShoppingCartItem(shoppingCart);
            databaseManager.close();

            showCustomDialog();

            System.out.println("agregado al carrito");
        });

        databaseManager.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showCustomDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_added_cart);

        dialog.show();
        Button dialogButton = dialog.findViewById(R.id.dialog_button);
        dialogButton.setOnClickListener(v -> {

            dialog.dismiss();
            this.finish();
        });


    }

}