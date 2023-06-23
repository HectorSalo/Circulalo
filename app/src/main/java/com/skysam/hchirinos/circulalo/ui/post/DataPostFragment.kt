package com.skysam.hchirinos.circulalo.ui.post

import android.app.Activity
import android.content.Intent
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
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.skysam.hchirinos.circulalo.R
import com.skysam.hchirinos.circulalo.common.Permission
import com.skysam.hchirinos.circulalo.common.Utils
import com.skysam.hchirinos.circulalo.dataClass.Category
import com.skysam.hchirinos.circulalo.dataClass.Post
import com.skysam.hchirinos.circulalo.databinding.FragmentDataPostBinding
import com.skysam.hchirinos.circulalo.ui.common.ExitDialog
import com.skysam.hchirinos.circulalo.ui.common.OnClickExit
import java.util.Date
import java.util.Locale

class DataPostFragment : Fragment(), TextWatcher, OnClickCategory, OnClickImage, OnClickExit {

    private var _binding: FragmentDataPostBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PostViewModel by activityViewModels()
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var imageAdapter: ImageAdapter
    private val categories = mutableListOf<Category>()
    private val categoriesSelected = mutableListOf<Category>()
    private val images = mutableListOf<String?>()
    private var positionImage = 0

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            findNavController().navigate(R.id.DataPostToCamera)
        } else {
            Snackbar.make(binding.root, getString(R.string.error_permission_read), Snackbar.LENGTH_SHORT)
                .setAnchorView(R.id.btn_next).show()
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
        images.add(null)
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

        binding.fab.setOnClickListener {
            if (Permission.checkPermissionCamera()) {
                findNavController().navigate(R.id.DataPostToCamera)
            } else {
                requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
            }
        }
        binding.btnNext.setOnClickListener { sendData() }
        binding.btnBack.setOnClickListener {
            val exitDialog = ExitDialog(this)
            exitDialog.show(requireActivity().supportFragmentManager, tag)
        }
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
        if (images.size < 3) {
            binding.btnNext.isEnabled = false
            return
        }
        binding.btnNext.isEnabled = true
        Utils.close(binding.root)
    }

    private fun sendData() {
        val newPost = Post(
            "",
            binding.etName.text.toString(),
            binding.etDescription.text.toString(),
            Utils.convertStringToDouble(binding.etPrice.text.toString()),
            Utils.convertStringToDouble(binding.etQuantity.text.toString()),
            Date(),
            Date(),
            "",
            images,
            categoriesSelected,
            true
        )
        viewModel.confirmPost(newPost)
        requireActivity().finish()
        //findNavController().navigate(R.id.dataPostToPostFragment)
    }

    private fun showImage(it: Intent) {
        images[positionImage] = it.dataString
        if (images.size < 8) {
            images.add(null)
        }
        imageAdapter.notifyItemChanged(positionImage)
        binding.rvImages.scrollToPosition(positionImage + 1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun categorySelected(category: Category) {
        if (category.selected) categoriesSelected.add(category) else categoriesSelected.remove(category)
        enableButtonSave()
    }

    override fun selectedImage(position: Int, remove: Boolean) {
        positionImage = position
        if (!remove) {

        } else {
            imageAdapter.notifyItemRemoved(position)
            images.removeAt(position)
        }
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

    override fun onClickExit() {
        requireActivity().finish()
    }
}