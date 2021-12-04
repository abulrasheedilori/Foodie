package com.brainstem.foodie.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brainstem.foodie.R
import com.brainstem.foodie.adapters.RecipesAdapter
import com.brainstem.foodie.utils.Constants.Companion.API_KEY
import com.brainstem.foodie.utils.NetworkResult
import com.brainstem.foodie.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipe.view.*

@AndroidEntryPoint
class RecipeFragment : Fragment() {

    private lateinit var mView: View
     private val mainViewModel : MainViewModel by viewModels()
    private var navController : NavController? = null
    private val mAdapter by lazy { RecipesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mView = inflater.inflate(R.layout.fragment_recipe, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        setRecyclerview()
        makeApiCall()

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    private fun showShimmerEffect() {
        //binding.recyclerView.showShimmer()
        mView.recycler_view.showShimmer()

    }

    private fun hideShimmerEffect() {
        //mView.recycler_view.hideShimmer()
        mView.recycler_view.hideShimmer()
    }

    private fun setRecyclerview(){
        mView.recycler_view.adapter = mAdapter
        mView.recycler_view.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun makeApiCall(){
        mainViewModel.getRecipes(applyQueries())
        mainViewModel.recipeResponse.observe(viewLifecycleOwner, { recipe ->

            when(recipe){
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    recipe.data?.let { recipeModel ->
                        mAdapter.setAdapterData(recipeModel)
                    }
                    Log.d("APICALL", "API CALL -> ${recipe.data.toString()}")
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    Toast.makeText(
                        requireContext(),
                        recipe.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("APICALL", "API CALL -> ${recipe.message}")
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                    Log.d("APICALL", "API CALL -> Loading")
                }
            }
        })
    }

    private fun applyQueries(): HashMap<String, String>{
        val queries = HashMap<String, String>()
        queries["number"] = "50"
        queries["apiKey"] = API_KEY
        queries["type"] = "snack"
        queries["diet"] = "vegan"
        queries["addRecipeInformation"] = "true"
        queries["fillIngredients"] = "true"
        return queries
    }
}