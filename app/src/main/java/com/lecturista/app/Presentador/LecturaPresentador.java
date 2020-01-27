package com.lecturista.app.Presentador;

import com.lecturista.app.Interface.LecturaInterface;
import com.lecturista.app.Modelo.LecturaModelo;
import com.lecturista.app.POJO.Reading;

import java.util.ArrayList;

public class LecturaPresentador implements LecturaInterface.LecturaPresentador {

    LecturaInterface.LecturaModelo lModelo;
    LecturaInterface.LecturaVista lVista;

    public LecturaPresentador(LecturaInterface.LecturaVista lVista) {
        this.lVista = lVista;
        lModelo = new LecturaModelo(this);
    }

    @Override
    public void obtenerLecturas() {
        lModelo.lecturas();
    }

    @Override
    public void retornarLecturas(ArrayList<Reading> lreading) {
        lVista.cargarLecturas(lreading);
    }

    @Override
    public void mostrarMensaje(String sin_lecturas) {

    }
}
