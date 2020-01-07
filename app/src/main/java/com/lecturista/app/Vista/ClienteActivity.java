package com.lecturista.app.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturista.app.Adapter.ClienteAdapter;
import com.lecturista.app.Interface.ClienteInterface;
import com.lecturista.app.POJO.Cliente;
import com.lecturista.app.Presentador.ClientePresentador;
import com.lecturista.app.R;
import com.lecturista.app.Vista.Dialog.CustomProgressDialog;
import com.lecturista.app.Vista.Dialog.Logout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClienteActivity extends AppCompatActivity implements ClienteInterface.LoggedVista, Logout.DialogCallback {

    @BindView(R.id.user) TextView usuario;
    private ClienteInterface.LoggedPresentador lPresentador;
    @BindView(R.id.recycler_cliente)
    RecyclerView recyclerView;
    @BindView(R.id.editextcliente)
    EditText campobuscar;
    ClienteAdapter cAdapter;
    CustomProgressDialog customProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cliente_activity);
        ButterKnife.bind(this);
        customProgressDialog = new CustomProgressDialog();
        Intent intent = getIntent();
        if (null != intent) { //Null Checking
            String usr= intent.getStringExtra("usuario");
            usuario.setText(usr);
        }
        lPresentador = new ClientePresentador(this);
    }

    @OnClick(R.id.logout)
    void logout(){
            FragmentManager fm = getSupportFragmentManager();
            Logout logout = Logout.newInstance("Cerrar sesion");
            logout.show(fm, "logout");
    }


    @OnClick(R.id.searchbutton)
    public void buscarCliente(){
        if(!campobuscar.getText().equals("")){
            customProgressDialog.show(this, "Buscando clientes");
            lPresentador.buscarCliente(campobuscar.getText().toString());
        }
    }

    @Override
    public void finishActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void mostrarError() {

    }

    @Override
    public void mostrarClientes(ArrayList<Cliente> lclientes) {
        customProgressDialog.ocultar();
        cAdapter = new ClienteAdapter(lclientes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cAdapter);
    }

    @Override
    public void clickedPositive() {
         lPresentador.cerrarSession();
    }
}
