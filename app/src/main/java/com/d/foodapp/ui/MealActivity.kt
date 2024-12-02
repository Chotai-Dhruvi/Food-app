package com.d.foodapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.d.foodapp.R
import com.d.foodapp.databinding.ActivityMealBinding
import com.d.foodapp.model.Meal
import com.d.foodapp.state.MealRepositoryState
import com.d.foodapp.ui.fragments.HomeFragment
import com.d.foodapp.viewModel.MealViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding

    private lateinit var mealId: String
    private lateinit var mealThumb: String
    private lateinit var mealTitle: String
    private lateinit var youtubeLink: String

    private val mealViewModel: MealViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve meal information from the intent
        getMealInformation()

        // Get meal details from the ViewModel
        mealViewModel.getMealDetails(mealId)

        // Observe the state of the meal details
        observeGetMealDetail()

        // Handle favorite button click
        binding.btnFavourite.setOnClickListener {
            saveMeal?.let { meal ->
                Log.d("Meal Response", meal.toString())
                mealViewModel.upsertMeal(meal)

                // Show a Toast message when the dish is liked
                Toast.makeText(this, "Dish Liked Successfully", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private var saveMeal: Meal? = null

    // Observes meal details and handles different states
    private fun observeGetMealDetail() {
        mealViewModel.mealState.observe(this, Observer { state ->
            when (state) {
                is MealRepositoryState.Loading -> {
                    // Show a loading spinner or progress bar
                    binding.progressBar.visibility = android.view.View.VISIBLE
                }
                is MealRepositoryState.getMealDetailsSuccess -> {
                    // Hide loading and display meal details
                    binding.progressBar.visibility = android.view.View.GONE

                    // Check if the response body contains a list of meals
                    val mealList = state.res.body()
                    if (mealList != null) {
                        saveMeal = mealList.meals[0]  // Assuming the first item is the desired meal

                        // Access the first meal in the list
                        val meal = mealList.meals[0]
                        binding.tvCategoryMeal.text = "Category: " + meal.strCategory
                        binding.tvLocationMeal.text = "Area: " + meal.strArea
                        binding.tvDetailsInstructionsMeal.text = meal.strInstructions
                        youtubeLink = meal.strYoutube.toString()

                        // Set up the YouTube link click
                        binding.imgYoutube.setOnClickListener {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
                            startActivity(intent)
                        }
                    }
                }
                is MealRepositoryState.getMealDetailsError -> {
                    // Hide loading and show an error message
                    binding.progressBar.visibility = android.view.View.GONE
                    Log.e("MealActivity", "Error: ${state.error}")
                    binding.tvErrorMessage.text = "Failed to load meal details. Please try again later."
                    binding.tvErrorMessage.visibility = android.view.View.VISIBLE
                }
            }
        })
    }


    // Retrieves the meal information from the intent
    private fun getMealInformation() {
        val intent = intent
        mealId = intent.getStringExtra("mealId").toString()
        mealThumb = intent.getStringExtra("mealThumb").toString()
        mealTitle = intent.getStringExtra("mealTitle").toString()

        // Load the meal image using Glide
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.ivMeal)

        // Set the meal title
        binding.collapsing.title = mealTitle
        Log.d("MealActivity", "Meal ID: $mealId")
    }
}