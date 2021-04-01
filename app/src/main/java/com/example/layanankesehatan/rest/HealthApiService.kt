package com.example.layanankesehatan.rest

import com.example.layanankesehatan.config.ServerConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class HealthApiService {
    companion object {
        fun getClient(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(ServerConfig.DOMAIN_SERVER_API)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
    }
}