package com.d.foodapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.d.foodapp.Adapters.CategoriesHomeAdapter
import com.d.foodapp.Adapters.OverPopularItemsAdapter
import com.d.foodapp.R
import com.d.foodapp.databinding.FragmentHomeBinding
import com.d.foodapp.model.Meal
import com.d.foodapp.state.HomeFragmentState
import com.d.foodapp.ui.MealActivity
import com.d.foodapp.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var overPopularItemsAdapter: OverPopularItemsAdapter
    private lateinit var categoriesHomeAdapter: CategoriesHomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        overPopularItemsAdapter = OverPopularItemsAdapter()
        categoriesHomeAdapter = CategoriesHomeAdapter()

        setupRecyclerViews()
        observeHomeFragmentState()
        onOverItemClick()
        onCategoryItemClick()

        // Fetch data
        homeViewModel.getRandomMeal()
        homeViewModel.getOverPopularMeals()
        homeViewModel.getCategoriesHomeFragment()
    }

    private fun onCategoryItemClick() {
        categoriesHomeAdapter.onCategoryItemClick = { data ->
            val fragment = CategoryFragment()
            val bundle = Bundle()
            bundle.putString("mealCategory",data.strCategory)
            fragment.arguments = bundle
            findNavController().navigate(R.id.action_homeFragment_to_categoryFragment, bundle)
        }
    }

    private fun onOverItemClick() {
        overPopularItemsAdapter.onOverItemClick = { data ->
            val intent = Intent(context, MealActivity::class.java)
            intent.putExtra("mealId", data.idMeal)
            intent.putExtra("mealThumb", data.strMealThumb)
            intent.putExtra("mealTitle", data.strMeal)
            startActivity(intent)

        }
    }



    private fun setupRecyclerViews() {
        // Over Popular Items RecyclerView
        binding.rvOverPopularHomeFrag.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = overPopularItemsAdapter
        }

        // Categories RecyclerView
        binding.rvCategoriesHomeFrag.apply {
            layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
            adapter = categoriesHomeAdapter
        }
    }

    private fun observeHomeFragmentState() {
        homeViewModel.homeLiveData.observe(viewLifecycleOwner) {state ->
                when (state) {
                    is HomeFragmentState.Loading -> {
                        // Show loading indicator
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is HomeFragmentState.RandomMealSuccess -> {
                        binding.progressBar.visibility = View.GONE
                        displayRandomMeal(state.meal)
                    }
                    is HomeFragmentState.RandomMealError -> {
                        binding.progressBar.visibility = View.GONE
                        binding.cvHome.visibility = View.GONE

                        Log.e("HomeFragment", "Random Meal Error: ${state.error}")
                    }
                    is HomeFragmentState.OverPopularMealsSuccess -> {
                        binding.progressBar.visibility = View.GONE
                        overPopularItemsAdapter.differ.submitList(state.meals)
                    }
                    is HomeFragmentState.OverPopularMealsError -> {
                        binding.progressBar.visibility = View.GONE
                        binding.rvOverPopularHomeFrag.visibility = View.GONE
                        Log.e("HomeFragment", "Over Popular Meals Error: ${state.error}")
                    }
                    is HomeFragmentState.CategoriesSuccess -> {
                        binding.progressBar.visibility = View.GONE
                        categoriesHomeAdapter.differ.submitList(state.categories)
                    }
                    is HomeFragmentState.CategoriesError -> {
                        binding.progressBar.visibility = View.GONE
                        Log.e("HomeFragment", "Categories Error: ${state.error}")
                    }

                    else -> {}
                }
        }
    }

    private fun displayRandomMeal(meal: Meal) {
        Glide.with(this)
            .load(meal.strMealThumb)
            .into(binding.ivRandomHomeFrag)

        binding.cvHome.setOnClickListener {
            val intent = Intent(context, MealActivity::class.java).apply {
                putExtra("mealId", meal.idMeal)
                putExtra("mealTitle", meal.strMeal)
                putExtra("mealThumb", meal.strMealThumb)
            }
            startActivity(intent)
        }
    }
}
