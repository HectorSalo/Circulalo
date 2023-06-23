package com.skysam.hchirinos.circulalo.common

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

/**
 * Created by Hector Chirinos on 22/01/2023.
 */

object Permission {
 fun checkPermissionReadStorage(): Boolean {
  val result = ContextCompat.checkSelfPermission(Circulalo.Circulalo.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
  return result == PackageManager.PERMISSION_GRANTED
 }

 fun checkPermissionCamera(): Boolean {
  val result = ContextCompat.checkSelfPermission(Circulalo.Circulalo.getContext(), Manifest.permission.CAMERA)
  return result == PackageManager.PERMISSION_GRANTED
 }
}