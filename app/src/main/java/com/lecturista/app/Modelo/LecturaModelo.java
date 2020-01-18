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
import com.lecturista.app.POJO.Reading;
import com.lecturista.app.Presentador.LecturaPresentador;
import com.preference.PowerPreference;
import com.preference.Preference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

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

    @Override
    public void lecturas() {
        obtenerLecturas();
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

    //----------Endpoint guardar lectura----------//
    public void obtenerLecturas(){
        Preference preference = PowerPreference.getFileByName("lecturista");
        Retrofit retrofit = APICLient.getApiService();
        ApiService apiService = retrofit.create(ApiService.class);
        JsonObject dataReadings = new JsonObject();
        dataReadings.addProperty("user_id",preference.getString("user_id"));
        dataReadings.addProperty("token", preference.getString("token"));
        final Call<JsonElement> batch = apiService.last_readings(dataReadings);
        batch.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if(response.isSuccessful()){
                    ArrayList<Reading>lReadings = new ArrayList<>();
                    try {
                        JSONObject jsonObj = new JSONObject(response.body().toString());
                        if(!jsonObj.getString("data").equals("FAIL")){
                            JSONArray jclientes = jsonObj.getJSONArray("data");
                            for(int i=0; i<jclientes.length(); i++) {
                                JSONObject jcliente = jclientes.getJSONObject(i);
                                Gson gson= new Gson();
                                Reading reading = gson.fromJson(jcliente.toString(),Reading.class);
                                lReadings.add(reading);
                            }
                            lPresentador.retornarLecturas(lReadings);
                        }else{
                            lPresentador.mostrarMensaje("Sin lecturas");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    Log.i("Error","Error");
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
