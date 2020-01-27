package com.lecturista.app.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturista.app.POJO.Reading;
import com.lecturista.app.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class LecturasAdapter extends RecyclerView.Adapter<LecturasAdapter.LecturaHolder> {

    ArrayList<Reading> lreadings;
    SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
    public RewriteReading rReading;

    public interface RewriteReading{
        void onClickReading(Reading id_reqrite);
    }

    public LecturasAdapter(ArrayList<Reading> lreadings, RewriteReading rReading) {
        this.lreadings = lreadings;
        this.rReading = rReading;
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
        holder.createdat.setText(output.format(lreadings.get(position).getCreated_at()));
        holder.affiliate.setText(lreadings.get(position).getAffiliate_id());
        holder.name.setText(lreadings.get(position).getName());
        holder.address.setText(lreadings.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return lreadings.size();
    }

    public class LecturaHolder extends RecyclerView.ViewHolder {

        TextView reading;
        TextView createdat;
        TextView affiliate;
        TextView name;
        TextView address;

        public LecturaHolder(@NonNull View itemView) {
            super(itemView);
            reading = itemView.findViewById(R.id.reading);
            createdat = itemView.findViewById(R.id.created_at);
            affiliate = itemView.findViewById(R.id.affiliate);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    rReading.onClickReading(lreadings.get(getAdapterPosition()));
                    return false;
                }
            });
        }
    }

}
