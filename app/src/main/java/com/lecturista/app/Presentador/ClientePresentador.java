package com.lecturista.app.Presentador;

import com.lecturista.app.Interface.ClienteInterface;
import com.lecturista.app.Modelo.ClienteModelo;
import com.lecturista.app.POJO.Cliente;
import com.lecturista.app.Vista.ClienteActivity;

import java.util.ArrayList;

public class ClientePresentador implements ClienteInterface.LoggedPresentador {

    private ClienteInterface.LoggedVista lVista;
    private ClienteInterface.LoggedModelo lModelo;

    public ClientePresentador(ClienteActivity lVista) {
        this.lVista =lVista;
        this.lModelo = new ClienteModelo(this);
    }

    @Override
    public void cerrarSession() {
        lModelo.cerrarSesion();
    }

    @Override
    public void cerrarActivity() {
        lVista.finishActivity();
    }

    @Override
    public void buscarCliente(String id, String criterio) {
        lModelo.getCliente(id, criterio);
    }

    @Override
    public void retornarCliente(ArrayList<Cliente> lclientes) {
        lVista.mostrarClientes(lclientes);
    }

    @Override
    public void mostrarError() {

    }
}
