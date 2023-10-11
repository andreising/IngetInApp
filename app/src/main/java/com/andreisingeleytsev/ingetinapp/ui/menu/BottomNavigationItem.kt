package com.andreisingeleytsev.ingetinapp.ui.menu

import com.andreisingeleytsev.ingetinapp.R
import com.andreisingeleytsev.ingetinapp.ui.utils.Routes

sealed class BottomNavigationItem( val icon_id: Int, val route: String) {
    object HomeItem: BottomNavigationItem( R.drawable.icon_home, Routes.HOME_SCREEN)
    object CalendarItem: BottomNavigationItem( R.drawable.icon_calendar, Routes.CALENDAR_SCREEN)
}
