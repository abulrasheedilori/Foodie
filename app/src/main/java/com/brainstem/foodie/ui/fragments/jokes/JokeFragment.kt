package com.brainstem.foodie.ui.fragments.jokes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brainstem.foodie.R


class JokeFragment : Fragment() {

//    private var _binding: FragmentJokeBinding? = null
//    private val binding get() = _binding!!
    private lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_joke, container, false)
        return mView
    }
}