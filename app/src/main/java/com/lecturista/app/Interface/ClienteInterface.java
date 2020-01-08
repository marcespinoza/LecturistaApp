package com.lecturista.app.Interface;

import com.lecturista.app.POJO.Cliente;

import java.util.ArrayList;

public interface ClienteInterface {

  interface LoggedModelo{
      void cerrarSesion();
      void getCliente(String id, String criterio);
  }

  interface LoggedPresentador{
    void cerrarSession();
    void cerrarActivity();
    void buscarCliente(String id, String idBusqueda);
    void retornarCliente(ArrayList<Cliente> lclientes);
    void mostrarError();
  }

  interface LoggedVista{
    void finishActivity();
    void mostrarError();
    void mostrarClientes(ArrayList<Cliente> lclientes);
  }

}
