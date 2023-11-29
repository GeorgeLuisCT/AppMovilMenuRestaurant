package com.gaedet.menujosue.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.gaedet.menujosue.R;
import com.gaedet.menujosue.database.DatabaseManager;
import com.gaedet.menujosue.models.Food;
import com.gaedet.menujosue.models.ShoppingCart;
import com.gaedet.menujosue.views.ContainerMenuFragment;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private Context context;
    private List<Food> foodList;
    private  DatabaseManager databaseManager;

    private OnItemClickListener onItemClickListener;


    public FoodAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
        databaseManager = new DatabaseManager(context);

    }


    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.iconImageView.setImageResource(food.getIcon());
        holder.nameTextView.setText(food.getName());
        holder.textViewPrice.setText("S/ " + (String.valueOf(food.getPrice())));

        if (food.getIsFavorite() == 1) {
            holder.addFavorite.setImageResource(R.drawable.icon_favorite_24);
        }

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(holder.getAdapterPosition());
            }
        });

        holder.addFoodToCart.setOnClickListener(view -> {
            databaseManager.open();
            float total = food.getPrice() * 1;
            ShoppingCart shoppingCart = new ShoppingCart(food.getId(),food.getName(), food.getIcon(), food.getPrice(), 1, total );
            databaseManager.insertShoppingCartItem(shoppingCart);
            databaseManager.close();

            Toast.makeText(context, food.getName()+ " Agregado al carrito", Toast.LENGTH_LONG).show();
        });

        holder.addFavorite.setOnClickListener(view -> {
            databaseManager.open();
            databaseManager.toggleFavorite(food.getId(), ContainerMenuFragment.NAME_CURRENT_TABLE);
            int currentIsFavorite = food.getIsFavorite();
            food.setIsFavorite(currentIsFavorite == 1 ? 0 : 1);
            databaseManager.close();

            if (food.getIsFavorite() == 1) {
                holder.addFavorite.setImageResource(R.drawable.icon_favorite_24);
            }else {
                holder.addFavorite.setImageResource(R.drawable.icon_favorite_border_24);

            }
        });

    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImageView;
        TextView nameTextView, textViewPrice;
        ImageView addFoodToCart, addFavorite;

        public FoodViewHolder(View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.imageIconfood);
            nameTextView = itemView.findViewById(R.id.textViewNameFood);
            textViewPrice = itemView.findViewById((R.id.textViewPrice));
            addFoodToCart = itemView.findViewById(R.id.addFoodToCart);
            addFavorite = itemView.findViewById(R.id.addFavorite);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}

