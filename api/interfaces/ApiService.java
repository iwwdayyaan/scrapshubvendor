package com.scrapshubvendor.api.interfaces;


import com.scrapshubvendor.api.Responce;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by indiawebwide on 18/04/17.
 */

public interface ApiService {

  @FormUrlEncoded
    @POST("json/jason.php")
    Call<Responce.SignupMember> editVender(@Field("action") String action, @Field("name") String name, @Field("email") String email,
                                             @Field("phone_number") String phone_number, @Field("password") String password,
                                             @Field("city") String city, @Field("address") String address,
                                           @Field("lat") String lat, @Field("log") String log, @Field("venderId") String venderId);
 @FormUrlEncoded
    @POST("json/jason.php")
    Call<Responce.SignupMember> login(@Field("action") String action, @Field("email") String email, @Field("password") String password,
                                      @Field("phone_number") String phone_number, @Field("token") String token);

     @FormUrlEncoded
    @POST("json/jason.php")
    Call<Responce.FetchVendorOrders> fetchVendorOrders(@Field("action") String action,@Field("venderId") String venderId);

 @FormUrlEncoded
    @POST("json/jason.php")
    Call<Responce.Status> orderAcceptReject(@Field("action") String action,@Field("venderId") String venderId,@Field("orderId") String orderId,
                                               @Field("status") String order_status);

    @FormUrlEncoded
    @POST("json/jason.php")
    Call<Responce.SignupMember> fetchVendorProfile(@Field("action") String action,@Field("venderId") String venderId);

    @FormUrlEncoded
    @POST("json/jason.php")
    Call<Responce.FetchCity> fetchCity(@Field("action") String action);

    @FormUrlEncoded
    @POST("json/jason.php")
    Call<Responce.FetchVendorLeaderCredits> fetchVendorLeaderCredits(@Field("action") String action,@Field("venderId") String venderId);

    @FormUrlEncoded
    @POST("json/jason.php")
    Call<Responce.FetchOrderDetail> fetchOrderDetail(@Field("action") String action,@Field("orderId") String orderId);

    @FormUrlEncoded
    @POST("json/jason.php")
    Call<Responce.FetchPackage> fetchPackage(@Field("action") String action,@Field("orderId") String orderId);

    @FormUrlEncoded
    @POST("json/jason.php")
    Call<Responce.SignupMember> signupMember(@Field("action") String action, @Field("name") String name, @Field("email") String email, @Field("phone_number") String phone_number,
                                             @Field("password") String password, @Field("city") String city, @Field("address") String address,
                                             @Field("lat") String lat,@Field("log") String lang);

    @FormUrlEncoded
    @POST("json/jason.php")
    Call<Responce.FetchAboutus> fetchAboutus(@Field("action") String action);

   @FormUrlEncoded
   @POST("json/jason.php")
   Call<Responce.Status> forgetPassword(@Field("action") String action,@Field("email") String email);

    @FormUrlEncoded
    @POST("json/jason.php")
    Call<Responce.Status> onOffVendor(@Field("action") String action,@Field("venderId") String venderId, @Field("on_off") String on_off);

    @FormUrlEncoded
    @POST("json/jason.php")
    Call<Responce.FetchUserStatus> fetchUserStatus(@Field("action") String action,@Field("venderId") String userid);

    @FormUrlEncoded
    @POST("json/jason.php")
    Call<Responce.Status> fetchVendorRemCredits(@Field("action") String action,@Field("venderId") String userid);
    /* @FormUrlEncoded
  @POST("json/jason.php")
  Call<Responce.Status> order(@Field("action") String action, @Field("userid") String userid, @Field("product") String product,
                              @Field("name") String name, @Field("email") String email,
                              @Field("phone") String phone, @Field("city") String city, @Field("address") String address,
                              @Field("lat") String lat, @Field("log") String lang);
  @Streaming
  @GET
  Call<Responce.LatLangBounds> getLatLongBounds(@Url String fileUrl);*/
   /* @GET("/data/2.5{movie_id}getDetails")
    Call <Responce.Status> getMovieDatils(@Path(value = "movie_id", encoded = true) String movieID);*/
}
