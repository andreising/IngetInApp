package com.andreisingeleytsev.ingetinapp.ui.utils

import java.time.LocalDate

sealed class UIEvents(){
    data class OnNavigate(val route: String): UIEvents()
    data class OnChangeWeek(val index: Int): UIEvents()
    object OnBack: UIEvents()
}
