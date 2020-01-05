package com.lecturista.app.Modelo;

import com.lecturista.app.Application.GlobalApplication;
import com.lecturista.app.Interface.LoggedInterface;
import com.lecturista.app.Presentador.LoggedPresentador;
import com.preference.PowerPreference;
import com.preference.Preference;

public class LoggedModelo implements LoggedInterface.LoggedModelo {

    private LoggedInterface.LoggedPresentador lPresentador;

    public LoggedModelo(LoggedPresentador lPresentador) {
        this.lPresentador = lPresentador;
        PowerPreference.init(GlobalApplication.getContext());
    }

    @Override
    public void cerrarSesion() {
        Preference preference = PowerPreference.getFileByName("lecturista");
        boolean logged = preference.setBoolean("logged", false);
        if(logged){
            lPresentador.cerrarActivity();
        }
    }


}
