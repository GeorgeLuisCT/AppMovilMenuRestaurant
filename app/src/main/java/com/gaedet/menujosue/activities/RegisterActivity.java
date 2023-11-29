package com.gaedet.menujosue.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.gaedet.menujosue.R;
import android.text.TextUtils;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText etNombre, etApellido, etEmail, etTelefono, etPassword, etDireccion;
    private Button buttonEnter;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inicializar Firebase Auth y Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();



        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etEmail = findViewById(R.id.etEmail);
        etTelefono = findViewById(R.id.etTelefono);
        etPassword = findViewById(R.id.etPassword);
        etDireccion = findViewById(R.id.etDireccion);

        buttonEnter = findViewById(R.id.buttonEnter);

        buttonEnter.setOnClickListener(view -> registerUser());
    }

    private void registerUser() {
        String nombre = etNombre.getText().toString().trim();
        String apellido = etApellido.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String direccion = etDireccion.getText().toString().trim();

        // Validación de campos
        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(apellido) || TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(telefono) || TextUtils.isEmpty(password) || TextUtils.isEmpty(direccion)) {
            Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear usuario en Firebase Auth
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Usuario registrado exitosamente en Firebase Auth
                        String userId = mAuth.getCurrentUser().getUid();

                        // Crear un documento para el usuario en Firestore
                        Map<String, Object> user = new HashMap<>();
                        user.put("nombre", nombre);
                        user.put("apellido", apellido);
                        user.put("email", email);
                        user.put("telefono", telefono);
                        user.put("direccion", direccion);

                        db.collection("usuarios").document(userId)
                                .set(user)
                                .addOnSuccessListener(aVoid -> {
                                    // Usuario registrado exitosamente en Firestore
                                    Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, ControlCenterActivity.class);
                                    startActivity(intent);
                                    finish(); // Cierra la actividad actual
                                })
                                .addOnFailureListener(e -> {
                                    // Error al registrar el usuario en Firestore
                                    Toast.makeText(RegisterActivity.this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
                                });

                    } else {
                        // Error al registrar el usuario en Firebase Auth
                        Toast.makeText(RegisterActivity.this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
