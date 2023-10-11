package com.andreisingeleytsev.ingetinapp.ui.menu

import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.andreisingeleytsev.ingetinapp.ui.theme.SecondaryColor


@Composable
fun IngetInBottomNavigationMenu(currentRoute: String?, onNavigate: (String)->Unit) {
    val listItems = listOf(
        BottomNavigationItem.HomeItem,
        BottomNavigationItem.CalendarItem

    )
    BottomAppBar(
        containerColor = Color.White
    ) {
        listItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute==item.route,
                onClick = {
                          onNavigate(item.route)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon_id),
                        contentDescription = "bottom_icon",
                        modifier = Modifier.size(23.dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = SecondaryColor,
                    unselectedIconColor = Color.Black,
                    indicatorColor = Color.White
                )
            )

        }
    }
}