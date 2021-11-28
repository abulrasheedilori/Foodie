package com.brainstem.foodie.data.network

import com.brainstem.foodie.models.RecipesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface RecipesApiInterface {

    @GET("/recipes/complexSearch")
    suspend fun getRecipes(@QueryMap queries: Map<String, String>): Response<RecipesModel>
}