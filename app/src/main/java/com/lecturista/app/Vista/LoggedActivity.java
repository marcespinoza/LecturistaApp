package com.lecturista.app.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.lecturista.app.Interface.LoggedInterface;
import com.lecturista.app.Presentador.LoggedPresentador;
import com.lecturista.app.R;
import com.lecturista.app.Vista.Dialog.Logout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoggedActivity extends AppCompatActivity implements LoggedInterface.LoggedVista, Logout.DialogCallback {

    @BindView(R.id.user) TextView usuario;
    private LoggedInterface.LoggedPresentador lPresentador;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buttons_activity);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (null != intent) { //Null Checking
            String usr= intent.getStringExtra("usuario");
            usuario.setText(usr);
        }
        lPresentador = new LoggedPresentador(this);
    }

    @OnClick(R.id.logout)
    void logout(){
            FragmentManager fm = getSupportFragmentManager();
            Logout logout = Logout.newInstance("Cerrar sesion");
            logout.show(fm, "logout");
    }

    @Override
    public void finishActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void clickedPositive() {
         lPresentador.cerrarSession();
    }
}
