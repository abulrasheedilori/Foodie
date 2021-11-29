package com.brainstem.foodie.data

import com.brainstem.foodie.data.network.RecipesApiInterface
import com.brainstem.foodie.models.RecipesModel
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Response
import javax.inject.Inject

@ActivityRetainedScoped
class RemoteDataSource @Inject constructor(
    private val recipesApiInterface: RecipesApiInterface
    ){
    suspend fun getRecipes(queries: Map<String, String>): Response<RecipesModel> {
        return recipesApiInterface.getRecipes(queries)
    }
}