package app.sebobooks.utility;
/*
 * Purpose: to difining Retrofit Client
 * Author:  Anupom Roy
 * Date: April 1, 2019
 * Version: 1.0
 * */

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Create a singleton class if it is too much network request
public class RetrofitClient {
    private static final String BASE_URL = "http://10.24.65.174:3000/api/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }
    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public Api getApi(){
        return retrofit.create(Api.class);
    }

}