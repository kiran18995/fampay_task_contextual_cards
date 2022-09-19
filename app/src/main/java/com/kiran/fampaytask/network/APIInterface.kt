package com.kiran.fampaytask.network

import com.kiran.fampaytask.models.CardGroupObject
import retrofit2.Call
import retrofit2.http.GET

interface APIInterface {
    @GET("04a04703-5557-4c84-a127-8c55335bb3b4")
    fun getCardGroups(): Call<CardGroupObject?>?
}