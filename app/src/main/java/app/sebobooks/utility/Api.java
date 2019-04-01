package app.sebobooks.utility;
/*
 * Purpose: Define All API Calls
 * Author:  Anupom Roy
 * Date: April 1, 2019
 * Version: 1.0
 * */
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface Api {

    @FormUrlEncoded
    @POST("client")
    Call<ResponseBody> createUser(
            @Field("clientcode") String clientcode,
            @Field("fname") String fname,
            @Field("lname") String lname

    );
}
