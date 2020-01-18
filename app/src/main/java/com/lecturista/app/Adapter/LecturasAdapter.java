package com.lecturista.app.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturista.app.POJO.Reading;
import com.lecturista.app.R;

import java.util.ArrayList;

public class LecturasAdapter extends RecyclerView.Adapter<LecturasAdapter.LecturaHolder> {

    ArrayList<Reading> lreadings;

    public LecturasAdapter(ArrayList<Reading> lreadings) {
        this.lreadings = lreadings;
    }

    @NonNull
    @Override
    public LecturaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lectura, parent, false);
        return new LecturaHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(@NonNull LecturaHolder holder, int position) {
        holder.reading.setText(lreadings.get(position).getReading());
        holder.createdat.setText(lreadings.get(position).getCreated_at());
    }

    @Override
    public int getItemCount() {
        return lreadings.size();
    }

    public class LecturaHolder extends RecyclerView.ViewHolder {

        TextView reading;
        TextView createdat;

        public LecturaHolder(@NonNull View itemView) {
            super(itemView);
            reading = itemView.findViewById(R.id.reading);
            createdat = itemView.findViewById(R.id.created_at);
        }
    }

}
