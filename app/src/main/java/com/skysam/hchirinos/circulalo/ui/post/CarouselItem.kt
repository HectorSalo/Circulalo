package com.skysam.hchirinos.circulalo.ui.post

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

class CarouselItem(@DrawableRes private val drawableRes: Int, @StringRes private val contentDescRes: Int) {

    @DrawableRes
    fun getDrawableRes(): Int {
        return drawableRes
    }

    @StringRes
    fun getContentDescRes(): Int {
        return contentDescRes
    }
}