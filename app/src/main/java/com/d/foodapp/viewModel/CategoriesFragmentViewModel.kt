package com.d.foodapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d.foodapp.repository.CategoriesFragmentRepository
import com.d.foodapp.repository.HomeRepository
import com.d.foodapp.state.CategoriesFragmentState
import com.d.foodapp.state.HomeFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesFragmentViewModel @Inject constructor(
    private val repository: CategoriesFragmentRepository
) : ViewModel() {
    private val _categoriesLiveData = MutableLiveData<CategoriesFragmentState>()
    val categoriesLiveData: LiveData<CategoriesFragmentState> = _categoriesLiveData

    fun getCategoriesHomeFragment() {
        viewModelScope.launch {
            _categoriesLiveData.postValue(CategoriesFragmentState.Loading)

            runCatching {
                repository.getCategoriesHomeFragment()
            }.onSuccess { response ->
                if (response.isSuccessful && response.body() != null) {
                    response.body()?.categories?.let {
                        _categoriesLiveData.postValue(CategoriesFragmentState.CategoriesSuccess(it))
                    } ?: run {
                        _categoriesLiveData.postValue(CategoriesFragmentState.CategoriesError("No data found"))
                    }
                } else {
                    _categoriesLiveData.postValue(CategoriesFragmentState.CategoriesError("API error: ${response.code()}"))
                }
            }.onFailure { throwable ->
                _categoriesLiveData.postValue(CategoriesFragmentState.CategoriesError(throwable.message ?: "Unknown error"))
            }
        }
    }
}