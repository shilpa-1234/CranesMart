package com.example.cranesmart;
import com.example.cranesmart.pojo.ex;
import com.example.cranesmart.pojo.Verfiy;
import com.example.cranesmart.pojo.fgotp;
import com.example.cranesmart.pojo.forget;
import com.example.cranesmart.pojo.re;
import com.example.cranesmart.pojo.register;
import com.example.cranesmart.pojo.reset;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {
        @FormUrlEncoded
        @POST("userRegAuth")
        Call<register> createUser(
                @Field("name") String name,
                @Field("email") String email,
                @Field("password") String password,
                @Field("mobile") String mobile,
                @Field("referral_id") String referral_id
        );


        //the signin call
        @FormUrlEncoded
        @POST("loginAuth")
        Call<ex> userLogin(
                @Field("username") String email,
                @Field("password") String password

        );

        @FormUrlEncoded
        @POST("registerOTPAuth")
        Call<Verfiy> verify(
                @Field("otp") String otp
          /* @Field("otp1") String otp1,
            @Field("otp2") String otp2,
            @Field("otp3") String otp3,
            @Field("otp4") String otp4*/

        );
        @FormUrlEncoded
        @POST("forgotAuth")
        Call<forget> pass(
                @Field("mobile") String mobile
        );
        @FormUrlEncoded
        @POST("forgotOTPAuth")
        Call<fgotp> otp(
                @Field("otp") String otp
        );
        @FormUrlEncoded
        @POST("updatePasswordAuth")
        Call<reset> reset(
                @Field("userID") String userID,
                @Field("password") String password,
                  @Field("cpassword") String cpassword

        );
        @GET("dashboardAuth")
        Call<re> listRepos();
    }

