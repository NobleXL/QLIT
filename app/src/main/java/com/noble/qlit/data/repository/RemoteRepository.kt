package com.noble.qlit.data.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.noble.qlit.MyApp
import com.noble.qlit.R
import com.noble.qlit.data.bean.Course
import com.noble.qlit.data.remote.EasyOkhttp
import com.noble.qlit.ui.activity.healthy.HealthyViewModel
import com.noble.qlit.utils.DataManager
import com.noble.qlit.utils.LogUtil
import com.noble.qlit.utils.errorToast
import com.noble.qlit.utils.successToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.FormBody
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.InputStream
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.math.log

/**
 * @author: noble
 * @desc: 网络请求
 */
object RemoteRepository {

    private const val BASE_URL = "http://60.216.38.157:881/"
    private const val captchaPicUrl = BASE_URL + "img/captcha.jpg"
    private const val loginUrl = BASE_URL + "j_spring_security_check"
    private const val gradesUrl =
        BASE_URL + "student/integratedQuery/scoreQuery/allPassingScores/callback"

    private const val HEALTHY_URL = "http://pass.qlit.edu.cn/student/"
    private const val H_captchaPicUrl =
        HEALTHY_URL + "savelight/view/common/help/yanzhengma/make.jsp"
    private const val H_loginUrl = HEALTHY_URL + "savelight/view/homeManager/loginValidate.jsp"
    private const val H_admin = HEALTHY_URL + "mobile/admin.jsp"
    private const val healthReport = HEALTHY_URL + "student/healthReport/healthReport.jsp"

    var pic by mutableStateOf(false)

    private const val TAG = "HTTP"

    // 获取成绩
    suspend fun getGrades(): MutableList<Course>? {
        val gradesinfos: MutableList<Course> = mutableListOf()
        try {
            val gradeshtml = EasyOkhttp.request(gradesUrl)
            val test = JSONObject(gradeshtml).getJSONArray("lnList")
            for (i in 0 until test.length()) {
                val lessons = JSONArray(test.toString()).optJSONObject(i).getJSONArray("cjList")
                Log.d(TAG, "getGrades: $lessons")
                for (i in 0 until lessons.length()) {
                    val lesson = JSONObject(lessons[i].toString())
                    val course = Course()
                    course.courseName = lesson.getString("courseName")
                    course.courseAttributeName = lesson.getString("courseAttributeName")
                    course.courseScore = lesson.getString("courseScore")
                    course.gradePointScore = lesson.getString("gradePointScore")
                    course.credit = lesson.getString("credit")
                    course.academicYearCode =
                        lesson.getString("academicYearCode") + "-" + lesson.getInt("termCode")
                    gradesinfos.add(course)
                }
            }
            LogUtil.i(TAG, "成功获得成绩")
            return gradesinfos
        } catch (e: Exception) {
            LogUtil.d(TAG, "无法获得成绩")
            e.printStackTrace()
            return null
        }
    }

    // 教务系统登录
    suspend fun login(yzm: String): Boolean {
        val loginData = getLoginData(yzm) ?: return false
        return try {
            val html = EasyOkhttp.request(loginUrl, loginData)
            if ("<title>URP综合教务系统首页</title>" in html) {
                LogUtil.d(TAG, "--> 登录成功")
                "登录成功".successToast()
                true
            } else {
                LogUtil.d(TAG, "--> 登录失败")
                "验证码或信息错误".errorToast()
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtil.d(TAG, "--> 登录失败")
            "服务器错误".errorToast()
            false
        }
    }

    // 获取帐号密码
    private suspend fun getLoginData(yzm: String): RequestBody? {
        val account = DataManager.readData("account", "")
        val password = DataManager.readData("password", "")
        if (account == "" || password == "") {
            MyApp.context.getString(R.string.results_config_null).errorToast()
            return null
        }
        return FormBody.Builder()
            .add("j_username", account)
            .add("j_password", md5(password))
            .add("j_captcha", yzm)
            .build()
    }

    // 健康打卡登录
    suspend fun HealthyLogin(yzm: String): Boolean {
        val loginData = getHealthyLoginData(yzm) ?: return false
        return try {
            EasyOkhttp.request(H_loginUrl, loginData)
            val html = EasyOkhttp.request(H_admin)
            if ("<title>智慧化校园平台</title>" in html) {
                LogUtil.d(TAG, "--> 登录成功")
                "登录成功".successToast()
                val healthyData = getHealthyMessage() ?: return false
                EasyOkhttp.request(healthReport, healthyData)
                true
            } else {
                LogUtil.d(TAG, "--> 登录失败")
                "验证码或信息错误".errorToast()
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtil.d(TAG, "--> 登录失败")
            "服务器错误".errorToast()
            false
        }
    }

    // 获取健康打卡账号密码
    private suspend fun getHealthyLoginData(yzm: String): RequestBody? {
        val idcard = DataManager.readData("IDcard", "")
        val idend = DataManager.readData("ID_end", "")
        if (idcard == "" || idend == "") {
            MyApp.context.getString(R.string.results_config_null).errorToast()
            return null
        }
        return FormBody.Builder()
            .add("name", idcard)
            .add("password", idend)
            .add("veryCode", yzm)
            .build()
    }

    // 获取健康打卡信息
    private suspend fun getHealthyMessage(): RequestBody? {
        val array = arrayOfNulls<String?>(9)
        val html2 = EasyOkhttp.request(healthReport)
        Log.d(TAG, "getHealthyMessage: $html2")
        val document: Document = Jsoup.parse(html2)
        val elements: Elements = document
            .select("body[style=text-align: center; margin: 0 auto; padding: 0;]")
            .select("form[method=post]")
            .select("input")
        for (i in 0..8) {
            val element: Element = elements.get(i)
            val data = element.toString().substringAfter("value=\"").substringBefore("\"")
            array[i] = data
        }
        val dqjkzk = DataManager.readData("dqjkzk", "")
        val dqszs = DataManager.readData("dqszs", "")
        val dqszc = DataManager.readData("dqszc", "")
        val dqszq = DataManager.readData("dqszq", "")
        val sfyjzym = DataManager.readData("sfyjzym", "")
        val sfjzzy = DataManager.readData("sfjzzy", "")
        val sfjcys = DataManager.readData("sfjcys", "")
        val swtw = DataManager.readData("swtw", "")
        val zwtw = DataManager.readData("zwtw", "")
        val xwtw = DataManager.readData("xwtw", "")
        if (dqjkzk == "" || dqszs == "" || dqszc == "" || dqszq == "" || sfyjzym == ""
            || sfjzzy == "" || sfjcys == "" || swtw == "" || zwtw == "" || xwtw == ""
        ) {
            MyApp.context.getString(R.string.results_config_null).errorToast()
            return null
        }
        return FormBody.Builder()
            .add("operateType", "D=C")
            .add("ID", array[1])
            .add("today", array[2])
            .add("P=MH_STUHEALTHREPORT=RQ=S=WD", array[3])
            .add("P=MH_STUHEALTHREPORT=STUID=S=WD", array[4])
            .add("P=MH_STUHEALTHREPORT=ID=S=P", array[5])
            .add("P=MH_STUHEALTHREPORT=STUID=S=C", array[6])
            .add("P=MH_STUHEALTHREPORT=RQ=S=C", array[7])
            .add("P=MH_STUHEALTHREPORT=CREATETIME=S=C", array[8])
            .add("P=MH_STUHEALTHREPORT=WXDWSHENG=S=C", "")
            .add("P=MH_STUHEALTHREPORT=WXDWSHI=S=C", "")
            .add("P=MH_STUHEALTHREPORT=WXDWQU=S=C", "")
            .add("P=MH_STUHEALTHREPORT=WXDWJIEDAO=S=C", "")
            .add("P=MH_STUHEALTHREPORT=DQJKZK=S=C", dqjkzk)
            .add("P=MH_STUHEALTHREPORT=DQSZS=S=C", dqszs)
            .add("P=MH_STUHEALTHREPORT=DQSZC=S=C", dqszc)
            .add("P=MH_STUHEALTHREPORT=DQSZQ=S=C", dqszq)
            .add("P=MH_STUHEALTHREPORT=SFYJZYM=S=C", sfyjzym)
            .add("P=MH_STUHEALTHREPORT=SFJZZY=S=C", sfjzzy)
            .add("P=MH_STUHEALTHREPORT=SFJCYS=S=C", sfjcys)
            .add("P=MH_STUHEALTHREPORT=SWTW=S=C", swtw)
            .add("P=MH_STUHEALTHREPORT=ZWTW=S=C", zwtw)
            .add("P=MH_STUHEALTHREPORT=XWTW=S=C", xwtw)
            .build()
    }


    // 获取验证码
    fun getCaptchaPic(): Bitmap? {
        val url = if (pic) H_captchaPicUrl else captchaPicUrl
        val request = Request.Builder()
            .url(url)
            .build()
        try {
            val response = EasyOkhttp.okHttpClient.newCall(request).execute()
            if (!response.isSuccessful) response.close()
            if (response.body() != null) {
                val `is`: InputStream? = response.body()!!.byteStream()
                LogUtil.d(TAG, "==> 成功获取验证码")
                return BitmapFactory.decodeStream(`is`)
            }
        } catch (e: Exception) {
            LogUtil.d(TAG, "--> 获取验证码失败")
        }
        return null
    }

    // 获取一言句子
    fun getHitokoto() = flow {
        try {
            val hitoko = EasyOkhttp.request("https://v1.hitokoto.cn/?encode=text&c=i")
            LogUtil.d("Hitokoto", "--> Hitokoto is $hitoko")
            emit(hitoko)
        } catch (e: Exception) {
            LogUtil.d("Hitokoto", "获取一言失败")
        }
    }.flowOn(Dispatchers.IO)

    // 将密码转换成md5
    private fun md5(password: String): String {
        val md = MessageDigest.getInstance("MD5")
        md.update(password.toByteArray())
        return BigInteger(1, md.digest()).toString(16)
    }

}