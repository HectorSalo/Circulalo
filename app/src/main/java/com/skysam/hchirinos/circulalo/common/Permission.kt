package com.skysam.hchirinos.circulalo.common

import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

/**
 * Created by Hector Chirinos on 22/01/2023.
 */

object Permission {
 fun checkPermission(): Boolean {
  val result = ContextCompat.checkSelfPermission(Circulalo.Circulalo.getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
  return result == PackageManager.PERMISSION_GRANTED
 }
}