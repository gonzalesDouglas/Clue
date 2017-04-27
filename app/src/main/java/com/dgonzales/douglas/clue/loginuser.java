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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
public class loginuser extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private ProgressBar mProgressBar;
    private Button mButtonLogin;
    private EditText textEmail,textPasswd;
    private TextView recupaswwd,registrotext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get Firebase auth instance
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(loginuser.this, Dashboard.class));
            finish();
        }
        setContentView(R.layout.activity_loginuser);


        //buscamos los botones
        mButtonLogin = (Button) findViewById(R.id.btnlogin);
        textEmail = (EditText) findViewById(R.id.txtemail);
        textPasswd = (EditText) findViewById(R.id.txtpasswd);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar2);
        recupaswwd = (TextView)findViewById(R.id.textolvidopasswd);
        registrotext = (TextView)findViewById(R.id.textregistro);
        mAuth = FirebaseAuth.getInstance();
        mButtonLogin.setOnClickListener(this);
        registrotext.setOnClickListener(this);
        recupaswwd.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnlogin:
                String email = textEmail.getText().toString();
                final String password = textPasswd.getText().toString();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(this, "Ingrese el email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(this, "Ingrese el password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mProgressBar.setVisibility(View.VISIBLE);
                //autenticar el usuario

                mAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(loginuser.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                mProgressBar.setVisibility(View.GONE);

                                if (!task.isSuccessful()){
                                    //hay un error
                                    if (password.length()<6){
                                        textPasswd.setError(getString(R.string.minimopassword));
                                    }else {
                                        Toast.makeText(loginuser.this,getString(R.string.falloAtenticacion), Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Intent intent = new Intent(loginuser.this,Dashboard.class);
                                    startActivity(intent);
                                    finish();
                                }

                            }
                        });

                break;
            case  R.id.textregistro:
                startActivity(new Intent(loginuser.this,registro.class));
                finish();
                break;
            case R.id.textolvidopasswd:
                startActivity(new Intent(loginuser.this,forgetpassword.class));
                finish();
                break;
        }
    }
}
