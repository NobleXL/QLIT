package com.noble.qlit.ui.activity.stall.navigation

/**
 * @author 小寒
 * @version 1.0
 * @date 2022/7/5 10:37
 */
sealed class Destinations(val route: String) {
    //首页大框架
    object StallFrame : Destinations("StallFrame")

    //商品详情页
    object StallDetail : Destinations("StallDetail")
    //兼职详情页
    object JobDetail : Destinations("JobDetail")
}
