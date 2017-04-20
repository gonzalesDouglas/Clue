package com.dgonzales.douglas.clue;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registro extends AppCompatActivity  {
    private EditText enterEmail,enterPassword,confirmPassword;
    private ProgressBar mProgressBar;
    private Button btnRegistro;
    private FirebaseAuth mAuth;

    @Override
    protected void onResume() {
        super.onResume();
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        // obtener instancia
        mAuth = FirebaseAuth.getInstance();
        //
        enterEmail = (EditText)findViewById(R.id.enterEmail);
        enterPassword = (EditText)findViewById(R.id.enterPassword);
        confirmPassword = (EditText)findViewById(R.id.confirmPassword);
        btnRegistro = (Button)findViewById(R.id.btnregistro);
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar3);
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = enterEmail.getText().toString().trim();
                String password = enterPassword.getText().toString().trim();
                String confPaswd = confirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Ingrese su email!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Ingrese su contraseña!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(confPaswd)) {
                    Toast.makeText(getApplicationContext(), "Confirme su contraseña!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Contraseña corta, ingrese minimo 6 caracteres!", Toast.LENGTH_SHORT).show();
                    return;
                }

                mProgressBar.setVisibility(View.VISIBLE);
                //create user
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(registro.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(registro.this, "Se creo el usuario con exito:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                mProgressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(registro.this, "Fallo la Autenticacion." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(registro.this, loginuser.class));
                                    finish();
                                }
                            }
                        });



            }
        });

    }

}
