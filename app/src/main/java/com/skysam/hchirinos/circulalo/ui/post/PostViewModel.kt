package com.skysam.hchirinos.circulalo.ui.post

import android.content.Intent
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skysam.hchirinos.circulalo.dataClass.Post

class PostViewModel : ViewModel() {

    private val _post = MutableLiveData<Post?>().apply {
        value = null
    }
    val post: LiveData<Post?> = _post

    private val _image = MutableLiveData<Intent?>().apply {
        value = null
    }
    val image: LiveData<Intent?> = _image

    val photo: MutableLiveData<Bitmap?> by lazy {
        MutableLiveData<Bitmap?>()
    }

    fun confirmPost(post: Post) {
        _post.value = post
    }

    fun showImage(intent: Intent) {
        _image.value = intent
    }
}