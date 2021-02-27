package com.example.popularmovies2.service;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *  Create a Singleton of Retrofit
 */
public class RetrofitInstance {

    /** static variable of Retrofit **/
    private static Retrofit retrofit=null;

    private static String BASE_URL = "https://api.themoviedb.org/3/";



    public static Retrofit getClient(){

        if(retrofit==null){

        // create the retrofit instance using the builder
            retrofit = new Retrofit.Builder()
                    // set the API base URL
                    .baseUrl(BASE_URL)
                    // Use the GsonConverterFactory class to generate an
                    // implementation of the MoviesService interface
                    // which uses Gson for its deserialization
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }




}
