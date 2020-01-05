package com.lecturista.app.Vista.Dialog;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.lecturista.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.ContentValues.TAG;

public class Logout extends DialogFragment {


    private  DialogCallback dialogCallback;

    public interface DialogCallback{

        void clickedPositive();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_logout, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    public static Logout newInstance(String title) {
        Logout frag = new Logout();
        return frag;
    }

    @OnClick(R.id.salirno)
    public void nobutton(){
        dismiss();
    }

    @OnClick(R.id.salirsi)
    public void sibutton(){
        dialogCallback.clickedPositive();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            dialogCallback = (DialogCallback) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: " + e.getMessage());
        }
    }

}
