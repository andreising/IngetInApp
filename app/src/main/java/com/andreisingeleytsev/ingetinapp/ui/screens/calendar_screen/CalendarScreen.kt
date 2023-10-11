package com.andreisingeleytsev.ingetinapp.ui.screens.calendar_screen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.andreisingeleytsev.ingetinapp.R
import com.andreisingeleytsev.ingetinapp.ui.screens.home_screen.DayContent
import com.andreisingeleytsev.ingetinapp.ui.screens.home_screen.DefaultDaysOfWeekHeader
import com.andreisingeleytsev.ingetinapp.ui.screens.home_screen.HomeScreenViewModel
import com.andreisingeleytsev.ingetinapp.ui.theme.SecondaryColor
import com.andreisingeleytsev.ingetinapp.ui.theme.Vaccine
import com.andreisingeleytsev.ingetinapp.ui.utils.Fonts
import com.andreisingeleytsev.ingetinapp.ui.utils.UIEvents
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.day.NonSelectableDayState
import io.github.boguszpawlowski.composecalendar.header.MonthState
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(navHostController: NavHostController, viewModel: CalendarScreenViewModel = hiltViewModel()) {
    val calendarState = rememberSelectableCalendarState()

    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{
            when(it) {
                is UIEvents.OnNavigate -> {
                    navHostController.navigate(it.route)
                }

                else -> {}
            }
        }
    }

    Column(
        Modifier
            .padding(30.dp)
            .fillMaxSize()) {
        SelectableCalendar(
            calendarState = calendarState, today = viewModel.currentDate.value,
            dayContent = { DayContentOfMonth(dayState = it as NonSelectableDayState) },
            daysOfWeekHeader = { DefaultDaysOfWeekHeader(daysOfWeek = it) },
            monthHeader = { MonthHeader(monthState = it) },
            showAdjacentMonths = false
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthHeader(monthState: MonthState) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = {
            monthState.currentMonth = monthState.currentMonth.minusMonths(1)
        }) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(35.dp))
        Text(
            monthState.currentMonth.month.name +" "+ monthState.currentMonth.year.toString(), style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Vaccine,
                fontFamily = Fonts.font
            )
        )
        Spacer(modifier = Modifier.width(35.dp))
        IconButton(onClick = {
            monthState.currentMonth = monthState.currentMonth.plusMonths(1)
        }) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayContentOfMonth(dayState: NonSelectableDayState,
               viewModel: CalendarScreenViewModel = hiltViewModel()) {
    val backgroundColor = when(dayState.date){
        LocalDate.now()-> Vaccine
        else -> {
            Color.White
        }
    }


    val textColor = when(dayState.date){
        LocalDate.now()-> Color.White
        else -> {
            Vaccine
        }
    }
    Box(modifier = Modifier
        .clickable {

            viewModel.onEvent(CalendarScreenEvent.OnDayChose(dayState.date))
        },contentAlignment = Alignment.TopEnd){
        Card(

            colors = CardDefaults.cardColors(
                containerColor = backgroundColor
            ), shape = RoundedCornerShape(1000.dp),
            border = BorderStroke(1.dp, Color.LightGray)
        ) {
            Text(
                text = dayState.date.dayOfMonth.toString(),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    fontFamily = Fonts.font
                ),
                modifier = Modifier
                    .padding(12.dp)
                    .size(20.dp)
                ,
                textAlign = TextAlign.Center,

                )
        }
        Card(
            colors = CardDefaults.cardColors(
                containerColor = SecondaryColor
            ), shape = RoundedCornerShape(1000.dp)
        ) {
            var count = 0
            viewModel.list.value.forEach {
                if (it.day == dayState.date.dayOfMonth && it.month == dayState.date.monthValue - 1) count++
            }
            if (count != 0) Text(
                text = count.toString(),
                style = TextStyle(
                    fontSize = 10.sp,
                    color = Color.White,
                    fontFamily = Fonts.font
                ),
                modifier = Modifier
                    .padding(2.dp)
                    .size(14.dp),
                textAlign = TextAlign.Center,

                )
        }
    }
}