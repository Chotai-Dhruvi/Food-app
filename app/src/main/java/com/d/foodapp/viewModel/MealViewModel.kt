package com.d.foodapp.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d.foodapp.api.MealApiService
import com.d.foodapp.db.MealDataBase
import com.d.foodapp.model.Meal
import com.d.foodapp.repository.MealRepository
import com.d.foodapp.state.MealRepositoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MealViewModel @Inject constructor(application: Application, private val mealRepository: MealRepository) : ViewModel() {

    private val _mealState = MutableLiveData<MealRepositoryState>()
    val mealState: LiveData<MealRepositoryState> get() = _mealState

    fun getMealDetails(mealId: String) {
        _mealState.value = MealRepositoryState.Loading
        viewModelScope.launch {
            runCatching {
                mealRepository.getMealDetails(mealId)
            }.onSuccess { response ->
                if (response.isSuccessful && response.body() != null) {
                    _mealState.postValue(MealRepositoryState.getMealDetailsSuccess(response))
                } else {
                    _mealState.postValue(MealRepositoryState.getMealDetailsError("Error: ${response.message()}"))
                }
            }.onFailure { exception ->
                _mealState.postValue(MealRepositoryState.getMealDetailsError("Failed to fetch meal details. Please try again later."))
                Log.e("MealViewModel", "Error fetching meal details: ${exception.message}", exception)
            }
        }
    }


    fun upsertMeal(meal: Meal) = viewModelScope.launch {
        mealRepository.upsertMeal(meal)
    }

    // fun deleteMeal(meal: Meal) = viewModelScope.launch {
    //    mealRepository.deleteMeal(meal)
    // }

    fun getMealSaved() = mealRepository.getMealSaved

}