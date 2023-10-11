package com.andreisingeleytsev.ingetinapp.ui.screens.main_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.andreisingeleytsev.ingetinapp.R
import com.andreisingeleytsev.ingetinapp.ui.menu.IngetInBottomNavigationMenu
import com.andreisingeleytsev.ingetinapp.ui.navigation.MainNavigationGraph
import com.andreisingeleytsev.ingetinapp.ui.theme.SecondaryColor
import com.andreisingeleytsev.ingetinapp.ui.theme.Vaccine
import com.andreisingeleytsev.ingetinapp.ui.utils.Routes

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isEditScreen = remember {
        mutableStateOf(false)
    }
    val isActive = remember {
        mutableStateOf(true)
    }
    val observer = remember{
        mutableStateOf(false)
    }
    Scaffold(
        bottomBar = {
            IngetInBottomNavigationMenu(currentRoute = currentRoute, onNavigate = { route ->
                navController.navigate(route)
            })
        }, floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (isActive.value){
                        observer.value = true
                    }

                },
                containerColor = if (isActive.value) SecondaryColor
                else Vaccine,
                shape = RoundedCornerShape(100.dp),
                contentColor = Color.White
            ) {
                val image_id = if (isEditScreen.value) R.drawable.save_icon
                else R.drawable.add_icon
                Icon(
                    painter = painterResource(id = image_id),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            MainNavigationGraph(
                navController = navController,
                isActive = isActive,
                isEditScreen = isEditScreen,
                observer = observer
            )
        }
    }
}