package com.lecturista.app.Vista.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lecturista.app.R;

public class CustomProgressDialog {

    Dialog dialog;
    TextView titulo;

    public void show(Context context){

    }

    public Dialog show(Context context, String tit){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.progress_dialog, null);
        titulo = (TextView) view.findViewById(R.id.titledialog);
        if (tit != null) {
            titulo.setText(tit);
        }
        dialog = new Dialog(context, R.style.CustomProgressBarTheme);
        dialog.setContentView(view);
        dialog.show();

        return dialog;
    }

    public void ocultar() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

}
