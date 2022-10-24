package com.noble.qlit.ui.activity.config

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noble.qlit.MyApp
import com.noble.qlit.R
import com.noble.qlit.utils.DataManager
import com.noble.qlit.utils.infoToast
import kotlinx.coroutines.launch

/**
 * @author: noble
 * @desc: ConfigViewModel
 */
class ConfigViewModel : ViewModel(){

    var account = mutableStateOf("")
    var password = mutableStateOf("")
    var IDcard = mutableStateOf("")
    var ID_end = mutableStateOf("")
    var dqjkzk = mutableStateOf("")
    var dqszs = mutableStateOf("")
    var dqszc = mutableStateOf("")
    var dqszq = mutableStateOf("")
    var sfyjzym = mutableStateOf("")
    var sfjzzy = mutableStateOf("")
    var sfjcys = mutableStateOf("")
    var swtw = mutableStateOf("")
    var zwtw = mutableStateOf("")
    var xwtw = mutableStateOf("")

    fun saveConfig() {
        viewModelScope.launch {
            DataManager.saveData("account", account.value)
            DataManager.saveData("password", password.value)
            DataManager.saveData("IDcard", IDcard.value)
            DataManager.saveData("ID_end", ID_end.value)
            DataManager.saveData("dqjkzk", dqjkzk.value)
            DataManager.saveData("dqszs", dqszs.value)
            DataManager.saveData("dqszc", dqszc.value)
            DataManager.saveData("dqszq", dqszq.value)
            DataManager.saveData("sfyjzym", sfyjzym.value)
            DataManager.saveData("sfjzzy", sfjzzy.value)
            DataManager.saveData("sfjcys", sfjcys.value)
            DataManager.saveData("swtw", swtw.value)
            DataManager.saveData("zwtw", zwtw.value)
            DataManager.saveData("xwtw", xwtw.value)
            MyApp.context.getString(R.string.config_save_config).infoToast()
        }
    }

    fun showConfig() {
        viewModelScope.launch {
            account.value = DataManager.readData("account", "")
            password.value = DataManager.readData("password", "")
            IDcard.value = DataManager.readData("IDcard", "")
            ID_end.value = DataManager.readData("ID_end", "")
            dqjkzk.value = DataManager.readData("dqjkzk", "")
            dqszs.value = DataManager.readData("dqszs", "")
            dqszc.value = DataManager.readData("dqszc", "")
            dqszq.value = DataManager.readData("dqszq", "")
            sfyjzym.value = DataManager.readData("sfyjzym", "")
            sfjzzy.value = DataManager.readData("sfjzzy", "")
            sfjcys.value = DataManager.readData("sfjcys", "")
            swtw.value = DataManager.readData("swtw", "")
            zwtw.value = DataManager.readData("zwtw", "")
            xwtw.value = DataManager.readData("xwtw", "")


        }
    }
}