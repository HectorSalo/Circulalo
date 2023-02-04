package com.skysam.hchirinos.circulalo.dataClass

import java.util.*

/**
 * Created by Hector Chirinos on 25/01/2023.
 */

data class Product(
 val id: String,
 var name: String,
 var price: Double,
 var type: String,
 var datePosted: Date,
 var userOwner: String,
 var images: MutableList<String>,
 var isActive: Boolean
 )
