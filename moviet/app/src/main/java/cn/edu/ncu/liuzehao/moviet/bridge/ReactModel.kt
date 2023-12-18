package cn.edu.ncu.liuzehao.moviet.bridge

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReactModel: ViewModel() {

    val isReactNative: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean> ()
    }

    fun setTrue() {
        isReactNative.value = true
    }

    fun setFalse() {
        isReactNative.value = false
    }
}