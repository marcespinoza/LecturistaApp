package com.lecturista.app.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lecturista.app.Adapter.LecturasAdapter;
import com.lecturista.app.Helper.ProgressDialog;
import com.lecturista.app.Interface.LecturaInterface;
import com.lecturista.app.POJO.Reading;
import com.lecturista.app.Presentador.LecturaPresentador;
import com.lecturista.app.R;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LecturaActivity extends AppCompatActivity implements LecturaInterface.LecturaVista, LecturasAdapter.RewriteReading {

    @BindView(R.id.swipe_lecturas) SwipeRefreshLayout swipeRefreshLayout;
    LecturaInterface.LecturaPresentador lPresentador;
    LecturasAdapter lAdapter;
    @BindView(R.id.recycler_lecturas)  RecyclerView recyclerView;
    String id_rewrite = "";
    boolean rewrite = false;
    Reading reading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lectura_activity);
        ButterKnife.bind(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ultimasLecturas();
            }
        });
        lPresentador = new LecturaPresentador(this);
        ultimasLecturas();
    }

    public void ultimasLecturas(){
        swipeRefreshLayout.setRefreshing(true);
        lPresentador.obtenerLecturas();
    }

    @Override
    public void cargarLecturas(ArrayList<Reading> lreading) {
        swipeRefreshLayout.setRefreshing(false);
        lAdapter = new LecturasAdapter(lreading, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(lAdapter);
    }

    @Override
    public void error(String mensaje) {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_SHORT).show();
    }

    public void startLectorActivity(){
        Intent intent = new Intent(this, LectorActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("reading", reading);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onClickReading(Reading reading) {
        this.reading = reading;
        rewrite =  true;
        this.id_rewrite = reading.getId_rewrite();
        startLectorActivity();
    }

}
