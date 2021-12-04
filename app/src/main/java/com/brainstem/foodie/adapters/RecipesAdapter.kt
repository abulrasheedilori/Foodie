package com.brainstem.foodie.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.brainstem.foodie.databinding.RecipeSrvRowLayoutBinding
import com.brainstem.foodie.models.RecipesModel
import com.brainstem.foodie.models.Result
import com.brainstem.foodie.utils.RecipeDiffUtil

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

    private var recipesList = emptyList<Result>()

    class MyViewHolder(private val binding: RecipeSrvRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(result: Result){
                binding.recipe = result
                binding.executePendingBindings()
            }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder{
                val binding = RecipeSrvRowLayoutBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = recipesList[position]
        holder.bind(currentRecipe)
    }

    override fun getItemCount(): Int {
        return recipesList.count()
    }

    fun setAdapterData(data: RecipesModel){
        val recipeDiffUtil = RecipeDiffUtil(recipesList, data.results)
        val diffUtil = DiffUtil.calculateDiff(recipeDiffUtil)
        recipesList = data.results
        diffUtil.dispatchUpdatesTo(this)
    }
}