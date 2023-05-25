package com.example.recyclerviewvolley;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    private Context context;
    private List<Users> list;
    private View.OnClickListener onClickListener;

    int id;
    String nombre;
    String apellidos;
    String ciudad;
    String provincia;
    int temperatura;
    int format;


    public UserAdapter(Context context, List<Users> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Users user = list.get(position);

        holder.textNombre.setText(user.getNombre()+" "+user.getApellidos());
        holder.textCiudad.setText(user.getCiudad()+" "+user.getProvincia());
        holder.textTemperatura.setText(String.valueOf(user.getTemperatura()));

        id = holder.itemView.getId();

        if(user.getFormat()==1 && user.getTemperatura()<38 || user.getFormat()==2 && user.getTemperatura()<100){
            holder.textTemperatura.setTextColor(Color.parseColor("#008f39"));
        }else{
            holder.textTemperatura.setTextColor(Color.parseColor("#cc0605"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Users user = list.get(holder.getAdapterPosition());

                SharedPreferences sharedPreferences = context.getSharedPreferences("ID",0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putInt("id", user.getId());
                editor.putString("nombre",user.getNombre());
                editor.putString("apellidos", apellidos);
                editor.putString("ciudad", ciudad);
                editor.putString("provincia", provincia);
                editor.putInt("temperatura",temperatura);
                editor.putInt("format",format);
                editor.apply();

                Intent intent = new Intent(context, ResumenActiviy.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = (View.OnClickListener) onClickListener;
    }

    public interface OnClickListener {
        void onClick(int position, Users user);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textNombre, textCiudad, textTemperatura;

        public ViewHolder(View itemView) {
            super(itemView);

            textNombre = itemView.findViewById(R.id.user_nombre);
            textCiudad = itemView.findViewById(R.id.user_ciudad);
            textTemperatura = itemView.findViewById(R.id.user_temperatura);
        }
    }
}