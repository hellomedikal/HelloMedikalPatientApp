package com.pradeep.hellomedikalpatientapp.Interface;


import com.pradeep.hellomedikalpatientapp.POJO.ModelEmailVerify;
import com.pradeep.hellomedikalpatientapp.POJO.ModelLogin;
import com.pradeep.hellomedikalpatientapp.POJO.ModelPhoneVerify;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiLogin_Interface {

    String BASE_URL = "http://app.hellomedikal.com/";

    @FormUrlEncoded
    @POST("LoginPatient")
    Call<ModelLogin> LOGIN_CALL(@Header("X-API-KEY") String api_key, @Header("Authorization") String authorization, @Header("Content-Type") String header, @Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("Verification")
    Call<ModelEmailVerify> EMAIL_VERIFY_CALL(@Header("X-API-KEY") String api_key, @Header("Authorization") String authorization, @Header("Content-Type") String header, @Field("email") String email);

    @FormUrlEncoded
    @POST("Verification")
    Call<ModelPhoneVerify> PHONE_VERIFY(@Header("X-API-KEY") String api_key, @Header("Authorization") String authorization, @Header("Content-Type") String header, @Field("phone_number") String phone_number);


}
