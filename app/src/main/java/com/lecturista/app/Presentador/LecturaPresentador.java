package com.lecturista.app.Presentador;

import android.graphics.Bitmap;

import com.lecturista.app.Interface.LecturaInterface;
import com.lecturista.app.Modelo.LecturaModelo;
import com.lecturista.app.POJO.Reading;
import com.lecturista.app.Vista.LectorActivity;

import java.util.ArrayList;

public class LecturaPresentador implements LecturaInterface.LecturaPresentador {

    public LecturaInterface.LecturaModelo lModelo;
    public LecturaInterface.LecturaVista lVista;

    public LecturaPresentador(LectorActivity lVista) {
        this.lVista = lVista;
        lModelo = new LecturaModelo(this);
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

    @Override
    public void obtenerLecturas() {
        lModelo.lecturas();
    }

    @Override
    public void retornarLecturas(ArrayList<Reading> lreading) {
        lVista.cargarLecturas(lreading);
    }

}
