package com.lecturista.app.Presentador;

import com.lecturista.app.Interface.LoggedInterface;
import com.lecturista.app.Modelo.LoggedModelo;
import com.lecturista.app.Vista.LoggedActivity;

public class LoggedPresentador implements LoggedInterface.LoggedPresentador {

    private LoggedInterface.LoggedVista lVista;
    private LoggedInterface.LoggedModelo lModelo;

    public LoggedPresentador(LoggedActivity lVista) {
        this.lVista =lVista;
        this.lModelo = new LoggedModelo(this);
    }

    @Override
    public void cerrarSession() {
        lModelo.cerrarSesion();
    }

    @Override
    public void cerrarActivity() {
        lVista.finishActivity();
    }
}
