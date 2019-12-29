package com.lecturista.app.Presentador;

import com.lecturista.app.Interface.LoginInterface;
import com.lecturista.app.Modelo.LoginModelo;
import com.lecturista.app.Vista.LoginActivity;

import java.security.MessageDigest;

public class LoginPresentador implements LoginInterface.Presentador {

    private LoginInterface.Modelo lModelo;
    private LoginInterface.Vista lVista;

    public LoginPresentador(LoginActivity lVista) {
        this.lVista = lVista;
        lModelo = new LoginModelo(this);
    }

    @Override
    public void enviarLogin(String usuario, String password) {
        lModelo.verificarLogin(usuario, getSHA256(password));
    }

    @Override
    public void loginCorrecto() {
        lVista.loginCorrecto();
    }

    @Override
    public void mostrarError(String s) {
        lVista.mostrarError(s);
    }

    public static String getSHA256(String data){
        StringBuffer sb = new StringBuffer();
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(data.getBytes());
            byte byteData[] = md.digest();

            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }


}
