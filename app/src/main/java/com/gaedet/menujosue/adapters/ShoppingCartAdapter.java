package com.gaedet.menujosue.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gaedet.menujosue.R;
import com.gaedet.menujosue.database.DatabaseManager;
import com.gaedet.menujosue.models.ShoppingCart;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ShoppingCartViewHolder> {
    private Context context;
    private List<ShoppingCart> shoppingCartList;
    private OnItemLongPressListener itemLongClickListener;
    private  int cantSlect = 0;
    private List<LinearLayout> layoutBackgroundList;
    private List<Integer> positionSelect;



    public ShoppingCartAdapter(Context context, List<ShoppingCart> shoppingCartList) {
        this.context = context;
        this.shoppingCartList = shoppingCartList;
        this.layoutBackgroundList = new ArrayList<>();
        this.positionSelect = new ArrayList<>();
    }


    @NonNull
    @Override
    public ShoppingCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shopping_cart, parent, false);
        return new ShoppingCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShoppingCartViewHolder holder, final int position) {
        final ShoppingCart shoppingCart = shoppingCartList.get(position);
        holder.iconImageView.setImageResource(shoppingCart.getIconProduct());
        holder.nameProductTextView.setText(shoppingCart.getNameProducto());
        holder.textViewPriceProduct.setText("S/. " + shoppingCart.getPrice());

        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String formattedNumber = decimalFormat.format(shoppingCart.getTotal());
        holder.textViewTotalShopingCart.setText("S/. " + formattedNumber);

        holder.textViewCant.setText(String.valueOf(shoppingCart.getQuantity()));

        // Agregar un clic al botón "Remove"
        holder.imageButtonRemove.setOnClickListener(view -> {
            int currentQuantity = shoppingCart.getQuantity();
            if (currentQuantity > 1) {
                shoppingCart.setQuantity(currentQuantity - 1);
                DatabaseManager databaseManager = new  DatabaseManager(context);
                databaseManager.open();
                databaseManager.updateTotal(shoppingCart.getIdProducto(), shoppingCart.getQuantity(), shoppingCart.getPrice());
                databaseManager.close();
                notifyDataSetChanged();
            }
        });

        // Agregar un clic al botón "Add"
        holder.imageButtonAdd.setOnClickListener(view -> {
            int currentQuantity = shoppingCart.getQuantity();
            if (currentQuantity < 100) {
                shoppingCart.setQuantity(currentQuantity + 1);
                DatabaseManager databaseManager = new  DatabaseManager(context);
                databaseManager.open();
                databaseManager.updateTotal(shoppingCart.getIdProducto(), shoppingCart.getQuantity(), shoppingCart.getPrice());
                databaseManager.close();
                notifyDataSetChanged();
            }
        });

        // Agregar un clic largo al elemento
        holder.itemView.setOnLongClickListener(view -> {
            if (itemLongClickListener != null) {

                if (!layoutBackgroundList.contains(holder.layoutBackground)){
                    holder.layoutBackground.setVisibility(View.VISIBLE);
                    cantSlect++;
                    layoutBackgroundList.add(holder.layoutBackground);
                    positionSelect.add(position);
                }else{
                    holder.layoutBackground.setVisibility(View.GONE);
                    cantSlect--;
                    layoutBackgroundList.remove(holder.layoutBackground);
                    positionSelect.remove(position);
                }
                itemLongClickListener.onItemLongPress(position, cantSlect);
                System.out.println("matuve precionado la posicion seleccionado es " + position);
            }
            return true;
        });

    }


    @Override
    public int getItemCount() {
        return shoppingCartList.size();
    }

    public static class ShoppingCartViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImageView;
        TextView nameProductTextView, textViewPriceProduct, textViewTotalShopingCart, textViewCant;
        LinearLayout layoutBackground;
        ImageButton imageButtonAdd, imageButtonRemove;


        public ShoppingCartViewHolder(View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.imageIconfood);
            nameProductTextView = itemView.findViewById(R.id.nameShoppingCart);
            textViewPriceProduct = itemView.findViewById(R.id.textViewPriceProduct);
            textViewTotalShopingCart = itemView.findViewById(R.id.textViewTotalShopingCart);
            textViewCant = itemView.findViewById(R.id.textViewCantCart);
            imageButtonAdd = itemView.findViewById(R.id.imageButtonAdd);
            imageButtonRemove = itemView.findViewById(R.id.imageButtonRemove);
            layoutBackground = itemView.findViewById(R.id.backGroundSelect);
        }
    }

    public void setOnItemLongClickListener(OnItemLongPressListener listener, int cantSelect) {
        this.itemLongClickListener = listener;
        this.cantSlect = cantSelect;
    }
    public interface OnItemLongPressListener {
        void onItemLongPress(int position, int canSelect);
    }

    public void hideAllLayoutBackgrounds() {
        for (LinearLayout layoutBackground : layoutBackgroundList) {
            layoutBackground.setVisibility(View.GONE);
        }

        cantSlect = 0;
        layoutBackgroundList.clear();
    }

    public void removeItem() {
        Collections.sort(positionSelect, Collections.reverseOrder());

        DatabaseManager databaseManager = new DatabaseManager(context);
        databaseManager.open();
        for (Integer position : positionSelect) {
            if (position >= 0 && position < shoppingCartList.size()) {
                ShoppingCart shoppingCart = shoppingCartList.get(position);
                databaseManager.deleteShoppingCartItem(shoppingCart.getIdProducto());
                shoppingCartList.remove((int) position);
                notifyItemRemoved(position);
            }
        }
        databaseManager.close();
        positionSelect.clear();
        hideAllLayoutBackgrounds();
        notifyDataSetChanged();
    }


}
