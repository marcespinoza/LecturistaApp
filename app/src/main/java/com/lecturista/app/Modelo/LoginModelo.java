package com.lecturista.app.Modelo;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.lecturista.app.Application.GlobalApplication;
import com.lecturista.app.Helper.APICLient;
import com.lecturista.app.Interface.ApiService;
import com.lecturista.app.Interface.LoginInterface;
import com.lecturista.app.POJO.LoginError;
import com.lecturista.app.POJO.LoginResponse;
import com.lecturista.app.Presentador.LoginPresentador;
import com.preference.PowerPreference;
import com.preference.Preference;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginModelo implements LoginInterface.Modelo {

    private LoginInterface.Presentador lPresentador;

    public LoginModelo(LoginPresentador lPresentador) {
        this.lPresentador = lPresentador;
        PowerPreference.init(GlobalApplication.getContext());
    }

    @Override
    public void verificarLogin(String usuario, String password) {
        requestLogin(usuario, password);
    }

    @Override
    public void checkLogin() {
        checkLoginUser();
    }

    //----------Endpoint login----------//
    public void requestLogin(String usuario, String password){
        Retrofit retrofit = APICLient.getApiService();
        ApiService apiService = retrofit.create(ApiService.class);
        JsonObject dataLogin = new JsonObject();
        dataLogin.addProperty("user_name",usuario);
        dataLogin.addProperty("pass",password);
        final Call<LoginResponse> batch = apiService.login(dataLogin);
        batch.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                if(response.body().getStatus().equals("200")){
                    Preference preference = PowerPreference.getFileByName("lecturista");
                    preference.setString("usuario",usuario);
                    preference.setBoolean("logged", true);
                    preference.setString("token",response.body().getToken());
                    preference.setString("user_id", response.body().getUser_id());
                    Log.i("TOKEN","ID "+response.body().getToken());
                    lPresentador.loginCorrecto(usuario);
                }

                }else{
                    Gson gson = new Gson();
                    LoginError message=gson.fromJson(response.errorBody().charStream(),LoginError.class);
                    Log.i("mensje","da"+""+message.getStatusmessage());
                    lPresentador.mostrarMensaje(message.getStatusmessage());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                lPresentador.mostrarMensaje(t.toString());
            }
        });
    }

    //----------Endpoint verificar token----------//
    public void verificarToken(String user_id, String token){
        Retrofit retrofit = APICLient.getApiService();
        ApiService apiService = retrofit.create(ApiService.class);
        JsonObject dataLogin = new JsonObject();
        dataLogin.addProperty("user_id",user_id);
        dataLogin.addProperty("token",token);
        final Call<JsonElement> batch = apiService.verifyToken(dataLogin);
        batch.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if(response.isSuccessful()){
                    JSONObject jsonObj = null;
                    try {
                        jsonObj = new JSONObject(response.body().toString());
                        if(jsonObj.getString("status").equals("200")){
                            Preference preference = PowerPreference.getFileByName("lecturista");
                            String usr = preference.getString("usuario");
                            lPresentador.returnlogin(true, usr);
                        }else{
                            lPresentador.returnlogin(false, "");
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
                lPresentador.mostrarMensaje(t.toString());
            }
        });
    }

    //------Verifica si el usuario ya inicio sesion-----//
    public void checkLoginUser(){
        Preference preference = PowerPreference.getFileByName("lecturista");
        boolean logged = preference.getBoolean("logged", false);
        String usr = preference.getString("usuario");
        if(logged){
            String userid = preference.getString("user_id");
            String token = preference.getString("token");
            verificarToken(userid, token);
        }else{
            lPresentador.finishDialog();
        }

    }

}
