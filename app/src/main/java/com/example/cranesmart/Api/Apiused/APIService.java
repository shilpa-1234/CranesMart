package com.example.cranesmart.Api.Apiused;

import com.example.cranesmart.Paymentgatewaywallet.Paymentshopingpojo;
import com.example.cranesmart.Paymentgatewaywallet.paymentpojo;
import com.example.cranesmart.pojo.Checkoutpojo.Checkoutapi;
import com.example.cranesmart.pojo.cranesmartpojo.Cranesmartwallet;
import com.example.cranesmart.pojo.Orderlistpojo.Orderlist;
import com.example.cranesmart.pojo.PremiumWallethistory.Wallethistory;
import com.example.cranesmart.pojo.RechargeHistory.Rechargehistorypojo;
import com.example.cranesmart.pojo.Search.Searchpojo;
import com.example.cranesmart.pojo.Userdetail.UserDetailpojo;
import com.example.cranesmart.pojo.address.Addaddress;
import com.example.cranesmart.pojo.address.Addaddresslist;
import com.example.cranesmart.pojo.cart.Addcartlist;
import com.example.cranesmart.pojo.cart.Cartpojo;
import com.example.cranesmart.pojo.changepassword.Changepassword;
import com.example.cranesmart.pojo.countrystatelist.Countrypojo;
import com.example.cranesmart.pojo.cranemartpoint.cranemartpointpojo;
import com.example.cranesmart.pojo.deletecart.Deletecartpojo;
import com.example.cranesmart.pojo.deletecart.Deletepojo;
import com.example.cranesmart.pojo.operatorlist.Commonliststate.Circlelist;
import com.example.cranesmart.pojo.operatorlist.DTH.DTHlist;
import com.example.cranesmart.pojo.operatorlist.Datacard.Datacard;
import com.example.cranesmart.pojo.operatorlist.Electricity.Electricity;
import com.example.cranesmart.pojo.operatorlist.Landline.Landline;
import com.example.cranesmart.pojo.operatorlist.Prepaid.Operatorlist;
import com.example.cranesmart.pojo.operatorlist.otprecharge.Otpmobilerecharge;
import com.example.cranesmart.pojo.operatorlist.postpaid.operlistpostpaid;
import com.example.cranesmart.pojo.paymentparametrekey.parametrekey;
import com.example.cranesmart.pojo.productdetail.Detailpojo;
import com.example.cranesmart.pojo.address.Editaddresspojo;
import com.example.cranesmart.pojo.editprofile.Editprofile;
import com.example.cranesmart.pojo.productlist.Productpojoretro;
import com.example.cranesmart.pojo.countrystatelist.Statelist;
import com.example.cranesmart.pojo.recharge.Landlinepojo;
import com.example.cranesmart.pojo.recharge.Rechargepojo;
import com.example.cranesmart.pojo.recharge.Rechargepostpojo;
import com.example.cranesmart.pojo.subcategory.Subcategoryretr;
import com.example.cranesmart.pojo.registerlogin.ex;
import com.example.cranesmart.pojo.verfiyforget.Verfiy;
import com.example.cranesmart.pojo.verfiyforget.fgotp;
import com.example.cranesmart.pojo.verfiyforget.forget;
import com.example.cranesmart.pojo.dashboard.re;
import com.example.cranesmart.pojo.registerlogin.register;
import com.example.cranesmart.pojo.updatepassword.reset;
import com.example.cranesmart.Paymentgatewaywallet.successpaymentpojo;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {
        // if any one have to check End url of Api then Go to StringName Directory
        //In this i have Made the Class And Class Name is:- EndurlofApi
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
        @FormUrlEncoded
        @POST("changePassword")
        Call<Changepassword> change(
                @Field("userID") String userID,
                @Field("opw") String opw,
                  @Field("npw") String npw,
                  @Field("cpw") String cpw

        );
        @GET("dashboardAuth")
        Call<re> listRepos();

        @FormUrlEncoded
        @POST("getSubCategoryList")
        Call<Subcategoryretr> sub(
                @Field("catID") String catID

        );
        @FormUrlEncoded
        @POST("getProductList")
        Call<Productpojoretro> product(
                @Field("catID") String catID
        );
        @FormUrlEncoded
        @POST("getProductDetail")
        Call<Detailpojo> detail(
                @Field("proID") String proID
        );
        @FormUrlEncoded
        @POST("cart")
        Call<Cartpojo> cart(
                @Field("userID") String userID,
                @Field("ip") String ip,
                @Field("proID") String proID,
                @Field("qty") String qty,
                @Field("attribute_data") String attribute_data
                );
          @FormUrlEncoded
        @POST("cartList")
        Call<Addcartlist> cartlist(
                  @Field("userID") String userID,
                  @Field("ip") String ip
          );
          @FormUrlEncoded
        @POST("saveAddress")
        Call<Addaddress> add(
                  @Field("name") String name,
                  @Field("address_line_1") String address_line_1,
                  @Field("address_line_2") String address_line_2,
                  @Field("city") String city,
                  @Field("postal_code") String postal_code,
                  @Field("phone_number") String phone_number ,
                  @Field("state") String state,
                  @Field("country") String country,
                  @Field("userID") String userID
          );
        @GET("getCountryList")
        Call<Countrypojo> country();
        @FormUrlEncoded
        @POST("getStateList")
        Call<Statelist> state(
          @Field("countryCode") String countryCode
        );

        @FormUrlEncoded
        @POST("userAddressList")
        Call<Addaddresslist> addresslist(
                @Field("userID") String userID
        );
        @FormUrlEncoded
        @POST("updateUserData")
        Call<Editprofile> profile(
                @Field("userID") String userID,
                @Field("name") String name,
                @Field("photo") String image,
                @Field("mobile") String mobile,
              @Field("email") String email
                );
        @FormUrlEncoded
        @POST("deleteUserAddress")
        Call<Deletepojo> delete(
                @Field("userID") String userID,
                @Field("addID") String addID
        );
        @FormUrlEncoded
        @POST("updateUserAddress")
        Call<Editaddresspojo> edit(
                @Field("userID") String userID,
                @Field("addID") String addID,
                @Field("name") String name,
                @Field("phoneNumber") String phone_number ,
                @Field("address_1") String address_line_1,
                @Field("city") String city,
                @Field("countryID") String country,
                @Field("stateID") String state,
                @Field("zipCode") String postal_code
        );
        @FormUrlEncoded
        @POST("deleteCartData")
        Call<Deletecartpojo> cartremove(
                @Field("temp_id") String temp_id,
                @Field("userID") String userID,
                @Field("proID") String proID
        );
        @FormUrlEncoded
        @POST("operatorList")
        Call<Operatorlist> list(
                @Field("type") String type

        );
        @FormUrlEncoded
        @POST("operatorList")
        Call<operlistpostpaid> list1(
                @Field("type") String postpaid

        );
        @FormUrlEncoded
        @POST("operatorList")
        Call<DTHlist> list2(
                @Field("type") String DTH

        );
        @FormUrlEncoded
        @POST("operatorList")
        Call<Datacard> list3(
                @Field("type") String Datacard

        );
        @FormUrlEncoded
        @POST("operatorList")
        Call<Landline> list4(
                @Field("type") String Landline

        ); @FormUrlEncoded
        @POST("operatorList")
        Call<Electricity> list5(
                        @Field("type") String Electricity

                );
        @GET("circleList")
        Call<Circlelist> circleList();
        @FormUrlEncoded
        @POST("rechargeAuth")
        Call<Rechargepojo> recharge(
                @Field("userID") String userID,
                @Field("type") String type,
                @Field("mobile") String mobile,
                @Field("rechargeType") String rechargeType,
                @Field("operator") String operator,
                @Field("circle") String circle,
                @Field("amount") String amount
        );
        @FormUrlEncoded
        @POST("rechargeAuth")
        Call<Rechargepostpojo> rechargepost(
                @Field("userID") String userID,
                @Field("type") String type,
                @Field("mobile") String mobile,
                @Field("rechargeType") String rechargeType,
                @Field("operator") String operator,
                @Field("circle") String circle,
                @Field("amount") String amount,
                @Field("acnumer") String acnumer
        );
        @FormUrlEncoded
        @POST("rechargeAuth")
        Call<Rechargepojo> rechargepos(
                @Field("userID") String userID,
                @Field("type") String type,
                @Field("operator") String operator,
                @Field("circle") String circle,
                @Field("amount") String amount,
                @Field("cardNumber") String cardNumber
        );
        @FormUrlEncoded
        @POST("rechargeAuth")
        Call<Datacard> Datacard(
                @Field("userID") String userID,
                @Field("type") String type,
                @Field("operator") String operator,
                @Field("circle") String circle,
                @Field("amount") String amount,
                @Field("cardNumber") String acnumer,
                @Field("rechargeType") String rechargeType
                );
        @FormUrlEncoded
        @POST("rechargeAuth")
        Call<Landlinepojo>landline(
                @Field("userID") String userID,
                @Field("type") String type,
                @Field("operator") String operator,
                @Field("circle") String circle,
                @Field("number") String number,
                @Field("amount") String amount
        );
        @FormUrlEncoded
        @POST("rechargeAuth")
        Call<Landlinepojo>electronics(
                @Field("userID") String userID,
                @Field("type") String type,
                @Field("operator") String operator,
                @Field("account_number") String account_number,
                @Field("customer_name") String customer_name,
                @Field("reference_id") String reference_id,
                @Field("amount") String amount
        );
        @FormUrlEncoded
        @POST("rechargeOTPAuth")
        Call<Otpmobilerecharge> verifyotp(
                @Field("userID") String userID,
                @Field("otp") String otp

        );
        @FormUrlEncoded
        @POST("searchProduct")
        Call<Searchpojo> search(
                @Field("keyword") String keyword

        );
        @FormUrlEncoded
        @POST("userDetail")
        Call<UserDetailpojo> userdetail(
                @Field
                        ("user_id") String userid
        );
        @FormUrlEncoded
        @POST("getPremiumWalletHistory")
        Call<Wallethistory> wallet(
                @Field
                        ("userID") String userID
        );
        @FormUrlEncoded
        @POST("getPointHistory")
        Call<cranemartpointpojo> cranes(
                @Field
                        ("userID") String userID
        );
        @FormUrlEncoded
        @POST("getRechargeHistory")
        Call<Rechargehistorypojo> historyrecharge(
                @Field
                        ("userID") String userID
        );
        @FormUrlEncoded
        @POST("userOrderData")
        Call<Orderlist> listorder(
                @Field
                        ("userID") String userID
        );
        @FormUrlEncoded
        @POST("paymentSuccessAuth")
        Call<successpaymentpojo> payment(
                @Field
                        ("userID") String userID,
                @Field
                        ("amount") String amount,
                @Field
                        ("status") String status,
                @Field
                        ("txnid") String txnid,
                @Field
                        ("hash") String hash,
                @Field
                        ("firstname") String firstname,
                @Field
                        ("key") String key,
                @Field
                        ("productinfo") String productinfo ,
                @Field
                        ("email") String email

        );
        @FormUrlEncoded
        @POST("orderSuccessWallet")
        Call<Cranesmartwallet> cranesmartwallet(
                @Field
                        ("userID") String userID,
                @Field
                        ("encoded_order_id") String encoded_order_id,
                @Field
                        ("status") String status,
                @Field
                        ("amount") String amount,
                @Field
                        ("txnid") String txnid

        );
        @GET("getPaymentKey")
        Call<parametrekey> key();

        @FormUrlEncoded
        @POST("generateHash")
        Call<paymentpojo> hash(
                @Field("userID") String userID,
                @Field("amount") String amount

        );
        @FormUrlEncoded
        @POST("paymentFailedAuth")
        Call<JSONObject> fail(
                @Field("userID") String userID,
                @Field("amount") String amount,
                @Field("status") String status,
                @Field("txnid") String txnid

        );
        @FormUrlEncoded
        @POST("orderAuth")
        Call<Checkoutapi> order(
                @Field("userID") String userID,
                @Field("address_id") String address_id


        );
        @FormUrlEncoded
        @POST("orderSuccess")
        Call<Paymentshopingpojo> paymentorder(
                @Field
                        ("userID") String userID,
                @Field
                        ("encoded_order_id") String encoded_order_id,
                @Field
                        ("amount") String amount,
                @Field
                        ("status") String status,
                @Field
                        ("txnid") String txnid,
                @Field
                        ("hash") String hash,
                @Field
                        ("firstname") String firstname,
                @Field
                        ("key") String key,
                @Field
                        ("productinfo") String productinfo ,
                @Field
                        ("email") String email

        );
        @FormUrlEncoded
        @POST("orderFailed")
        Call<JSONObject> failorder(
                @Field("userID") String userID,
                @Field("encoded_order_id") String encoded_order_id,
                @Field("status") String status,
                @Field("amount") String amount,
                @Field("txnid") String txnid

        );
}

