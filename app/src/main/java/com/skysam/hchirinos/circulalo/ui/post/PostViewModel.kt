package com.skysam.hchirinos.circulalo.ui.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skysam.hchirinos.circulalo.dataClass.Post

class PostViewModel : ViewModel() {

    private val _post = MutableLiveData<Post?>().apply {
        value = null
    }
    val post: LiveData<Post?> = _post

    fun confirmPost(post: Post) {
        _post.value = post
    }
}