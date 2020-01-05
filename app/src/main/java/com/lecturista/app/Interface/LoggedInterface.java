package com.lecturista.app.Interface;

public interface LoggedInterface {

  interface LoggedModelo{
      void cerrarSesion();
  }

  interface LoggedPresentador{
    void cerrarSession();
    void cerrarActivity();
  }

  interface LoggedVista{
    void finishActivity();
  }

}
