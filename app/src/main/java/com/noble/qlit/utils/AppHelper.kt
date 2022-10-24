package com.noble.qlit.utils

import android.content.Intent
import android.widget.Toast
import com.noble.qlit.MyApp
import es.dmoral.toasty.Toasty
import java.util.*

/**
 * @author: noble
 * @desc: AppHelper
 */

/* 范型实化
   start<ExampleActivity>(){
        putExtra()
        putExtra()
   }
 */
inline fun <reified T> start(block: Intent.() -> Unit = {}) {
    val context = MyApp.context
    val intent = Intent(context, T::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    intent.block()
    context.startActivity(intent)
}

fun String.showToast(duration: Int = Toast.LENGTH_SHORT) =
    Toasty.normal(MyApp.context, this, duration).show()


fun String.infoToast(duration: Int = Toast.LENGTH_SHORT) =
    Toasty.info(MyApp.context, this, duration, true).show()


fun String.successToast(duration: Int = Toast.LENGTH_SHORT) =
    Toasty.success(MyApp.context, this, duration, true).show()


fun String.warningToast(duration: Int = Toast.LENGTH_SHORT) =
    Toasty.warning(MyApp.context, this, duration, true).show()


fun String.errorToast(duration: Int = Toast.LENGTH_SHORT) =
    Toasty.error(MyApp.context, this, duration, true).show()

// 获取当前时间
fun getDate(): String {
    val calendar: Calendar = Calendar.getInstance()
    val y = calendar.get(Calendar.YEAR).toString()
    var m = (calendar.get(Calendar.MONTH) + 1).toString()
    var d = calendar.get(Calendar.DAY_OF_MONTH).toString()
    if (m.toInt() < 10) {
        m = "0$m"
    }
    if (d.toInt() < 10) {
        d = "0$d"
    }
    return "$y/$m/$d"
}