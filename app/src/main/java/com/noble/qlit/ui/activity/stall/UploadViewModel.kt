package com.noble.qlit.ui.activity.stall

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Calendar

/**
 * @author: NobleXL
 * @desc: UploadViewModel
 */
class UploadViewModel : ViewModel() {

    var imageUri by mutableStateOf<Uri?>(null)
    var imgUri by mutableStateOf("")
    var itemName by mutableStateOf("")
    var itemPrice by mutableStateOf("")
    var itemPhone by mutableStateOf("")
    var itemVX by mutableStateOf("")
    var itemQQ by mutableStateOf("")

    private val apiService = StallService.instance()

    fun fetchInfo() {
        val apiEntity = StallEntity(imgUri, itemName, itemPrice, getDate(), itemPhone.toInt(), itemVX, itemQQ.toInt())
        viewModelScope.launch {
            val res = apiService.add(apiEntity)
            Timber.i("返回值：$res")
        }
    }

    fun fetchInfo2() {
        val apiEntity = StallEntity(imgUri, itemName, itemPrice, getDate(), itemPhone.toInt(), itemVX, itemQQ.toInt())
        viewModelScope.launch {
            val res = apiService.add2(apiEntity)
            Timber.i("返回值：$res")
        }
    }

    // 获取当前时间
    fun getDate(): String {
        val calendar: Calendar = Calendar.getInstance()
        val y = calendar.get(Calendar.YEAR).toString()
        var m = (calendar.get(Calendar.MONTH) + 1).toString()
        var d = calendar.get(Calendar.DAY_OF_MONTH).toString()
        var h = calendar.get(Calendar.HOUR_OF_DAY).toString()
        var min = calendar.get(Calendar.MINUTE).toString()
        if (m.toInt() < 10) {
            m = "0$m"
        }
        if (d.toInt() < 10) {
            d = "0$d"
        }
        if (h.toInt() < 10) {
            h = "0$h"
        }
        if (min.toInt() < 10) {
            min = "0$min"
        }
        return "$y/$m/$d $h:$min"
    }

}