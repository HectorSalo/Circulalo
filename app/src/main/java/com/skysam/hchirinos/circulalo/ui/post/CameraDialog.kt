package com.skysam.hchirinos.circulalo.ui.post

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.skysam.hchirinos.circulalo.R
import com.skysam.hchirinos.circulalo.databinding.FragmentCameraBinding
import java.nio.ByteBuffer
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors


/**
 * Created by Hector Chirinos on 25/06/2023.
 */

class CameraDialog: DialogFragment() {
 private var _binding: FragmentCameraBinding? = null
 private val binding get() = _binding!!
 private val viewModel: PostViewModel by activityViewModels()
 private val cameraFacing = CameraSelector.LENS_FACING_BACK

 private val requestIntentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
  if (result.resultCode == Activity.RESULT_OK) {
   viewModel.showImage(result.data!!)
   dismiss()
  }
 }

 override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  setStyle(
   STYLE_NORMAL,
   com.google.android.material.R.style.ShapeAppearanceOverlay_MaterialComponents_MaterialCalendar_Window_Fullscreen
  )
 }

 override fun onCreateView(
  inflater: LayoutInflater, container: ViewGroup?,
  savedInstanceState: Bundle?
 ): View {
  _binding = FragmentCameraBinding.inflate(inflater, container, false)
  return binding.root
 }

 override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
  super.onViewCreated(view, savedInstanceState)
  startCamera()
 }

 private fun startCamera() {
  val listenableFuture = ProcessCameraProvider.getInstance(requireContext())

  listenableFuture.addListener({
   try {
    val cameraProvider = listenableFuture.get()
    val preview = Preview.Builder().build()
    val imageCapture = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
     ImageCapture.Builder()
      .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
      .setTargetRotation(requireActivity().display!!.rotation).build()
    } else {
     ImageCapture.Builder()
      .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
      .setTargetRotation(requireActivity().windowManager.defaultDisplay.rotation)
      .build()
    }
    val cameraSelector = CameraSelector.Builder()
     .requireLensFacing(cameraFacing).build()
    cameraProvider.unbindAll()
    val camera: Camera =
     cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
    preview.setSurfaceProvider(binding.preview.surfaceProvider)

    if (!camera.cameraInfo.hasFlashUnit()) {
     binding.btnFlash.visibility = View.GONE
    }
    binding.btnFlash.setOnClickListener {
     setFlash(camera)
    }
    binding.btnGallery.setOnClickListener {
     val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
     requestIntentLauncher.launch(intent)
    }
    binding.btnClose.setOnClickListener { dismiss() }

    binding.btnCapture.setOnClickListener {
     imageCapture.takePicture(Executors.newSingleThreadExecutor(), @ExperimentalGetImage object :
      ImageCapture.OnImageCapturedCallback() {
      override fun onCaptureSuccess(image: ImageProxy) {
       super.onCaptureSuccess(image)
       image.use {
        val bitmap: Bitmap = imageProxyToBitmap(image)
        viewModel.photo.postValue(bitmap)
       }
       dismiss()
      }
      override fun onError(exception: ImageCaptureException) {
       Log.e("ImageCapture", "Failed to capture image: $exception")
       super.onError(exception)
      }
     })
    }

   } catch (e: ExecutionException) {
    e.printStackTrace()
   } catch (e: InterruptedException) {
    e.printStackTrace()
   }
  }, ContextCompat.getMainExecutor(requireContext()))
 }

 private fun setFlash(camera: Camera) {
  if (camera.cameraInfo.torchState.value == 0) {
   camera.cameraControl.enableTorch(true)
   binding.btnFlash.setImageResource(R.drawable.ic_flash_on_24)
  } else {
   camera.cameraControl.enableTorch(false)
   binding.btnFlash.setImageResource(R.drawable.ic_flash_off_24)
  }
 }

 private fun imageProxyToBitmap(image: ImageProxy): Bitmap {
  val buffer: ByteBuffer = image.planes[0].buffer
  val bytes = ByteArray(buffer.remaining())
  buffer.get(bytes)
  return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
 }

 override fun onDestroyView() {
  super.onDestroyView()
  _binding = null
 }
}