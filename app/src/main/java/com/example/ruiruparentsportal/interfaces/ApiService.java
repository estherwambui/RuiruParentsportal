package com.example.ruiruparentsportal.interfaces;

import com.example.ruiruparentsportal.model.FeeStatusResponse;
import com.example.ruiruparentsportal.model.NewsResponse;
import com.example.ruiruparentsportal.response.ParentResponse;
import com.example.ruiruparentsportal.response.ResultsResponse;
import com.example.ruiruparentsportal.response.StudentResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @FormUrlEncoded
    @POST("parent/login")
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


    @GET("parent/students/{parent_id}")
    Call<StudentResponse> getMyStudents(
            @Path("parent_id") Integer id
    );

    @FormUrlEncoded
    @POST("student/results")
    Call<ResultsResponse> getResults(
            @Field("adm") Integer admNo,
            @Field("form") Integer form,
            @Field("term") Integer term
    );

    @GET("news/")
    Call<NewsResponse> getNewsArticles();

    @FormUrlEncoded
    @POST("/student/fees")
    Call<FeeStatusResponse> getFeeStatus(
            @Field("adm_no") Integer adm,
            @Field("form") Integer form,
            @Field("term") Integer term
    );
}
