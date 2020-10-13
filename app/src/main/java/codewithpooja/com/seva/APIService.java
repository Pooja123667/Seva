package codewithpooja.com.seva;

import android.util.JsonReader;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIService {

    @Multipart
    @POST("seva/index.php") //adding new donation (donorside)
    Call<Result> addDonation(
            @Part MultipartBody.Part image,
            @Part("req") RequestBody request,
            @Part("donation_quantity") RequestBody donation_quantity,
            @Part("donation_brief") RequestBody donation_brief,
            @Part("token") RequestBody token);

    @FormUrlEncoded //for registration
    @POST("seva/index.php")
    Call<Result> createUser (
            @Field("req") String request,
            @Field("name") String Name,
            @Field("contact") String Contact,
            @Field("username") String Username,
            @Field("password") String Password);

    @FormUrlEncoded //for login
    @POST("seva/index.php")
    Call<Result> checkUser(
            @Field("req") String request,
            @Field("username") String username,
            @Field("password") String password);

    @GET("seva/index.php") // get request for fetching the donations list of that particular user
    Call<List<Donation>> getDonationList(
            @Query("req") String request,
            @Query("token") String token);

    @GET("seva/index.php") //get request for fetching all the donations (using in collector side)
    Call<List<Donation>> getAllDonations(
            @Query("req") String request);

    @FormUrlEncoded //for setting status yes (of food)
    @POST("seva/index.php")
    Call<Result> setFoodStatus(
            @Field("req") String request,
            @Field("token") String token, //will get from shared pref
            @Field("donation_id") String donation_id);

    @GET("seva/index.php") // getting the list of accepted donations
    Call<List<Donation>> getAcceptedDonationList(
            @Query("req") String request,
            @Query("token") String token);

    @FormUrlEncoded //for setting food status to NO (not done yet)
    @POST("seva/index.php")
    Call<Result> rejectFoodStatus(
            @Field("req") String request,
            @Field("token") String token, //will get from shared pref
            @Field("donation_id") String donation_id);


}
