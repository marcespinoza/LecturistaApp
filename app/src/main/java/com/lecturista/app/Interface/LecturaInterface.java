package com.lecturista.app.Interface;

import com.lecturista.app.POJO.Reading;

import java.util.ArrayList;

public interface LecturaInterface {

    interface LecturaModelo{
        void lecturas();
    }

    interface LecturaPresentador{
        void obtenerLecturas();
        void retornarLecturas(ArrayList<Reading> lreading);
        void mostrarMensaje(String sin_lecturas);
    }

    interface LecturaVista{
        void cargarLecturas(ArrayList<Reading> lreading);
        void error(String mensaje);
    }

}
