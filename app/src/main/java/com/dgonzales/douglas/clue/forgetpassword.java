package com.dgonzales.douglas.clue;

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
import com.google.firebase.auth.FirebaseAuth;

public class forgetpassword extends AppCompatActivity {
    private EditText inputemail;
    private Button btnReset;
    private FirebaseAuth mAuth;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        //inicializando
        inputemail = (EditText)findViewById(R.id.resetEmail);
        btnReset = (Button)findViewById(R.id.btnreset);
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar4);
        //inicializando firebase
        mAuth = FirebaseAuth.getInstance();
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputemail.getText().toString().trim();
                //si no ingresa el email
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Ingrese su Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                mProgressBar.setVisibility(View.VISIBLE);
                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(forgetpassword.this, "Se ha enviado las intrucciones a su email!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(forgetpassword.this, "Fallo el envio de las intrucciones a su email", Toast.LENGTH_SHORT).show();
                                }

                                mProgressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }
}
