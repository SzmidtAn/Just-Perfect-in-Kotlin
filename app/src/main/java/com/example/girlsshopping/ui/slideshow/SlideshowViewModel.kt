package com.example.girlsshopping.ui.slideshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SlideshowViewModel : ViewModel() {
    private val mText: MutableLiveData<String?>
    val text: LiveData<String?>
        get() = mText

    init {
        mText = MutableLiveData()
        mText.value = "This is slideshow fragment"
    }
}