package com.lecturista.app.Interface;

public interface LoginInterface {

    interface Modelo{
        void verificarLogin(String usuario, String password);
        void checkLogin();
    }
    interface Presentador{
        void enviarLogin(String usuario, String password);
        void loginCorrecto(String usuario);
        void mostrarError(String s);
        void checkLogin();
        void returnlogin(boolean logged, String usuario);
    }
    interface Vista{
        void mostrarError(String s);
        void loginCorrecto(String usuario);
        void startButtonActivity(String usuario);
    }
}
