package com.gaedet.menujosue.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;

import com.gaedet.menujosue.R;
import com.gaedet.menujosue.models.Food;
import com.gaedet.menujosue.models.ShoppingCart;

import java.util.ArrayList;

public class DatabaseManager {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public DatabaseManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void InsertInitialData(Context context){

        //obtner proferecias
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        boolean dataChange = sharedPreferences.getBoolean("dataChange", false);
        if (!dataChange){

            ArrayList<Food> foods = new ArrayList<>();
            ArrayList<Food> extras = new ArrayList<>();
            ArrayList<Food> beverages = new ArrayList<>();

            foods.add(new Food(R.drawable.aji_gallina, "Ají de Gallina", 6.0f, "Ají de Gallina con una deliciosa mezcla de ingredientes:\n- Pollo desmenuzado\n- Ají amarillo\n- Leche\n- Pan\n- Queso parmesano\n- Papas\n- Arroz"));
            foods.add(new Food(R.drawable.bistek, "Bistek", 6.0f, "Bistek con sabores auténticos:\n- Trozos de carne\n- Cebolla\n- Tomate\n- Ají amarillo\n- Papas fritas\n- Salsa de soja\n- Arroz"));
            foods.add(new Food(R.drawable.filete, "Filete", 6.0f, "Filete jugoso y sabroso con ingredientes de primera calidad:\n- Filete de res\n- Salsa especial\n- Guarnición de tu elección"));
            foods.add(new Food(R.drawable.cecina, "Cecina", 6.0f, "Cecina de cerdo con un sabor inigualable:\n- Cecina de cerdo marinada\n- Yuca frita\n- Salsa de ají\n- Ensalada"));
            foods.add(new Food(R.drawable.arroz_cubana, "Arroz a la Cubana", 6.0f, "Arroz a la Cubana con un toque exquisito:\n- Arroz\n- Huevo frito\n- Plátano frito\n- Salsa de tomate"));
            foods.add(new Food(R.drawable.tortilla_atun, "Tortilla de Atún", 6.0f, "Tortilla de Atún con ingredientes saludables:\n- Atún en lata\n- Huevos\n- Cebolla\n- Tomate\n- Aceite de oliva"));
            foods.add(new Food(R.drawable.guiso_res, "Guiso de Res", 6.0f, "Guiso de Res con una mezcla de sabores:\n- Trozos de carne de res\n- Papas\n- Zanahorias\n- Guisantes\n- Cebolla"));
            foods.add(new Food(R.drawable.adobo_chancho, "Adobo de Chancho", 6.0f, "Adobo de Chancho con una combinación de ingredientes únicos:\n- Trozos de cerdo adobados\n- Ají panca\n- Comino\n- Chicha de jora\n- Papa sancochada"));
            foods.add(new Food(R.drawable.pollo_mechado, "Pollo Mechado", 6.0f, "Pollo Mechado con un sabor incomparable:\n- Pollo desmenuzado\n- Cebolla\n- Ají amarillo\n- Tomate\n- Comino\n- Papas\n- Arroz"));
            foods.add(new Food(R.drawable.arroz_pollo, "Arroz con Pollo", 6.0f, "Arroz con Pollo con ingredientes tradicionales:\n- Pollo troceado\n- Arroz\n- Cerveza\n- Sillao\n- Ají amarillo\n- Zanahorias"));
            foods.add(new Food(R.drawable.seco_pollo, "Seco de Pollo", 6.0f, "Seco de Pollo con un toque de sabor único:\n- Pollo desmenuzado\n- Cilantro\n- Ají amarillo\n- Cerveza negra\n- Garbanzos\n- Arroz"));
            foods.add(new Food(R.drawable.tallarines, "Tallarines Verdes", 6.0f, "Tallarines con delicioso pesto verde"));
            foods.add(new Food(R.drawable.tallarines, "Tallarines Saltado", 7.0f, "Tallarines saltados con opción de carne o pollo"));
            foods.add(new Food(R.drawable.tallarines, "Tallarines Rojos", 6.5f, "Tallarines con deliciosa salsa criolla"));
            foods.add(new Food(R.drawable.brocoli_saltado, "Brócoli Saltado", 6.0f, "Brócoli Saltado con un toque de sabor:\n- Brócoli\n- Zanahorias\n- Cebolla\n- Salsa de soja\n- Ají amarillo"));

            extras.add(new Food(R.drawable.ceviche, "Ceviche", 9.0f, "Delicioso ceviche peruano con ingredientes frescos:\n- Pescado fresco\n- Limón\n- Cebolla\n- Ají\n- Maíz\n- Camote\n- Lechuga"));
            extras.add(new Food(R.drawable.chuleta, "Chuleta", 9.0f, "Chuleta de cerdo con un sabor inigualable:\n- Chuleta de cerdo\n- Salsa especial\n- Guarnición de tu elección"));
            extras.add(new Food(R.drawable.trucha, "Trucha", 9.0f, "Trucha fresca con un toque exquisito:\n- Trucha\n- Limón\n- Hierbas aromáticas\n- Papas\n- Ensalada"));
            extras.add(new Food(R.drawable.saltadito_pollo, "Saltadito de Pollo", 9.0f, "Saltadito de Pollo con una mezcla de sabores únicos:\n- Trozos de pollo\n- Cebolla\n- Tomate\n- Ají amarillo\n- Papas fritas\n- Salsa de soja\n- Arroz"));
            extras.add(new Food(R.drawable.lomosaltado, "Lomo Saltado", 9.0f, "Lomo saltado peruano con ingredientes auténticos:\n- Trozos de carne\n- Cebolla\n- Tomate\n- Ají amarillo\n- Papas fritas\n- Salsa de soja\n- Arroz"));
            extras.add(new Food(R.drawable.chaufa_pollo, "Chaufa de Pollo", 9.0f, "Chaufa de Pollo con un toque oriental:\n- Pollo troceado\n- Arroz frito\n- Huevo\n- Cebolla china\n- Sillao"));
            extras.add(new Food(R.drawable.chaufa_chancho, "Chaufa de Chancho", 9.0f, "Chaufa de Chancho con sabores irresistibles:\n- Chancho troceado\n- Arroz frito\n- Huevo\n- Cebolla china\n- Sillao"));
            extras.add(new Food(R.drawable.chaufa_res, "Chaufa de Res", 9.0f, "Chaufa de Res con un toque de sabor único:\n- Trozos de carne de res\n- Arroz frito\n- Huevo\n- Cebolla china\n- Sillao"));

            beverages.add(new Food(R.drawable.gaseosas, "Gaseosas", 1.5f, "Refrescantes gaseosas disponibles en diferentes sabores."));
            beverages.add(new Food(R.drawable.coca_cola, "Coca Cola", 2.0f, "Bebida carbonatada de Coca Cola en una botella fría."));
            beverages.add(new Food(R.drawable.inka_cola, "Inka Cola", 2.0f, "Inka Cola, la bebida dorada del Perú."));
            beverages.add(new Food(R.drawable.cerveza, "Cerveza", 3.0f, "Cerveza fría para acompañar tu comida."));
            beverages.add(new Food(R.drawable.chicha, "Chicha", 2.5f, "Chicha morada, bebida tradicional peruana."));
            beverages.add(new Food(R.drawable.maracuya, "Maracuyá", 1.8f, "Refrescante jugo de maracuyá."));
            beverages.add(new Food(R.drawable.cevada, "Cevada", 1.5f, "Bebida de cevada, saludable y refrescante."));
            beverages.add(new Food(R.drawable.limonada, "Limonada", 1.8f, "Limonada casera, perfecta para calmar la sed."));
            beverages.add(new Food(R.drawable.carambola, "Carambola", 2.0f, "Carambola, una bebida exótica para disfrutar."));


            open();
            for (Food food: foods) {
                insertFood(food);
            }

            for (Food food: extras) {
                insertExtra(food);
            }

            for (Food food: beverages) {
                insertBeverages(food);
            }
            close();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("dataChange", true);
            editor.apply();
        }
    }

    // Insert a new food item into the database
    public long insertFood(Food food) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, food.getName());
        values.put(DatabaseHelper.COLUMN_ICON, food.getIcon());
        values.put(DatabaseHelper.COLUMN_PRICE, food.getPrice());
        values.put(DatabaseHelper.COLUMN_DESCRIPTION, food.getDescription());

        return database.insert(DatabaseHelper.TABLE_FOOD, null, values);
    }

    public long insertExtra(Food food) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, food.getName());
        values.put(DatabaseHelper.COLUMN_ICON, food.getIcon());
        values.put(DatabaseHelper.COLUMN_PRICE, food.getPrice());
        values.put(DatabaseHelper.COLUMN_DESCRIPTION, food.getDescription());

        return database.insert(DatabaseHelper.TABLE_EXTRAS, null, values);
    }

    public long insertBeverages(Food food) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, food.getName());
        values.put(DatabaseHelper.COLUMN_ICON, food.getIcon());
        values.put(DatabaseHelper.COLUMN_PRICE, food.getPrice());
        values.put(DatabaseHelper.COLUMN_DESCRIPTION, food.getDescription());

        return database.insert(DatabaseHelper.TABLE_BEVERAGES, null, values);
    }

    // Insert a new shopping cart item into the database
    public long insertShoppingCartItem(ShoppingCart shoppingCart) {
        Log.d("IDProducto",String.valueOf(shoppingCart.getIdProducto()) );
        // Verificar si el producto ya existe en el carrito
        String[] columnsToReturn = {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_QUANTITY, DatabaseHelper.COLUMN_TOTAL};
        String selection = DatabaseHelper.COLUMN_PRODUCT_NAME + " = ?";
        String[] selectionArgs = {String.valueOf(shoppingCart.getNameProducto())};
        Cursor cursor = database.query(DatabaseHelper.TABLE_SHOPPING_CART, columnsToReturn, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            // El producto ya existe, actualiza la cantidad y el total
            @SuppressLint("Range") int existingId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
            @SuppressLint("Range") float existingQuantity = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.COLUMN_QUANTITY));
            @SuppressLint("Range") float existingTotal = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.COLUMN_TOTAL));

            float newQuantity = existingQuantity + shoppingCart.getQuantity();
            float newTotal = existingTotal + shoppingCart.getTotal();

            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_QUANTITY, newQuantity);
            values.put(DatabaseHelper.COLUMN_TOTAL, newTotal);

            // Actualiza el registro existente
            String where = DatabaseHelper.COLUMN_ID + " = ?";
            String[] whereArgs = {String.valueOf(existingId)};
            long updatedRows = database.update(DatabaseHelper.TABLE_SHOPPING_CART, values, where, whereArgs);
            cursor.close();

            return updatedRows;
        } else {
            // El producto no existe, inserta un nuevo registro
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_PRODUCT_NAME, shoppingCart.getNameProducto());
            values.put(DatabaseHelper.COLUMN_PRODUCT_ICON, shoppingCart.getIconProduct());
            values.put(DatabaseHelper.COLUMN_PRODUCT_PRICE, shoppingCart.getPrice());
            values.put(DatabaseHelper.COLUMN_QUANTITY, shoppingCart.getQuantity());
            values.put(DatabaseHelper.COLUMN_TOTAL, shoppingCart.getTotal());

            long insertedRow = database.insert(DatabaseHelper.TABLE_SHOPPING_CART, null, values);

            if (cursor != null) {
                cursor.close();
            }

            return insertedRow;
        }
    }


    public ArrayList<Food> getAllFood() {
        ArrayList<Food> foodList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FOOD;
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                @SuppressLint("Range") int icon = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ICON));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
                @SuppressLint("Range") float price = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRICE));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION));
                @SuppressLint("Range") int isFavorite = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.IS_FAVORITE));

                Food food = new Food(id,icon, name, price, description, isFavorite);
                foodList.add(food);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return foodList;
    }

    public ArrayList<Food> getAllExtras() {
        ArrayList<Food> foodList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_EXTRAS;
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                @SuppressLint("Range") int icon = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ICON));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
                @SuppressLint("Range") float price = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRICE));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION));
                @SuppressLint("Range") int isFavorite = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.IS_FAVORITE));

                Food food = new Food(id,icon, name, price, description, isFavorite);
                foodList.add(food);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return foodList;
    }

    public ArrayList<Food> getAllBeverages() {
        ArrayList<Food> foodList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_BEVERAGES;
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                @SuppressLint("Range") int icon = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ICON));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
                @SuppressLint("Range") float price = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRICE));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION));
                @SuppressLint("Range") int isFavorite = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.IS_FAVORITE));

                Food food = new Food(id,icon, name, price, description, isFavorite);
                foodList.add(food);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return foodList;
    }

    // Método para obtener todos los registros de la tabla del carrito de compras (ShoppingCart)
    public ArrayList<ShoppingCart> getAllShoppingCartItems() {
        ArrayList<ShoppingCart> shoppingCartList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_SHOPPING_CART;
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int idProducto = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                @SuppressLint("Range") String nameProducto = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRODUCT_NAME));
                @SuppressLint("Range") int iconProducto = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRODUCT_ICON));
                @SuppressLint("Range") float price = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRODUCT_PRICE));
                @SuppressLint("Range") int quantity = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_QUANTITY));
                @SuppressLint("Range") float total = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.COLUMN_TOTAL));

                ShoppingCart shoppingCart = new ShoppingCart(idProducto, nameProducto,iconProducto, price, quantity, total);
                shoppingCartList.add(shoppingCart);
                System.out.println("id del producto optenido " + idProducto);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return shoppingCartList;
    }

    public void updateTotal(int productId, int newQuantity, float price) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_QUANTITY, newQuantity);

        // Calcula el nuevo total y actualízalo en la base de datos
        float newTotal = newQuantity * price;
        values.put(DatabaseHelper.COLUMN_TOTAL, newTotal);

        String selection = DatabaseHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(productId) };

        try {
            int rowsAffected = database.update(DatabaseHelper.TABLE_SHOPPING_CART, values, selection, selectionArgs);
            if (rowsAffected > 0) {
                System.out.println("Actualización exitosa. Filas afectadas: " + rowsAffected);
            } else {
                System.out.println("No se realizaron cambios en la base de datos.");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar la base de datos: " + e.getMessage());
        }
    }




    // Método para obtener un registro específico de la tabla de alimentos (Food) por su ID
    public Food getFoodById(int foodId) {
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FOOD +
                " WHERE " + DatabaseHelper.COLUMN_ID + " = ?";
        Cursor cursor = database.rawQuery(selectQuery, new String[]{String.valueOf(foodId)});

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
            @SuppressLint("Range") int icon = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ICON));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
            @SuppressLint("Range") float price = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRICE));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION));

            cursor.close();

            return new Food(id,icon, name, price, description);
        } else {
            return null; // El registro no se encontró
        }
    }

    public Food getExtraById(int foodId) {
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_EXTRAS +
                " WHERE " + DatabaseHelper.COLUMN_ID + " = ?";
        Cursor cursor = database.rawQuery(selectQuery, new String[]{String.valueOf(foodId)});

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
            @SuppressLint("Range") int icon = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ICON));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
            @SuppressLint("Range") float price = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRICE));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION));

            cursor.close();

            return new Food(id,icon, name, price, description);
        } else {
            return null; // El registro no se encontró
        }
    }

    public Food getBeveragesById(int foodId) {
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_BEVERAGES +
                " WHERE " + DatabaseHelper.COLUMN_ID + " = ?";
        Cursor cursor = database.rawQuery(selectQuery, new String[]{String.valueOf(foodId)});

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
            @SuppressLint("Range") int icon = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ICON));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
            @SuppressLint("Range") float price = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRICE));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION));

            cursor.close();

            return new Food(id,icon, name, price, description);
        } else {
            return null; // El registro no se encontró
        }
    }

    // Método para obtener un registro específico del carrito de compras (ShoppingCart) por su ID
    public ShoppingCart getShoppingCartItemById(int itemId) {
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_SHOPPING_CART +
                " WHERE " + DatabaseHelper.COLUMN_ID + " = ?";
        Cursor cursor = database.rawQuery(selectQuery, new String[]{String.valueOf(itemId)});

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int idProducto = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRODUCT_ID));
            @SuppressLint("Range") String nameProducto = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRODUCT_NAME));
            @SuppressLint("Range") int iconProducto = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRODUCT_ICON));
            @SuppressLint("Range") float price = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRODUCT_PRICE));
            @SuppressLint("Range") int quantity = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_QUANTITY));
            @SuppressLint("Range") float total = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.COLUMN_TOTAL));

            cursor.close();

            return new ShoppingCart(idProducto, nameProducto,iconProducto, price, quantity, total);
        } else {
            return null; // El registro no se encontró
        }
    }

    public int deleteShoppingCartItem(int itemId) {
        String whereClause = DatabaseHelper.COLUMN_ID + " = ?";
        String[] whereArgs = {String.valueOf(itemId)};

        // Elimina el registro con el ID especificado
        return database.delete(DatabaseHelper.TABLE_SHOPPING_CART, whereClause, whereArgs);
    }

    public boolean isShoppingCartEmpty() {
        String countQuery = "SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_SHOPPING_CART;
        Cursor cursor = database.rawQuery(countQuery, null);

        int count = 0;
        if (cursor != null) {
            cursor.moveToFirst();
            count = cursor.getInt(0);
            cursor.close();
        }

        return count == 0;
    }

    public void deleteAllRecords() {
        database.delete(DatabaseHelper.TABLE_SHOPPING_CART, null, null);
    }

    public void toggleFavorite(int itemId, String tableName) {

        // Consultar el valor actual de isFavorite
        String query = "SELECT " + DatabaseHelper.IS_FAVORITE + " FROM " + tableName +
                " WHERE " + DatabaseHelper.COLUMN_ID + " = " + itemId;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int currentValue = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.IS_FAVORITE));

            // Cambiar el valor de isFavorite
            int newValue = (currentValue == 0) ? 1 : 0;

            // Actualizar el valor en la base de datos
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.IS_FAVORITE, newValue);

            database.update(tableName, values, DatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(itemId)});
        }

        cursor.close();
    }




    // You can add other methods to update, delete, and retrieve data as needed.
}