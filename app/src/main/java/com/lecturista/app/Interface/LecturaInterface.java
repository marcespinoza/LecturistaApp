package com.lecturista.app.Interface;

import android.graphics.Bitmap;

public interface LecturaInterface {

    interface LecturaPresentador{
        void enviarDatos(Bitmap image, String textoReconocido);
        void mostrarMensaje(String mensaje);
        void errorGrabacion(String mensaje);
        void grabacionExitosa();
    }
    interface LecturaModelo{
        void grabarDatos(Bitmap image, String textoReconocido);

    }
    interface LecturaVista{
        void error(String mensaje);
        void exito();
    }

}
