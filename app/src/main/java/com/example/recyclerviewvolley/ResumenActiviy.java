package com.example.recyclerviewvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ResumenActiviy extends AppCompatActivity {
    TextView nombreResumen;
    TextView apellidosResumen;
    TextView temperaturaResumen;
    TextView ciudadResumen;
    TextView provinciaResumen;
    Button color;
    Button volverMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);

        nombreResumen = (TextView) findViewById(R.id.txtNombreResumen);
        apellidosResumen = (TextView) findViewById(R.id.txtApellidosResumen);
        temperaturaResumen = (TextView) findViewById(R.id.txtTemperaturaResumen);
        ciudadResumen = (TextView) findViewById(R.id.txtCiudadResumen);
        provinciaResumen = (TextView) findViewById(R.id.txtProvinciaResumen);

        color = (Button) findViewById(R.id.colorChanger);
        volverMenu = (Button) findViewById(R.id.btnVolver);

        datos();

        volverMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResumenActiviy.this, Inicio.class);
                startActivity(intent);
            }
        });
    }
    private void datos(){

        SharedPreferences sharedPreferences = getSharedPreferences("ID", 0);

        int id = sharedPreferences.getInt("id",0);


        String url = "http://192.168.1.37/listid.php?id="+id;

        JsonObjectRequest jsonArrayRequest=new JsonObjectRequest (Request.Method.GET, url, null, (Response.Listener<JSONObject>) response -> {
            try {
                System.out.println(response);
                System.out.println(id);
                JSONObject object = (JSONObject) response.getJSONArray("temp").get(0);
                Users user = new Users();
                nombreResumen.setText(object.getString("nombre"));
                apellidosResumen.setText(object.getString("apellidos"));
                ciudadResumen.setText(object.getString("ciudad"));
                provinciaResumen.setText(object.getString("provincia"));
                temperaturaResumen.setText(String.valueOf(object.getInt("temperatura")));
                if(object.getInt("format")==1 && object.getInt("temperatura")<38 || object.getInt("format")==2 && object.getInt("temperatura")<100){
                    color.setBackgroundColor(Color.parseColor("#008f39"));
                }else {
                    color.setBackgroundColor(Color.parseColor("#cc0605"));
                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}