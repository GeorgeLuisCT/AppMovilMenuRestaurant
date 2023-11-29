package com.gaedet.menujosue.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.gaedet.menujosue.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {


    private TextInputEditText etEmail, etPassword;
    private Button buttonEnter;
    private TextView txtRegistrar;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        buttonEnter = findViewById(R.id.buttonEnter);
        txtRegistrar = findViewById(R.id.txtRegistrar);

        buttonEnter.setOnClickListener(view -> loginUser());
        txtRegistrar.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Inicio de sesión exitoso
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // Obtener datos adicionales del usuario desde Firestore
                            String userId = user.getUid();
                            getUserDataFromFirestore(userId);
                        }
                    } else {
                        // Error en el inicio de sesión
                        Toast.makeText(LoginActivity.this, "Error en el inicio de sesión", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getUserDataFromFirestore(String userId) {
        db.collection("usuarios").document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Usuario encontrado en Firestore
                            String nombre = document.getString("nombre");
                            String apellido = document.getString("apellido");
                            String email = document.getString("email");
                            String telefono = document.getString("telefono");
                            String direccion = document.getString("direccion");

                            SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("nombre", nombre);
                            editor.putString("apellido", apellido);
                            editor.putString("email", email);
                            editor.putString("telefono", telefono);
                            editor.putString("direccion", direccion);

                            editor.apply();

                            // Redirigir a la actividad principal
                            Intent intent = new Intent(LoginActivity.this, ControlCenterActivity.class);
                            startActivity(intent);
                            finish(); // Cierra la actividad actual
                        } else {
                            // No se encontraron datos en Firestore
                            Toast.makeText(LoginActivity.this, "No se encontraron datos del usuario", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Error al obtener datos de Firestore
                        Toast.makeText(LoginActivity.this, "Error al obtener datos del usuario", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
