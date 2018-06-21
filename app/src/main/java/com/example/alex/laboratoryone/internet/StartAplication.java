package com.example.alex.laboratoryone.internet;

import android.app.Application;
import android.content.Context;

import com.example.alex.laboratoryone.data.SQLiteHelper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StartAplication extends Application {
    private static String BASE_URL = "https://api.fixer.io/";
    private RetorfitGet service;
    private SQLiteHelper helper;

    @Override
    public void onCreate() {
        super.onCreate();
        service = initRetrofit();
        helper = new SQLiteHelper(getApplicationContext());
    }

    public SQLiteHelper sqliteGet() {
        return helper;
    }

    public RetorfitGet ListCurrency() {
        return service;
    }

    public static StartAplication get(Context context) {
        return (StartAplication) context.getApplicationContext();
    }

    private RetorfitGet initRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetorfitGet.class);
    }


}
