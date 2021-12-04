package com.brainstem.foodie.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.brainstem.foodie.R

class RecipeRowLayoutBindingAdapter {

    companion object{

        @BindingAdapter("loadRecipeImage")
        @JvmStatic
        fun loadRecipeImage(imageview: ImageView, imageUrl: String){
            imageview.load(imageUrl){
                crossfade(600)
            }
        }

        @BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(textView: TextView, likes: Int){
            textView.text = likes.toString()
        }

        @BindingAdapter("setNumberOfMinutes")
        @JvmStatic
        fun setNumberOfMinutes(textView: TextView, minutes: Int){
            textView.text = minutes.toString()
        }

        @BindingAdapter("setVegan")
        @JvmStatic
        fun setVegan(view: View, vegan: Boolean){
            if (vegan){
                when(view){
                    is TextView -> {view.setTextColor(ContextCompat.getColor(view.context, R.color.green))}
                    is ImageView -> {view.setColorFilter(ContextCompat.getColor(view.context, R.color.green))}
                }
            }
        }
    }
}