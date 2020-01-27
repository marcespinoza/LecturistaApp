package com.lecturista.app.Presentador;

import android.graphics.Bitmap;

import com.lecturista.app.Interface.LectorInterface;
import com.lecturista.app.Modelo.LectorModelo;
import com.lecturista.app.POJO.Reading;
import com.lecturista.app.Vista.LectorActivity;

import java.util.ArrayList;

public class LectorPresentador implements LectorInterface.LecturaPresentador {

    public LectorInterface.LecturaModelo lModelo;
    public LectorInterface.LecturaVista lVista;

    public LectorPresentador(LectorActivity lVista) {
        this.lVista = lVista;
        lModelo = new LectorModelo(this);
    }

    @Override
    public void enviarDatos(Bitmap image, String textoReconocido, boolean rewrite, String id_rewrite, String id_affiliate) {
        lModelo.grabarDatos(image, textoReconocido, rewrite, id_rewrite, id_affiliate);
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
