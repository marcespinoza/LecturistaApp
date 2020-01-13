package com.lecturista.app.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
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
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class ClienteActivity extends AppCompatActivity implements ClienteInterface.LoggedVista, Logout.DialogCallback, ClienteAdapter.ClienteInterface {

    @BindView(R.id.user) TextView usuario;
    private ClienteInterface.LoggedPresentador lPresentador;
    @BindView(R.id.recycler_cliente)
    RecyclerView recyclerView;
    @BindView(R.id.editextcliente)
    EditText campobuscar;
    @BindView(R.id.opciones_busqueda)
    RadioGroup radioGroup;
    ClienteAdapter cAdapter;
    CustomProgressDialog customProgressDialog;
    String criterioBusqueda;
    @BindView(R.id.iniciarmedicion)
    MaterialButton iniciarmedicion;
    Cliente cliente;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cliente_activity);
        ButterKnife.bind(this);
        customProgressDialog = new CustomProgressDialog();
        Intent intent = getIntent();
        criterioBusqueda = "name";
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

    @OnClick(R.id.iniciarmedicion)
    public void startLectorActivity(){
        Intent intent = new Intent(this, LectorActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("cliente", cliente);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @OnClick(R.id.searchbutton)
    public void buscarCliente(){
        if(!campobuscar.getText().equals("")){
            customProgressDialog.show(this, "Buscando clientes");
            lPresentador.buscarCliente(campobuscar.getText().toString(), criterioBusqueda);
        }
    }

    @OnCheckedChanged({R.id.radio_id, R.id.radio_nombre, R.id.radio_direccion})
    public void onRadioClicked(RadioButton radioButton){
        boolean checked = radioButton.isChecked();
        campobuscar.setText("");
        iniciarmedicion.setEnabled(false);
        if (cAdapter!=null) cAdapter.clear();
        switch (radioButton.getId()) {
            case R.id.radio_id:
                if (checked) {
                    criterioBusqueda = "client_id";
                }
                break;
            case R.id.radio_nombre:
                if (checked) {
                    criterioBusqueda = "name";
                }
                break;
            case R.id.radio_direccion:
                if (checked) {
                    criterioBusqueda = "address";
                }
                break;
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
        customProgressDialog.ocultar();
    }

    @Override
    public void mostrarClientes(ArrayList<Cliente> lclientes) {
        customProgressDialog.ocultar();
        cAdapter = new ClienteAdapter(lclientes, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cAdapter);
    }

    @Override
    public void clickedPositive() {
         lPresentador.cerrarSession();
    }

    @Override
    public void onClienteSelected(Cliente cliente) {
        this.cliente = cliente;
        iniciarmedicion.setEnabled(true);
    }
}
