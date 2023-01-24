package com.skysam.hchirinos.circulalo.common

import android.app.Application
import android.content.Context

/**
 * Created by Hector Chirinos on 22/01/2023.
 */

class Circulalo: Application() {
 companion object {
  lateinit var appContext: Context
 }

 override fun onCreate() {
  super.onCreate()
  appContext = applicationContext
 }

 object Circulalo {
  fun getContext(): Context = appContext
 }
}