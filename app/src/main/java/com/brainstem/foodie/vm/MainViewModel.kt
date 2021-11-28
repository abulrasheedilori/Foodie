package com.brainstem.foodie.vm

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.brainstem.foodie.data.Repository
import com.brainstem.foodie.models.RecipesModel
import com.brainstem.foodie.utils.NetworkResult
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel @ViewModelScoped constructor(
    private val repository: Repository,
    application: Application
): AndroidViewModel(application) {

    private var _recipeResponse: MutableLiveData<NetworkResult<RecipesModel>> = MutableLiveData()
    val recipeResponse: LiveData<NetworkResult<RecipesModel>> = _recipeResponse

    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch{
        getRecipeSafeCall(queries)
    }

    private suspend fun getRecipeSafeCall(queries: Map<String, String>) {
        if(hasNetworkCapability()){
            _recipeResponse.value = NetworkResult.Loading()
            try {
                val response = repository.remote.getRecipes(queries)
                if (response.isSuccessful){
                    _recipeResponse.value = handleRecipesModelResponse(response)
                }
            }catch (e: Exception){
                _recipeResponse.value = NetworkResult.Error("No Recipes is available")
            }
        }else{
                _recipeResponse.value = NetworkResult.Error("No Internet Connection")
        }
    }

    private fun handleRecipesModelResponse(response: Response<RecipesModel>): NetworkResult<RecipesModel>? {
        return when{
            response.message().toString().contains("timeout") -> NetworkResult.Error("Timeout")
            response.code() == 402 -> NetworkResult.Error("API Key Limited")
            response.body()!!.results.isNullOrEmpty() -> NetworkResult.Error("Recipes not found")
            response.isSuccessful -> {
                val recipes = response.body()
                NetworkResult.Success(recipes!!)
            }else -> NetworkResult.Error(response.message())
        }
    }

    private fun hasNetworkCapability(): Boolean{
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}