package com.lecturista.app.Presentador;

import android.graphics.Bitmap;

import com.lecturista.app.Interface.LecturaInterface;
import com.lecturista.app.Modelo.LecturaModelo;
import com.lecturista.app.Vista.LectorActivity;

public class LecturaPresentador implements LecturaInterface.LecturaPresentador {

    public LecturaInterface.LecturaModelo lModelo;
    public LecturaInterface.LecturaVista lVista;

    public LecturaPresentador(LectorActivity lVista) {
        this.lVista = lVista;
        lModelo = new LecturaModelo(this);
    }

    @Override
    public void enviarDatos(Bitmap image, String textoReconocido) {
        lModelo.grabarDatos(image, textoReconocido);
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        lVista.error(mensaje);
    }

    @Override
    public void errorGrabacion(String mensaje) {
        lVista.error(mensaje);
    }

    @Override
    public void grabacionExitosa() {
        lVista.exito();
    }

}
