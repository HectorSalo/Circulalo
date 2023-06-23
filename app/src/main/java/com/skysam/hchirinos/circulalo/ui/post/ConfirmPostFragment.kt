package com.skysam.hchirinos.circulalo.ui.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.carousel.CarouselLayoutManager
import com.skysam.hchirinos.circulalo.databinding.FragmentConfirmPostBinding

class ConfirmPostFragment : Fragment() {

    private var _binding: FragmentConfirmPostBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfirmPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*val list = CarouselData.createItems()
        val adapterK = CarouselAdapter(list)
        val carouselLayoutManager = CarouselLayoutManager()
        binding.rvImages.apply {
            layoutManager = carouselLayoutManager
            adapter = adapterK
            isNestedScrollingEnabled = false
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}