package com.lecturista.app.Modelo;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.lecturista.app.Application.GlobalApplication;
import com.lecturista.app.Helper.APICLient;
import com.lecturista.app.Interface.ApiService;
import com.lecturista.app.Interface.ClienteInterface;
import com.lecturista.app.POJO.Cliente;
import com.lecturista.app.Presentador.ClientePresentador;
import com.preference.PowerPreference;
import com.preference.Preference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ClienteModelo implements ClienteInterface.LoggedModelo {

    private ClienteInterface.LoggedPresentador lPresentador;

    public ClienteModelo(ClientePresentador lPresentador) {
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

    @Override
    public void getCliente(String id, String criterio) {
        requestCliente(id, criterio);
    }

    //----------Endpoint login----------//
    public void requestCliente(String id, String criterio){
        Preference preference = PowerPreference.getFileByName("lecturista");
        String usuario = preference.getString("user_id", "");
        String token = preference.getString("token", "");
        Retrofit retrofit = APICLient.getApiService();
        ApiService apiService = retrofit.create(ApiService.class);
        JsonObject dataCliente = new JsonObject();
        dataCliente.addProperty("user_id",usuario);
        dataCliente.addProperty("token",token);
        dataCliente.addProperty(criterio,id);
        final Call<JsonElement> batch = apiService.getClientes(dataCliente);
        batch.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if(response.isSuccessful()){
                    ArrayList<Cliente>lClientes = new ArrayList<>();
                    try {
                        JSONObject jsonObj = new JSONObject(response.body().toString());
                        if(!jsonObj.getString("data").equals("null")){
                        JSONArray jclientes = jsonObj.getJSONArray("data");
                          for(int i=0; i<jclientes.length(); i++) {
                            JSONObject jcliente = jclientes.getJSONObject(i);
                            Gson gson= new Gson();
                            Cliente cliente = gson.fromJson(jcliente.toString(),Cliente.class);
                            lClientes.add(cliente);
                          }
                        lPresentador.retornarCliente(lClientes);
                        }else{
                            lPresentador.mostrarError();
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
                Log.i("Error","Error"+t.toString());
            }
        });
    }


}
