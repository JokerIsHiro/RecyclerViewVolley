package com.example.recyclerviewvolley;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListadoFragment extends Fragment {

    private RecyclerView uList;

    private LinearLayoutManager linearLayoutManager;
    private List<Users> usersList;
    private RecyclerView.Adapter adapter;

    private String url = "http://192.168.1.37/list.php";

    public ListadoFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_listado, container, false);

        uList = view.findViewById(R.id.recycler_view);

        usersList = new ArrayList<>();

        adapter = new UserAdapter(view.getContext(), usersList);

        linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.getOrientation();
        uList.setHasFixedSize(true);
        uList.setLayoutManager(linearLayoutManager);
        uList.setAdapter(adapter);

        GetData();

        return view;
    }
    private void GetData() {

        JsonObjectRequest jsonArrayRequest=new JsonObjectRequest (Request.Method.GET, url, null, (Response.Listener<JSONObject>) response -> {
            try {
                JSONArray array = response.getJSONArray("temp");
                for (int i = 0; i < array.length(); i++) {
                    try {
                        Users user = new Users();
                        user.setNombre(array.getJSONObject(i).getString("nombre"));
                        user.setApellidos(array.getJSONObject(i).getString("apellidos"));
                        user.setCiudad(array.getJSONObject(i).getString("ciudad"));
                        user.setProvincia(array.getJSONObject(i).getString("provincia"));
                        user.setTemperatura(array.getJSONObject(i).getInt("temperatura"));
                        user.setFormat(array.getJSONObject(i).getInt("format"));

                        usersList.add(user);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapter.notifyDataSetChanged();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }
}