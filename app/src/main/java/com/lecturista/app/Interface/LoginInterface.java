package com.lecturista.app.Interface;

public interface LoginInterface {

    interface Modelo{
        void verificarLogin(String usuario, String password);
    }
    interface Presentador{
        void enviarLogin(String usuario, String password);
        void loginCorrecto();
        void mostrarError(String s);
    }
    interface Vista{
        void mostrarError(String s);
        void loginCorrecto();
    }
}
