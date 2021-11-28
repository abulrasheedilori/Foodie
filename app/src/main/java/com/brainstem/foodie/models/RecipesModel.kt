package com.brainstem.foodie.models


import com.google.gson.annotations.SerializedName

data class RecipesModel(
    @SerializedName("results")
    val results: List<Result>
)