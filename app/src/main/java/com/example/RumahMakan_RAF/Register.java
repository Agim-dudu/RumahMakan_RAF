package com.example.RumahMakan_RAF;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    //Deklarasi Variabel:
    TextInputEditText editTextEmail, editTextPassword;
    Button buttonReg;
    FirebaseAuth mAuth;

    android.widget.ProgressBar ProgressBar;
    android.widget.TextView TextView;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inisialisasi FirebaseAuth dan Views
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        buttonReg = findViewById(R.id.btn_register);
        ProgressBar = findViewById(R.id.ProgressBar);
        TextView = findViewById(R.id.LoginNow);

        // Memberikan listener pada TextView (Login Now)
        TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });

        // Memberikan listener pada Button (Register)
        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //menampilkan loading
                ProgressBar.setVisibility(View.VISIBLE);

                // Menampilkan ProgressBar
                String email, password;
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();

                // Memeriksa apakah email kosong
                if (TextUtils.isEmpty(email)){
                    ProgressBar.setVisibility(View.GONE);
                    Toast.makeText(Register.this, "Masukkan Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Memeriksa apakah password kosong
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(Register.this, "Masukkan Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Membuat akun baru menggunakan email dan password menggunakan FirebaseAuth
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Register.this, "Akun berhasil dibuat.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),Login.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Register.this, "Gagal Membuat Akun.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }
}