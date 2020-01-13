package com.lecturista.app.Modelo;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.lecturista.app.Application.GlobalApplication;
import com.lecturista.app.Helper.APICLient;
import com.lecturista.app.Interface.ApiService;
import com.lecturista.app.Interface.LecturaInterface;
import com.lecturista.app.POJO.LoginError;
import com.lecturista.app.POJO.LoginResponse;
import com.lecturista.app.Presentador.LecturaPresentador;
import com.preference.PowerPreference;
import com.preference.Preference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LecturaModelo implements LecturaInterface.LecturaModelo {

    public LecturaInterface.LecturaPresentador lPresentador;

    public LecturaModelo(LecturaPresentador lPresentador) {
        this.lPresentador = lPresentador;
        PowerPreference.init(GlobalApplication.getContext());
    }

    @Override
    public void grabarDatos(Bitmap image, String texto) {
        guardarLectura(image, texto);
    }


    //----------Endpoint guardar lectura----------//
    public void guardarLectura(Bitmap image, String texto){
        Preference preference = PowerPreference.getFileByName("lecturista");
        Retrofit retrofit = APICLient.getApiService();
        ApiService apiService = retrofit.create(ApiService.class);
        JsonObject dataLogin = new JsonObject();
        dataLogin.addProperty("user_id",preference.getString("user_id"));
        dataLogin.addProperty("reading",texto);
        dataLogin.addProperty("token", preference.getString("token"));
        dataLogin.addProperty("affiliate_id",preference.getString("user_id"));
        dataLogin.addProperty("image64",getEncoded64ImageStringFromBitmap(image));
        final Call<JsonElement> batch = apiService.guardarImagen(dataLogin);
        batch.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if(response.isSuccessful()){
                    try {
                        JSONObject jsonObj = new JSONObject(response.body().toString());
                        if(jsonObj.getString("status-msg").equals("ok")){
                            lPresentador.mostrarMensaje();
                        }else{
                            lPresentador.mostrarMensaje();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    Log.i("mensje","da"+""+response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                lPresentador.grabacionExitosa();
            }
        });
    }

    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return imgString;
    }
}
