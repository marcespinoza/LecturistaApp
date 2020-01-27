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
import com.lecturista.app.Interface.LectorInterface;
import com.lecturista.app.POJO.Reading;
import com.lecturista.app.Presentador.LectorPresentador;
import com.preference.PowerPreference;
import com.preference.Preference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LectorModelo implements LectorInterface.LecturaModelo {

    public LectorInterface.LecturaPresentador lPresentador;

    public LectorModelo(LectorPresentador lPresentador) {
        this.lPresentador = lPresentador;
        PowerPreference.init(GlobalApplication.getContext());
    }

    @Override
    public void grabarDatos(Bitmap image, String texto, boolean rewrite, String id_rewrite, String id_affiliate) {
        guardarLectura(image, texto, rewrite,id_rewrite, id_affiliate);
    }



    //----------Endpoint guardar lectura----------//
    public void guardarLectura(Bitmap image, String texto, boolean rewrite, String id_rewrite, String id_affiliate){
        Preference preference = PowerPreference.getFileByName("lecturista");
        Retrofit retrofit = APICLient.getApiService();
        ApiService apiService = retrofit.create(ApiService.class);
        JsonObject dataLogin = new JsonObject();
        dataLogin.addProperty("user_id",preference.getString("user_id"));
        dataLogin.addProperty("reading",texto);
        dataLogin.addProperty("token", preference.getString("token"));
        dataLogin.addProperty("affiliate_id", id_affiliate);
        dataLogin.addProperty("image64",getEncoded64ImageStringFromBitmap(image));
        if(rewrite){
            dataLogin.addProperty("rewrite", rewrite);
            dataLogin.addProperty("id_rewrite",id_rewrite);
        }
        final Call<JsonElement> batch = apiService.guardarImagen(dataLogin);
        batch.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if(response.isSuccessful()){
                    try {
                        JSONObject jsonObj = new JSONObject(response.body().toString());
                        if(jsonObj.getString("status").equals("200")){
                            lPresentador.grabacionExitosa();
                        }else{
                            lPresentador.errorGrabacion("Error al grabar. Intente nuevamente");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    lPresentador.mostrarMensaje(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                lPresentador.mostrarMensaje(t.getMessage());
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
