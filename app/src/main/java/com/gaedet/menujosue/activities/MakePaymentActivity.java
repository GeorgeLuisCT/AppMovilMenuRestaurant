package com.gaedet.menujosue.activities;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gaedet.menujosue.R;
import com.gaedet.menujosue.database.DatabaseManager;
import com.gaedet.menujosue.models.ShoppingCart;
import com.gaedet.menujosue.utilities.SendEmailTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class MakePaymentActivity extends AppCompatActivity implements SendEmailTask.EmailTaskListener {

    LinearLayout layoutYape, layoutPlin, layoutBN;
    Button btnUbloadVoucher;
    DatabaseManager databaseManager;

    private String cliente;

    private List<ShoppingCart> pedido;
    private AlertDialog progressDialog;
    private float totalGeneral;
    private String tipoPedido;
    public static int MY_PERMISSIONS_REQUEST_MANAGE_EXTERNAL_STORAGE = 0;

    private static final int PICK_IMAGE = 1;
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment);

        Intent intent = getIntent();
        String valorRecibido = intent.getStringExtra("tipo");

        System.out.println(valorRecibido);

        layoutYape = findViewById(R.id.ViewYape);
        layoutPlin = findViewById(R.id.ViewPlin);
        layoutBN = findViewById(R.id.ViewBN);
        btnUbloadVoucher = findViewById(R.id.btnUbloadVoucher);

        //OPTENER LOS VLAORES CORRESPONEITES PARA LOS MENSJAE AIMDICOASFMVEFO,FC
        databaseManager = new  DatabaseManager(this);
        databaseManager.open();
        pedido = databaseManager.getAllShoppingCartItems();
        databaseManager.close();

        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);

        String nombre = sharedPreferences.getString("nombre", "");
        String apellido = sharedPreferences.getString("apellido", "");

        for (ShoppingCart item: pedido) {
            totalGeneral = totalGeneral + item.getTotal();
        }

        cliente = nombre + " " + apellido;

        SharedPreferences preferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String tipoPedidoAlmacenado = preferences.getString("tipoPedido", "");

        tipoPedido = tipoPedidoAlmacenado;


        switch (valorRecibido) {

            case "yape":
                layoutYape.setVisibility(View.VISIBLE);
                break;
            case "plin":
                layoutPlin.setVisibility(View.VISIBLE);
                break;
            case "bn":
                layoutBN.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }

        btnUbloadVoucher.setOnClickListener(view -> {
            System.out.println("Versión de Android: " + Build.VERSION.SDK_INT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // No tienes permiso, solicítalo al usuario
                    System.out.println("no tiene permiso");
                    ActivityCompat.requestPermissions((Activity) this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_MANAGE_EXTERNAL_STORAGE);
                }else{
                    System.out.println("abriendo galera");
                    openGallery();
                }
            }else{
                System.out.println("la version es " + Build.VERSION_CODES.Q);
            }

        });
    }

    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    public void onEmailTaskCompleted() {
        Toast.makeText(this, "Correo electrónico enviado correctamente", Toast.LENGTH_SHORT).show();
        dismissProgressDialog();
        showOrderConfirmationDialog();
    }

    @Override
    public void onEmailTaskFailed(String errorMessage) {
        Toast.makeText(this, "Error al enviar el correo electrónico: " + errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri selectedImageUri = data.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    showImage(bitmap, selectedImageUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void showImage(Bitmap bitmap, Uri selectedImageUri) {
        // Crear una nueva instancia de ImageView
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(bitmap);

        // Crear el diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Imagen seleccionada")
                .setView(imageView)
                .setPositiveButton("Enviar", (dialogInterface, i) -> {

                    SendEmailTask sendEmailTask  = new SendEmailTask(MakePaymentActivity.this,"example@gmail.com", "Nuevo pedido", buildMessage().toString(), "example@gmail.com", "example", selectedImageUri);
                    sendEmailTask.setEmailTaskListener(this);
                    sendEmailTask.execute();
                    showProgressDialog();
                })
                .setNegativeButton("Cancelar", (dialogInterface, i) -> {
                    // Aquí puedes agregar la lógica para cancelar
                })
                .show();
    }

    private void showOrderConfirmationDialog() {

        databaseManager = new DatabaseManager(this);
        databaseManager.open();
        databaseManager.deleteAllRecords();
        databaseManager.close();

        // Crear el segundo diálogo de confirmación
        AlertDialog.Builder confirmationBuilder = new AlertDialog.Builder(this);
        confirmationBuilder.setCancelable(false);
        confirmationBuilder.setTitle("Pedido realizado")
                .setMessage("¡Tu pedido ha sido enviado con éxito!")
                .setPositiveButton("Aceptar", (dialogInterface, i) -> {
                    finish();
                })
                .show();
    }

    public void showProgressDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.progress_dialog_layout, null);
        builder.setView(view);
        builder.setCancelable(false); // Para que no se pueda cerrar con un toque fuera del diálogo

        progressDialog = builder.create();
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private StringBuilder buildMessage(){
        // Construir el cuerpo del mensaje en formato HTML
        StringBuilder htmlBody = new StringBuilder();
        htmlBody.append("<html><head><style>");

        // Estilos CSS
        htmlBody.append("body { font-family: Arial, sans-serif; padding: 20px; }");
        htmlBody.append("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
        htmlBody.append("th, td { border: 1px solid #dddddd; text-align: left; padding: 8px; }");
        htmlBody.append("th { background-color: #f2f2f2; }");
        htmlBody.append("</style></head><body>");

        // Agregar el nombre del cliente encima de la tabla
        htmlBody.append("<p style='font-size: 18px; font-weight: bold;'>Cliente: ").append(cliente).append("</p>");

        htmlBody.append("<p style='font-size: 18px; font-weight: bold;'>Tipo de pedido: ").append(tipoPedido).append("</p>");

        // Tabla para mostrar la información del pedido
        htmlBody.append("<table>");
        htmlBody.append("<tr><th>Producto</th><th>Cantidad</th><th>S/. Total</th></tr>");

        // Agregar filas con la información de cada producto en el pedido
        for (ShoppingCart item : pedido) {
            htmlBody.append("<tr>");
            htmlBody.append("<td>").append(item.getNameProducto()).append("</td>");
            htmlBody.append("<td>").append(item.getQuantity()).append("</td>");
            htmlBody.append("<td>").append(item.getTotal()).append("</td>");
            htmlBody.append("</tr>");
        }

        htmlBody.append("</table>");

        // Agregar el total general debajo de la tabla
        htmlBody.append("<p style='font-size: 18px; font-weight: bold;'>Total General: ").append(totalGeneral).append("</p>");

        htmlBody.append("</body></html>");
        return htmlBody;
    }

}
