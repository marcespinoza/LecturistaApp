package com.lecturista.app.Presentador;

import com.lecturista.app.Interface.LoginInterface;
import com.lecturista.app.Modelo.LoginModelo;
import com.lecturista.app.Vista.LoginActivity;

public class LoginPresentador implements LoginInterface.Presentador {

    private LoginInterface.Modelo lModelo;
    private LoginInterface.Vista lVista;

    public LoginPresentador(LoginActivity lVista) {
        this.lVista = lVista;
        lModelo = new LoginModelo(this);
    }

    @Override
    public void enviarLogin(String usuario, String password) {
        lModelo.verificarLogin(usuario, password);
    }

    @Override
    public void loginCorrecto(String usuario) {
        lVista.loginCorrecto(usuario);
    }

    @Override
    public void mostrarMensaje(String s) {
        lVista.mostrarMensaje(s);
    }


    @Override
    public void checkLogin() {
        lModelo.checkLogin();
    }

    @Override
    public void returnlogin(boolean logged, String usuario) {
        if(logged){
            lVista.startClienteActivity(usuario);
        }else{
            lVista.mostrarMensaje("Token invalido");
        }
    }

    @Override
    public void finishDialog() {
        lVista.ocultarDialog();
    }


}
