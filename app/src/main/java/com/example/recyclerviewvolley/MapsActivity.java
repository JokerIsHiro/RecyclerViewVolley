package com.example.recyclerviewvolley;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.recyclerviewvolley.databinding.ActivityMapsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String url = "http://192.168.1.37/list.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        JsonObjectRequest jsonArrayRequest=new JsonObjectRequest (Request.Method.GET, url, null, (Response.Listener<JSONObject>) response -> {
            try {
                JSONArray array = response.getJSONArray("temp");
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> addresses = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    String ciudad = array.getJSONObject(i).getString("ciudad");
                    addresses.addAll(geocoder.getFromLocationName(ciudad, 1));
                    try {

                        JSONObject jsonObject=array.getJSONObject(i);

                        if(addresses.size()>i){
                            double latitud = addresses.get(i).getLatitude();
                            double longitud = addresses.get(i).getLongitude();

                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(latitud, longitud))
                                    .title(jsonObject.getString("ciudad")));
                        }

                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.0882365,-8.9971987), 6.0f));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
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