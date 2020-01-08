package com.lecturista.app.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturista.app.POJO.Cliente;
import com.lecturista.app.R;

import java.util.ArrayList;
import java.util.List;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ViewHolder> {

    List<Cliente> lClientes;

    public ClienteAdapter(ArrayList<Cliente> lClientes) {
        this.lClientes=lClientes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cliente, parent, false);
        return new ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.idcliente.setText(lClientes.get(position).getOriginal_id());
        holder.nombre.setText(lClientes.get(position).getName());
        holder.direccion.setText(lClientes.get(position).getAddress());
    }


    @Override
    public int getItemCount() {
        return lClientes.size();
    }

    public void clear(){
        if(lClientes!=null){
           lClientes.clear();
           notifyDataSetChanged();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {

        private TextView idcliente, nombre, direccion;

        public ViewHolder(View view) {
            super(view);
            idcliente = view.findViewById(R.id.idcliente);
            nombre = view.findViewById(R.id.nombrecliente);
            direccion = view.findViewById(R.id.direccioncliente);
        }

    }

}
