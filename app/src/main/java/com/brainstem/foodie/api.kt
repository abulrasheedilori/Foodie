package com.brainstem.foodie

import com.brainstem.foodie.models.recipesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface api {
    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
        @QueryMap queries: Map<String, String>
    ): Response<recipesModel>
}