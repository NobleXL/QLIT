package com.noble.qlit.ui.activity.stall

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.noble.qlit.ui.activity.stall.navigation.Destinations


/**
 * @author: NobleXL
 * @desc: NavHostAPP 导航控制器
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavHostAPP() {

    val navController = rememberAnimatedNavController()

    val stalloneViewModel: StallOneViewModel = viewModel()

    val jobViewModel: JobViewModel = viewModel()

    ProvideWindowInsets {

        CompositionLocalProvider() {

            AnimatedNavHost(
                navController = navController,
                startDestination = Destinations.StallFrame.route
            ) {
                //首页大框架
                composable(
                    Destinations.StallFrame.route,
                    enterTransition = {
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Right)
                    },
                    exitTransition = {
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Left)
                    }) {
                    StallFrame(
                        stalloneViewModel = stalloneViewModel,
                        jobViewModel = jobViewModel,
                        onNavigateToStallone = {
                            navController.navigate(Destinations.StallDetail.route)
                        },
                        onNavigateToJob = {
                            navController.navigate(Destinations.JobDetail.route)
                        })
                }

                //商品详情页
                composable(
                    Destinations.StallDetail.route,
                    enterTransition = {
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
                    },
                    exitTransition = {
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right)
                    }) {
                    StallScreen(
                        stalloneViewModel = stalloneViewModel,
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }

                //兼职详情页
                composable(
                    Destinations.JobDetail.route,
                    enterTransition = {
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
                    },
                    exitTransition = {
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right)
                    }) {
                    JobScreen(
                        jobViewModel = jobViewModel,
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }

            }
        }
    }
}


