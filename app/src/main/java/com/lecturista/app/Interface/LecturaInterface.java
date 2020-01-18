package com.lecturista.app.Interface;

import android.graphics.Bitmap;

import com.lecturista.app.POJO.Reading;

import java.util.ArrayList;

public interface LecturaInterface {

    interface LecturaPresentador{
        void enviarDatos(Bitmap image, String textoReconocido);
        void mostrarMensaje(String mensaje);
        void errorGrabacion(String mensaje);
        void grabacionExitosa();
        void obtenerLecturas();
        void retornarLecturas(ArrayList<Reading> lreading);
    }
    interface LecturaModelo{
        void grabarDatos(Bitmap image, String textoReconocido);
        void lecturas();
    }
    interface LecturaVista{
        void error(String mensaje);
        void exito();
        void cargarLecturas(ArrayList<Reading> lreading);
    }

}
