package com.lecturista.app.Vista;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.lecturista.app.Helper.ProgressDialog;
import com.lecturista.app.Interface.LoginInterface;
import com.lecturista.app.Presentador.LoginPresentador;
import com.lecturista.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginInterface.Vista {

    LoginInterface.Presentador presentador;

    @BindView(R.id.login_button)  MaterialButton login;
    @BindView(R.id.usuario) EditText usuario;
    @BindView(R.id.clave) EditText clave;
    ProgressBar progressBar;
    AlertDialog alertDialog;
    SharedPreferences sharedPref;
    MaterialButton enviarConexion;
    TextView errorEmpresa;
    EditText empresaText;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        pDialog = new ProgressDialog(this);
        ButterKnife.bind(this);
        presentador = new LoginPresentador(this);
        sharedPref = this.getSharedPreferences("datosesion",Context.MODE_PRIVATE);
        boolean sesion = sharedPref.getBoolean("sesion",false);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(usuario.getText().toString(), clave.getText().toString());
            }
        });
        checkLogin();
    }

    private void checkLogin(){
        presentador.checkLogin();
    }

    public void login(String usuario, String clave) {
        pDialog.showProgressDialog("Espere por favor...");
        presentador.enviarLogin(usuario, clave);
    }

    private void login(String usuario){
        startActivity(usuario);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void mostrarError(String s) {
        pDialog.finishDialog();
        Toast.makeText(this,s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginCorrecto(String usuario) {
        pDialog.finishDialog();
        login(usuario);
    }

    @Override
    public void startButtonActivity(String usuario) {
        startActivity(usuario);
    }

    public void startActivity(String usuario){
        Intent intent = new Intent(this, ButtonsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("usuario",usuario);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }


}
