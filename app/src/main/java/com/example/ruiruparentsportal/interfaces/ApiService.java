package com.example.ruiruparentsportal.interfaces;

import com.example.ruiruparentsportal.model.FeeStatusResponse;
import com.example.ruiruparentsportal.model.NewsResponse;
import com.example.ruiruparentsportal.response.ParentResponse;
import com.example.ruiruparentsportal.response.ResultsResponse;
import com.example.ruiruparentsportal.response.StudentResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("login.php")
    Call<ParentResponse> loginParent(
            @Field("token") String token,
            @Field("phone") String phone,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("register.php")

        /* Corrected this bug, now register is working :) */
    Call<ParentResponse> registerParent(
            @Field("token") String token,
            @Field("name") String name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("password") String password);


    @FormUrlEncoded
    @POST("functions.php")
    Call<StudentResponse> getMyStudents(
            @Field("token") String token,
            @Field("parent_id") Integer id
    );

    @FormUrlEncoded
    @POST("functions.php")
    Call<ResultsResponse> getResults(
            @Field("token") String token,
            @Field("adm_no") Integer admNo,
            @Field("form") Integer form,
            @Field("term") Integer term
    );

    @FormUrlEncoded
    @POST("functions.php")
    Call<NewsResponse> getNewsArticles(
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("functions.php")
    Call<FeeStatusResponse> getFeeStatus(
            @Field("token") String token,
            @Field("adm_no") Integer adm,
            @Field("form") Integer form,
            @Field("term") Integer term
    );
}
