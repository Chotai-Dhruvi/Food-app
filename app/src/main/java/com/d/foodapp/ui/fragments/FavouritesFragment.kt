package com.d.foodapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.d.foodapp.Adapters.FavoriteAdapter
import com.d.foodapp.R
import com.d.foodapp.databinding.FragmentFavouritesBinding
import com.d.foodapp.model.Meal
import com.d.foodapp.viewModel.MealViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.log

@AndroidEntryPoint
class FavouritesFragment : Fragment() {

    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    private val mealViewModel: MealViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            favoriteAdapter = FavoriteAdapter()
            setUpFavoriteRecyclerView()
            getSavedData()
        }
        catch (t: Throwable)
        {
            Log.d("favorite", t.message.toString())
        }

    }

    private fun getSavedData() {
        lifecycleScope.launchWhenStarted {
            mealViewModel.getMealSaved().collect{ savedData ->
                favoriteAdapter.differ.submitList(savedData)
            }
        }
    }

    private fun setUpFavoriteRecyclerView() {
        binding.rvFavorite.apply{
            layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
            adapter = favoriteAdapter
        }
    }

}