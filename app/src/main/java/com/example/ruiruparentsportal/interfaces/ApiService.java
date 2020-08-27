package com.example.ruiruparentsportal.interfaces;

import com.example.ruiruparentsportal.model.FeeStatusResponse;
import com.example.ruiruparentsportal.model.NewsResponse;
import com.example.ruiruparentsportal.response.FeeStructureResponse;
import com.example.ruiruparentsportal.response.ParentResponse;
import com.example.ruiruparentsportal.response.ResetPassResponse;
import com.example.ruiruparentsportal.response.ResultsResponse;
import com.example.ruiruparentsportal.response.StudentResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    /*Login*/
    @FormUrlEncoded
    @POST("parent/login")
    Call<ParentResponse> loginParent(
            @Field("token") String token,
            @Field("phone") String phone,
            @Field("password") String password);

    /*Retrieve Fees Structure*/
    @FormUrlEncoded
    @POST("feestructure")
    Call<FeeStructureResponse> getFeeStructure(
            @Field("form") Integer form,
            @Field("term") Integer term
    );

    /*Get parent's students*/
    @GET("parent/students/{parent_id}")
    Call<StudentResponse> getMyStudents(
            @Path("parent_id") Integer id
    );

    /*Get student results*/
    @FormUrlEncoded
    @POST("student/results")
    Call<ResultsResponse> getResults(
            @Field("adm") Integer admNo,
            @Field("form") Integer form,
            @Field("term") Integer term
    );

    /*Get news*/
    @GET("news/")
    Call<NewsResponse> getNewsArticles();

    /*Get student's fees status*/
    @FormUrlEncoded
    @POST("fees/status")
    Call<FeeStatusResponse> getFeeStatus(
            @Field("adm") Integer adm,
            @Field("form") Integer form,
            @Field("term") Integer term
    );

    /*Reset password*/
    @POST("parent/reset")
    @FormUrlEncoded
    Call<ResetPassResponse> requestCode(
            @Field("phone") String phone
    );

    /*Reset password*/
    @POST("parent/updatePass")
    @FormUrlEncoded
    Call<ResetPassResponse> resetPassword(
            @Field("phone") String phone,
            @Field("code") String code,
            @Field("new_password") String password
    );
}
