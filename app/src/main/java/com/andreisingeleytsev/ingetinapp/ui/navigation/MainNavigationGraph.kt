package com.andreisingeleytsev.ingetinapp.ui.navigation


import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.andreisingeleytsev.ingetinapp.ui.screens.calendar_screen.CalendarScreen
import com.andreisingeleytsev.ingetinapp.ui.screens.edit_event_screen.EditEventScreen
import com.andreisingeleytsev.ingetinapp.ui.screens.home_screen.HomeScreen
import com.andreisingeleytsev.ingetinapp.ui.utils.Routes


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavigationGraph(
    navController: NavHostController,
    isActive: MutableState<Boolean>,
    isEditScreen: MutableState<Boolean>,
    observer: MutableState<Boolean>
) {
    NavHost(navController = navController, startDestination = Routes.HOME_SCREEN) {
        composable(Routes.HOME_SCREEN) {
            HomeScreen(navHostController = navController, observer = observer)
            isActive.value = true
            isEditScreen.value = false
        }
        composable(Routes.CALENDAR_SCREEN ) {
            CalendarScreen(navHostController = navController)
            isActive.value = false
            isEditScreen.value = false
        }
        composable(Routes.EDIT_EVENT_SCREEN + "/{year}/{month}/{day}/{event_id}"){
            EditEventScreen(navHostController = navController, observer = observer)
            isActive.value = true
            isEditScreen.value = true
        }

    }
}