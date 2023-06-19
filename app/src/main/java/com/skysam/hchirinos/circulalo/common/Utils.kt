package com.skysam.hchirinos.circulalo.common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.skysam.hchirinos.circulalo.BuildConfig
import com.skysam.hchirinos.circulalo.R
import java.io.FileNotFoundException
import java.text.DateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.ceil
import kotlin.math.max

/**
 * Created by Hector Chirinos on 18/06/2023.
 */

object Utils {
 fun getEnviroment(): String {
  return BuildConfig.BUILD_TYPE
 }

 fun close(view: View) {
  val imn = Circulalo.Circulalo.getContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
  imn.hideSoftInputFromWindow(view.windowToken, 0)
 }

 fun convertDoubleToString(value: Double): String {
  return String.format(Locale.GERMANY, "%,.2f", value)
 }

 fun convertStringToDouble(value: String): Double {
  val valueS = value.replace(",", "").replace(".", "")
  return valueS.toDouble() / 100
 }

 fun convertDateToString(value: Date): String {
  return DateFormat.getDateInstance().format(value)
 }

 fun reduceBitmap(
  uri: String?,
  maxAncho: Int,
  maxAlto: Int
 ): Bitmap? {
  return try {
   val options = BitmapFactory.Options()
   options.inJustDecodeBounds = true
   BitmapFactory.decodeStream(
    Circulalo.Circulalo.getContext().contentResolver.openInputStream(Uri.parse(uri)),
    null, options
   )
   options.inSampleSize = max(
    ceil(options.outWidth / maxAncho.toDouble()),
    ceil(options.outHeight / maxAlto.toDouble())
   ).toInt()
   options.inJustDecodeBounds = false
   BitmapFactory.decodeStream(
    Circulalo.Circulalo.getContext().contentResolver
     .openInputStream(Uri.parse(uri)), null, options
   )
  } catch (e: FileNotFoundException) {
   e.printStackTrace()
   Toast.makeText(Circulalo.Circulalo.getContext(), R.string.error_image_notfound, Toast.LENGTH_SHORT).show()
   null
  }
 }
}