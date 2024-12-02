package com.d.foodapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.d.foodapp.Adapters.CategoryFragmentAdapter
import com.d.foodapp.databinding.FragmentCategoryBinding
import com.d.foodapp.state.CategoryFragmentState
import com.d.foodapp.viewModel.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var categoryName: String

    private val categoryViewModel: CategoryViewModel by viewModels()
    private lateinit var categoryFragmentAdapter: CategoryFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryFragmentAdapter = CategoryFragmentAdapter()
        getDataByBundle()
        categoryViewModel.getCategory(categoryName) // Trigger data fetch
        setUpCategoryRecView()
        observeCategoryState()

        }

    private fun observeCategoryState() {
        // Observe the state changes from the ViewModel
        categoryViewModel.categoryLiveData.observe(viewLifecycleOwner) {state ->
                when (state) {
                    is CategoryFragmentState.Loading -> {
                        // Show loading indicator
                        binding.progressBar.visibility = View.VISIBLE
                        binding.rvCategoryFragment.visibility = View.GONE
                        binding.tvErrorMessage.visibility = View.GONE
                    }
                    is CategoryFragmentState.Success -> {
                        // Populate RecyclerView with data
                        binding.progressBar.visibility = View.GONE
                        binding.rvCategoryFragment.visibility = View.VISIBLE
                        binding.tvErrorMessage.visibility = View.GONE
                        categoryFragmentAdapter.differ.submitList(state.items)
                    }
                    is CategoryFragmentState.Error -> {
                        // Show error message
                        binding.progressBar.visibility = View.GONE
                        binding.rvCategoryFragment.visibility = View.GONE
                        binding.tvErrorMessage.visibility = View.VISIBLE
                        binding.tvErrorMessage.text = state.message
                    }
                }
        }
    }

    private fun setUpCategoryRecView() {
        binding.rvCategoryFragment.apply {
            layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
            adapter = categoryFragmentAdapter
        }
    }


    private fun getDataByBundle() {
        val data = arguments
        if (data != null){
            categoryName = data.getString("mealCategory").toString()
    }
}
}