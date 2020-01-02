package com.lecturista.app.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lecturista.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ButtonsActivity extends AppCompatActivity {

    @BindView(R.id.user) TextView usuario;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buttons_activity);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (null != intent) { //Null Checking
            String usr= intent.getStringExtra("usuario");
            usuario.setText(usr);
        }
    }
}
