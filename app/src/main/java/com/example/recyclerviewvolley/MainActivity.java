package com.example.recyclerviewvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText usuario;
    EditText contrasena;
    Button iniciar;

    private String url = "http://192.168.1.37/auth.php";

    private String user;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciar = (Button) findViewById(R.id.btnIniciar);

        usuario = (EditText) findViewById(R.id.txtUser);
        contrasena = (EditText) findViewById(R.id.txtPasswd);

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }
    private void login() {

        String userLogin = usuario.getText().toString().trim();
        String passwdLogin = contrasena.getText().toString().trim();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(userLogin.equals("admin") && passwdLogin.equals("admin")){
                    Toast.makeText(MainActivity.this, "Credenciales correctos", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, Inicio.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "Los datos son incorrectos, pruebe con admin admin", Toast.LENGTH_SHORT).show();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user", userLogin);
                params.put("passwd", passwdLogin);

                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }
}
