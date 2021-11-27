package com.brainstem.foodie.models


import com.google.gson.annotations.SerializedName

data class recipesModel(
    @SerializedName("results")
    val results: List<Result>
)