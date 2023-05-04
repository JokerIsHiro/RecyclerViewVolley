package com.example.recyclerviewvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText usuario;
    EditText contrasena;
    Button iniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciar = (Button)findViewById(R.id.btnIniciar);

        usuario = (EditText)findViewById(R.id.txtUser);
        contrasena = (EditText)findViewById(R.id.txtPasswd);

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(usuario.getText().toString().equals("admin") && contrasena.getText().toString().equals("admin")){ //Comprobar que los datos sean correctos, si hay un espacio en blanco es detectado como fallo
                    Intent irMenu = new Intent(MainActivity.this, Inicio.class);
                    startActivity(irMenu);
                }else{
                    Toast.makeText(getApplicationContext(), "El usuario y la contraseña son admin", Toast.LENGTH_SHORT).show(); //Saldrá si el usuario se equivoca al introducir los datos
                }
            }
        });
    }
}