package com.skysam.hchirinos.circulalo.ui.post

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.skysam.hchirinos.circulalo.R
import com.skysam.hchirinos.circulalo.common.Utils
import com.skysam.hchirinos.circulalo.dataClass.Category
import com.skysam.hchirinos.circulalo.databinding.FragmentDataPostBinding
import java.util.Locale

class DataPostFragment : Fragment(), TextWatcher, OnClickCategory, OnClickImage {

    private var _binding: FragmentDataPostBinding? = null
    private val binding get() = _binding!!
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var imageAdapter: ImageAdapter
    private val categories = mutableListOf<Category>()
    private val categoriesSelected = mutableListOf<Category>()
    private val images = mutableListOf<Bitmap?>()
    private var positionImage = 0


    private val requestIntentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            showImage(result.data!!)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDataPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listCategoriesArray = resources.getStringArray(R.array.categories).toList()
        listCategoriesArray.forEach {
            val categoryNew = Category("", it)
            categories.add(categoryNew)
        }
        categoryAdapter = CategoryAdapter(categories, this)
        binding.rvCategories.apply {
            setHasFixedSize(true)
            adapter = categoryAdapter
        }
        for (i in 1..8) {
            images.add(null)
        }
        imageAdapter = ImageAdapter(images, this)
        binding.rvImages.apply {
            setHasFixedSize(true)
            adapter = imageAdapter
        }

        binding.etPrice.addTextChangedListener(this)
        binding.etQuantity.addTextChangedListener(this)
        binding.etName.doAfterTextChanged { enableButtonSave() }
        binding.etPrice.doAfterTextChanged { enableButtonSave() }
        binding.etDescription.doAfterTextChanged { enableButtonSave() }
        binding.etQuantity.doAfterTextChanged { enableButtonSave() }

        binding.btnNext.setOnClickListener { findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment) }

    }

    private fun enableButtonSave() {
        if (binding.etName.text.isNullOrEmpty()) {
            binding.btnNext.isEnabled = false
            return
        }
        if (binding.etDescription.text.isNullOrEmpty()) {
            binding.btnNext.isEnabled = false
            return
        }
        if (Utils.convertStringToDouble(binding.etPrice.text.toString()) == 0.0) {
            binding.btnNext.isEnabled = false
            return
        }
        if (Utils.convertStringToDouble(binding.etQuantity.text.toString()) == 0.0) {
            binding.btnNext.isEnabled = false
            return
        }
        if (categoriesSelected.isEmpty()) {
            binding.btnNext.isEnabled = false
            return
        }
        if (images.size < 2) {
            binding.btnNext.isEnabled = false
            return
        }
        binding.btnNext.isEnabled = true
    }

    private fun showImage(it: Intent) {
        val sizeImagePreview = resources.getDimensionPixelSize(R.dimen.size_image_item)
        val bitmap = Utils.reduceBitmap(it.dataString, sizeImagePreview, sizeImagePreview)

        if (bitmap != null) {
            images[positionImage] = bitmap
            imageAdapter.notifyItemChanged(positionImage)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun categorySelected(category: Category) {
        if (category.selected) categoriesSelected.add(category) else categoriesSelected.remove(category)
        enableButtonSave()
    }

    override fun selectedImage(position: Int) {
        positionImage = position
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        requestIntentLauncher.launch(intent)
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun afterTextChanged(s: Editable?) {
        val cadena =
            String.format(Locale.GERMAN, "%,.2f", Utils.convertStringToDouble(s.toString()))
        if (s.toString() == binding.etPrice.text.toString()) {
            binding.etPrice.removeTextChangedListener(this)
            binding.etPrice.setText(cadena)
            binding.etPrice.setSelection(cadena.length)
            binding.etPrice.addTextChangedListener(this)
        }
        if (s.toString() == binding.etQuantity.text.toString()) {
            binding.etQuantity.removeTextChangedListener(this)
            binding.etQuantity.setText(cadena)
            binding.etQuantity.setSelection(cadena.length)
            binding.etQuantity.addTextChangedListener(this)
        }
    }
}