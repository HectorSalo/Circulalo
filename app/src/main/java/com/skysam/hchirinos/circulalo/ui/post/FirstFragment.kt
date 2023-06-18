package com.skysam.hchirinos.circulalo.ui.post

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.skysam.hchirinos.circulalo.R
import com.skysam.hchirinos.circulalo.dataClass.Category
import com.skysam.hchirinos.circulalo.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var categoryAdapter: CategoryAdapter
    private val categories = mutableListOf<Category>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listCategoriesArray = resources.getStringArray(R.array.categories).toList()
        listCategoriesArray.forEach {
            val categoryNew = Category("", it)
            categories.add(categoryNew)
        }
        categoryAdapter = CategoryAdapter(categories)
        binding.rvCategories.apply {
            setHasFixedSize(true)
            adapter = categoryAdapter
        }

        val gd = GradientDrawable()
        gd.shape = GradientDrawable.RECTANGLE
        gd.setStroke(3, resources.getColor(R.color.purple_500))
        binding.iv1.background = gd

        binding.btnNext.setOnClickListener { findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment) }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}