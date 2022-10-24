package com.noble.qlit.ui.activity.results

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noble.qlit.data.bean.Course
import com.noble.qlit.data.repository.RemoteRepository
import com.noble.qlit.utils.ActivityCollector
import com.noble.qlit.utils.LogUtil
import com.noble.qlit.utils.start
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

/**
 * @author: noble
 * @desc: ResultsViewModel
 */
class ResultsViewModel : ViewModel() {

    var bitmapFlag: Boolean = false
    var isLogin: Boolean = false
    var bitmap = mutableStateOf(ImageBitmap(200, 200))
    var yzm = mutableStateOf("")

    var termCode = mutableListOf<String>()
    var termGrade = mutableListOf<List<Course>>()
    var isLoaded = mutableStateOf(false)

    private suspend fun separatePages() {
        val grades = RemoteRepository.getGrades()
        val terms: MutableMap<String, MutableList<Course>> = mutableMapOf()
        grades?.let { it ->
            it.sortByDescending {
                it.academicYearCode
            }
            for (grade in it) {
                val termCode = grade.academicYearCode
                var tmp = mutableListOf<Course>()
                if (terms.containsKey(termCode)) {
                    tmp = terms[termCode]!!
                }
                tmp.add(grade)
                terms[termCode] = tmp
            }
        }
        for ((key, value) in terms) {
            termCode.add(key)
            value.sortBy { it.courseAttributeName }
            termGrade.add(value)
        }
    }

    fun getResults() {
        viewModelScope.launch {
            separatePages()
            isLoaded.value = true
            LogUtil.d("ResultsActivity", "成功获得结果")
        }
    }

    fun attemptLogin() {
        viewModelScope.launch {
            if (RemoteRepository.login(yzm.value)) {
                gotoResults()
            }else{
                getCaptchaPic()
                yzm.value = ""
            }
        }
    }

    private fun gotoResults() {
        ActivityCollector.finishTopActivity()
        start<ResultsActivity>() {
            putExtra("isLogin", true)
        }
    }

    fun getCaptchaPic() {
        thread {
            RemoteRepository.getCaptchaPic()?.let {
                bitmap.value = it.asImageBitmap()
            }
        }
        bitmapFlag = true
    }
}