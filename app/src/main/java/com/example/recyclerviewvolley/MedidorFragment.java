package com.example.recyclerviewvolley;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MedidorFragment extends Fragment {

    EditText txtNombre;
    EditText txtApellidos;
    EditText txtTemperatura;
    EditText txtCiudad;
    EditText txtProvincia;
    RadioGroup grupo;
    RadioButton rbCelsius;
    RadioButton rbFahre;
    Button btnFinalizar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medidor, container, false);

        txtNombre = (EditText)view.findViewById(R.id.txtNombre);
        txtApellidos = (EditText)view.findViewById(R.id.txtApellidos);
        txtTemperatura = (EditText)view.findViewById(R.id.txtTemperatura);
        txtCiudad = (EditText)view.findViewById(R.id.txtCiudad);
        txtProvincia = (EditText)view.findViewById(R.id.txtProvincia);
        grupo = (RadioGroup)view.findViewById(R.id.radioGrupo);
        rbCelsius = (RadioButton)view.findViewById(R.id.rbCelsius);
        rbFahre = (RadioButton)view.findViewById(R.id.rbFahre);
        btnFinalizar = (Button)view.findViewById(R.id.btnFinalizar);

        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtNombre.getText().toString().isEmpty() && !txtApellidos.getText().toString().isEmpty() && !txtTemperatura.getText().toString().isEmpty()
                        && !txtCiudad.getText().toString().isEmpty() && !txtProvincia.getText().toString().isEmpty() && rbCelsius.isChecked()){
                    enviar();

                } else if (!txtNombre.getText().toString().isEmpty() && !txtApellidos.getText().toString().isEmpty() && !txtTemperatura.getText().toString().isEmpty()
                        && !txtCiudad.getText().toString().isEmpty() && !txtProvincia.getText().toString().isEmpty() && rbFahre.isChecked()){
                    enviar();
                }

                if(txtNombre.getText().toString().isEmpty() && txtApellidos.getText().toString().isEmpty()
                        && txtTemperatura.getText().toString().isEmpty() && txtCiudad.getText().toString().isEmpty()
                        && txtProvincia.getText().toString().isEmpty()){ //Comprobación de que ninguno de los campos estén vacíos
                    Toast.makeText(view.getContext().getApplicationContext(), "Rellene los campos", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(view.getContext().getApplicationContext(), "Creado", Toast.LENGTH_SHORT).show();
                }
            }
            private void enviar() {
                String url = "http://192.168.1.37/temp.php";

                final String nombre = txtNombre.getText().toString().trim();
                final String apellidos = txtApellidos.getText().toString().trim();
                final int temperatura = Integer.parseInt(txtTemperatura.getText().toString().trim());
                final String ciudad = txtCiudad.getText().toString().trim();
                final String provincia = txtProvincia.getText().toString().trim();
                final int format;
                if(rbCelsius.isChecked()){
                    format = 1;
                }else{
                    format = 2;
                }
                RequestQueue queue = Volley.newRequestQueue(view.getContext());


                StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject respObj = new JSONObject(response);

                            Users user = new Users(
                                    respObj.getString("nombre"),
                                    respObj.getString("apellidos"),
                                    respObj.getInt("temperatura"),
                                    respObj.getString("ciudad"),
                                    respObj.getString("provincia"),
                                    respObj.getInt("format")
                            );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(view.getContext(), "Fallo al obtener respuesta = " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("nombre", nombre);
                        params.put("apellidos", apellidos);
                        params.put("temperatura", String.valueOf(temperatura));
                        params.put("ciudad", ciudad);
                        params.put("provincia", provincia);
                        params.put("format", String.valueOf(format));

                        return params;
                    }
                };
                queue.add(request);
            }
        });
        return view;
    }
}