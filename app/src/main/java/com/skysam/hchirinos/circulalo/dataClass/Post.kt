package com.skysam.hchirinos.circulalo.dataClass

import java.util.*

/**
 * Created by Hector Chirinos on 25/01/2023.
 */

data class Post(
 val id: String,
 var name: String,
 var description: String,
 var price: Double,
 var type: String,
 var datePosted: Date,
 var dateUpdated: Date,
 var userOwner: String,
 var images: MutableList<String>,
 var categories: MutableList<Category>,
 var isActive: Boolean
 )
