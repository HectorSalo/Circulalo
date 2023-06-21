package com.skysam.hchirinos.circulalo.ui.post

import com.skysam.hchirinos.circulalo.R
import java.util.Arrays

object CarouselData {
    fun createItems(): List<CarouselItem>? {
        return listOf<CarouselItem>(
            CarouselItem(R.drawable.image_1, R.string.cat_carousel_image_1_content_desc),
            CarouselItem(R.drawable.image_2, R.string.cat_carousel_image_2_content_desc),
            CarouselItem(R.drawable.image_3, R.string.cat_carousel_image_3_content_desc),
            CarouselItem(R.drawable.image_4, R.string.cat_carousel_image_4_content_desc),
            CarouselItem(R.drawable.image_5, R.string.cat_carousel_image_5_content_desc),
            CarouselItem(R.drawable.image_6, R.string.cat_carousel_image_6_content_desc),
            CarouselItem(R.drawable.image_7, R.string.cat_carousel_image_7_content_desc),
            CarouselItem(R.drawable.image_8, R.string.cat_carousel_image_8_content_desc),
            CarouselItem(R.drawable.image_9, R.string.cat_carousel_image_9_content_desc),
            CarouselItem(R.drawable.image_10, R.string.cat_carousel_image_10_content_desc)
        )
    }
}