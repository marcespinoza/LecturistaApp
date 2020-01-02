package com.lecturista.app.Modelo;

import android.util.Log;

import com.google.gson.Gson;
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
                    lPresentador.loginCorrecto(usuario);
                }

                }else{
                    Gson gson = new Gson();
                    LoginError message=gson.fromJson(response.errorBody().charStream(),LoginError.class);
                    Log.i("mensje","da"+""+message.getStatusmessage());
                    lPresentador.mostrarError(message.getStatusmessage());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                lPresentador.mostrarError(t.toString());
            }
        });
    }

    //------Verifica si el usuario ya inicio sesion-----//
    public void checkLoginUser(){
        Preference preference = PowerPreference.getFileByName("lecturista");
        boolean logged = preference.getBoolean("logged");
        String usr = preference.getString("usuario");
        lPresentador.returnlogin(logged, usr);
    }

}
