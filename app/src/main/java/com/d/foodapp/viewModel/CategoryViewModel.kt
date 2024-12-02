package com.d.foodapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d.foodapp.model.OverPopularItem
import com.d.foodapp.repository.CategoryFragmentRepository
import com.d.foodapp.state.CategoryFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: CategoryFragmentRepository
): ViewModel() {

    private var _categoryLiveData = MutableLiveData<CategoryFragmentState>()
    var categoryLiveData: LiveData<CategoryFragmentState> = _categoryLiveData

    fun getCategory(categoryName: String) {
        viewModelScope.launch {
            _categoryLiveData.postValue(CategoryFragmentState.Loading)
            Log.d("CategoryViewModel", "Loading state triggered")

            runCatching {
                repository.getCategory(categoryName)
            }.onSuccess { response ->
                if (response.isSuccessful && response.body() != null) {
                    val items = response.body()!!.overMeals
                    _categoryLiveData.postValue(CategoryFragmentState.Success(items))
                } else {
                    _categoryLiveData.postValue(CategoryFragmentState.Error("Failed to fetch data"))
                }
            }.onFailure { throwable ->
                Log.d("CategoryViewModel", throwable.message.toString())
                _categoryLiveData.postValue(
                    CategoryFragmentState.Error(
                        throwable.message ?: "Unknown error occurred"
                    )
                )
            }
        }
    }
}