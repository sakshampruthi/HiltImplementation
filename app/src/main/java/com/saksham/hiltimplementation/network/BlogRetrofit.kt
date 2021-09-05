package com.saksham.hiltimplementation.network

import retrofit2.http.GET

interface BlogRetrofit {

    @GET("blogs")
    suspend fun getData():List<BlogNetworkEntity>
}