package com.andreisingeleytsev.ingetinapp

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.andreisingeleytsev.ingetinapp.ui.screens.main_screen.MainScreen
import com.andreisingeleytsev.ingetinapp.ui.screens.onboarding_screen.OnboardingScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var sharedPreferences: SharedPreferences? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = this.getSharedPreferences("shared_preferences", MODE_PRIVATE)
        setContent {
            val isFirstLaunch = remember {
                mutableStateOf(
                    sharedPreferences?.getBoolean("first_launch", true) ?: true
                )
            }
            if (isFirstLaunch.value) OnboardingScreen{
                isFirstLaunch.value = false
                sharedPreferences?.edit()?.putBoolean("first_launch", false)?.apply()
            }
            else MainScreen()
        }
    }
}
