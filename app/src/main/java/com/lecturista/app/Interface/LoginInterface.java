package com.lecturista.app.Interface;

public interface LoginInterface {

    interface Modelo{
        void verificarLogin(String usuario, String password);
        void checkLogin();
    }
    interface Presentador{
        void enviarLogin(String usuario, String password);
        void loginCorrecto(String usuario);
        void mostrarMensaje(String s);
        void checkLogin();
        void returnlogin(boolean logged, String usuario);
        void finishDialog();
    }
    interface Vista{
        void mostrarMensaje(String s);
        void loginCorrecto(String usuario);
        void startClienteActivity(String usuario);
        void ocultarDialog();
    }
}
