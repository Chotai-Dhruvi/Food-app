package com.d.foodapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.d.foodapp.Adapters.CategoriesHomeAdapter
import com.d.foodapp.R
import com.d.foodapp.databinding.FragmentCategoriesBinding
import com.d.foodapp.databinding.FragmentCategoryBinding
import com.d.foodapp.state.CategoriesFragmentState
import com.d.foodapp.viewModel.CategoriesFragmentViewModel
import com.d.foodapp.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private val categoriesFragmentViewModel: CategoriesFragmentViewModel by viewModels()

    private lateinit var categoriesHomeAdapter: CategoriesHomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
                binding = FragmentCategoriesBinding.inflate(inflater, container, false)
                return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoriesHomeAdapter = CategoriesHomeAdapter()

        setupRecyclerViews()
        observeCategoriesFragmentState()
        onCategoryItemClick()

        categoriesFragmentViewModel.getCategoriesHomeFragment()
    }

    private fun onCategoryItemClick() {
        categoriesHomeAdapter.onCategoryItemClick = { data ->
            val fragment = CategoryFragment()
            val bundle = Bundle()
            bundle.putString("mealCategory",data.strCategory)
            fragment.arguments = bundle
            findNavController().navigate(R.id.action_categoriesFragment_to_categoryFragment, bundle)
        }
    }


    private fun observeCategoriesFragmentState() {
        categoriesFragmentViewModel.categoriesLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is CategoriesFragmentState.CategoriesError -> {
                    binding.progressBar.visibility = View.GONE
                    // Add an error message or debug here
                    Log.e("CategoriesFragment", "Error: ${state.error}")
                }
                is CategoriesFragmentState.CategoriesSuccess -> {
                    binding.progressBar.visibility = View.GONE
                    categoriesHomeAdapter.differ.submitList(state.categories)
                    Log.d("CategoriesFragment", "Data loaded: ${state.categories.size} items")
                }
                CategoriesFragmentState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }


    private fun setupRecyclerViews() {
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
            adapter = categoriesHomeAdapter
        }
    }


}