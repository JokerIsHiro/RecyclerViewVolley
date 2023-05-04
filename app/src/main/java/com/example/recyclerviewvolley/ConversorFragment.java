package com.example.recyclerviewvolley;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ConversorFragment extends Fragment {

    double celsius, fahre, resultFahre, resultCelsius;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conversor, container, false);

        EditText celsiusConv = (EditText) view.findViewById(R.id.txtConvCelsius);
        EditText fahreConv = (EditText) view.findViewById(R.id.txtConvFahre);
        TextView resultadoFahre = (TextView) view.findViewById(R.id.txtResultadoFahre);
        TextView resultadoCelsius = (TextView) view.findViewById(R.id.txtResultadoCel);
        Button calcularFahre = (Button) view.findViewById(R.id.btnCelsiusFahre);
        Button calcularCelsius = (Button) view.findViewById(R.id.btnFahreCelsius);

        calcularFahre.setOnClickListener(new View.OnClickListener() {//Al presionar el boton calcular hará la operación
            @Override
            public void onClick(View view) {
                celsius = Double.valueOf(celsiusConv.getText().toString());
                resultFahre = (celsius * 9 / 5) + 32; //Formula para pasar de Celsius a Fahrenheit
                resultadoFahre.setText(String.valueOf(resultFahre));
            }
        });
        calcularCelsius.setOnClickListener(new View.OnClickListener() { //Al presionar el boton calcular hará la operación
            @Override
            public void onClick(View view) {
                fahre = Double.valueOf(fahreConv.getText().toString());
                resultCelsius = (fahre - 32) * 5 / 9; // Formula para pasar de Fahrenheit a Celsis
                resultadoCelsius.setText(String.valueOf(resultCelsius));
            }
        });
        return view;
    }
}