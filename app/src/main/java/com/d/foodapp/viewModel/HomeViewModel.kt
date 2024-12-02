package com.d.foodapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.d.foodapp.model.Category
import com.d.foodapp.model.Meal
import com.d.foodapp.model.OverPopularItem
import com.d.foodapp.repository.HomeRepository
import com.d.foodapp.state.HomeFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    private val _homeLiveData = MutableLiveData<HomeFragmentState>()
    val homeLiveData: LiveData<HomeFragmentState> = _homeLiveData

    fun getRandomMeal() {
        viewModelScope.launch {
            _homeLiveData.postValue(HomeFragmentState.Loading)

            runCatching {
                repository.getRandomMeal()
            }.onSuccess { response ->
                if (response.isSuccessful && response.body() != null) {
                    response.body()?.meals?.let {
                        _homeLiveData.postValue(HomeFragmentState.RandomMealSuccess(it[0]))
                    } ?: run {
                        _homeLiveData.postValue(HomeFragmentState.RandomMealError("No data found"))
                    }
                } else {
                    _homeLiveData.postValue(HomeFragmentState.RandomMealError("API error: ${response.code()}"))
                }
            }.onFailure { throwable ->
                _homeLiveData.postValue(HomeFragmentState.RandomMealError(throwable.message ?: "Unknown error"))
            }
        }
    }

    fun getOverPopularMeals() {
        viewModelScope.launch {
            _homeLiveData.postValue(HomeFragmentState.Loading)

            runCatching {
                repository.getOverPopularMeals("Seafood")
            }.onSuccess { response ->
                if (response.isSuccessful && response.body() != null) {
                    response.body()?.overMeals?.let {
                        _homeLiveData.postValue(HomeFragmentState.OverPopularMealsSuccess(it))
                    } ?: run {
                        _homeLiveData.postValue(HomeFragmentState.OverPopularMealsError("No data found"))
                    }
                } else {
                    _homeLiveData.postValue(HomeFragmentState.OverPopularMealsError("API error: ${response.code()}"))
                }
            }.onFailure { throwable ->
                _homeLiveData.postValue(HomeFragmentState.OverPopularMealsError(throwable.message ?: "Unknown error"))
            }
        }
    }

    fun getCategoriesHomeFragment() {
        viewModelScope.launch {
            _homeLiveData.postValue(HomeFragmentState.Loading)

            runCatching {
                repository.getCategoriesHomeFragment()
            }.onSuccess { response ->
                if (response.isSuccessful && response.body() != null) {
                    response.body()?.categories?.let {
                        _homeLiveData.postValue(HomeFragmentState.CategoriesSuccess(it))
                    } ?: run {
                        _homeLiveData.postValue(HomeFragmentState.CategoriesError("No data found"))
                    }
                } else {
                    _homeLiveData.postValue(HomeFragmentState.CategoriesError("API error: ${response.code()}"))
                }
            }.onFailure { throwable ->
                _homeLiveData.postValue(HomeFragmentState.CategoriesError(throwable.message ?: "Unknown error"))
            }
        }
    }
}
