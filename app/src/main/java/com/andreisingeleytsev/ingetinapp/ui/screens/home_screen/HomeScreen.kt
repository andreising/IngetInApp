package com.andreisingeleytsev.ingetinapp.ui.screens.home_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.andreisingeleytsev.ingetinapp.ui.utils.UIEvents
import com.andreisingeleytsev.ingetinapp.R
import com.andreisingeleytsev.ingetinapp.data.entities.EventItem
import com.andreisingeleytsev.ingetinapp.ui.theme.CardColor
import com.andreisingeleytsev.ingetinapp.ui.theme.Pink
import com.andreisingeleytsev.ingetinapp.ui.theme.SecondaryColor
import com.andreisingeleytsev.ingetinapp.ui.theme.Vaccine
import com.andreisingeleytsev.ingetinapp.ui.utils.Fonts
import com.andreisingeleytsev.ingetinapp.ui.utils.MonthConverter
import io.github.boguszpawlowski.composecalendar.SelectableWeekCalendar
import io.github.boguszpawlowski.composecalendar.day.NonSelectableDayState
import io.github.boguszpawlowski.composecalendar.rememberSelectableWeekCalendarState
import io.github.boguszpawlowski.composecalendar.week.Week
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(navHostController: NavHostController, observer: MutableState<Boolean>, viewModel: HomeScreenViewModel = hiltViewModel()) {
    if (observer.value) {
        viewModel.onEvent(HomeScreenEvent.OnNavigateToEdit(observer))
    }
    val dropdownItems = listOf(
        R.string.this_week,
        R.string.next_week
    )
    val tabList = listOf(
        R.string.all_plans,
        R.string.generals,
        R.string.personal
    )
    var isWeekMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    val eventList = viewModel.list.collectAsState(initial = emptyList())
    val weekState = rememberSelectableWeekCalendarState()
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{
            when(it) {
                is UIEvents.OnNavigate -> {
                    navHostController.navigate(it.route)
                }

                is UIEvents.OnChangeWeek -> {
                    weekState.weekState.currentWeek =
                        weekState.weekState.currentWeek.plusWeeks(it.index.toLong())
                }

                else -> {}
            }
        }
    }

    Column(
        Modifier
            .padding(32.dp)
            .fillMaxSize()) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = viewModel.choseDate.value
                        .dayOfWeek.name.lowercase()
                        .capitalize(androidx.compose.ui.text.intl.Locale.current),
                        style = TextStyle(
                        color = Color.LightGray,
                        fontSize = 14.sp,
                        fontFamily = Fonts.font
                    )
                )
                Text(
                    text = viewModel.choseDate.value
                        .month.name.lowercase()
                        .capitalize(androidx.compose.ui.text.intl.Locale.current)
                            + " "
                            + viewModel.choseDate.value
                                .year.toString(),
                    style = TextStyle(
                        color = Vaccine,
                        fontSize = 18.sp,
                        fontFamily = Fonts.font
                    )
                )
            }
            Card(modifier = Modifier
                .pointerInput(true) {
                    detectTapGestures {
                        isWeekMenuVisible = true
                        pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                    }
                }, colors = CardDefaults.cardColors(
                containerColor = Vaccine
            )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(
                        text = stringResource(id = dropdownItems[viewModel.dropdownIndex.value]), style = TextStyle(
                            color = Color.White,
                            fontSize = 14.sp,
                            fontFamily = Fonts.font,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
                DropdownMenu(
                    expanded = isWeekMenuVisible,
                    onDismissRequest = { isWeekMenuVisible = false }) {
                    dropdownItems.forEach { item->
                        DropdownMenuItem(text = {
                            Text(
                                text = stringResource(id = item), style = TextStyle(
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontFamily = Fonts.font
                                )
                            )
                        }, onClick = {
                            viewModel.onEvent(
                                HomeScreenEvent.OnWeekChoose(
                                    dropdownItems.indexOf(
                                        item
                                    )
                                )
                            )
                            isWeekMenuVisible = false
                        }, modifier = Modifier.background(Vaccine))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        SelectableWeekCalendar(
            today = viewModel.choseDate.value,
            calendarState = weekState,
            dayContent = { DayContent(dayState = it as NonSelectableDayState)},
            weekHeader ={

            },
            daysOfWeekHeader = {
                DefaultDaysOfWeekHeader(it)
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        TabRow(selectedTabIndex = viewModel.tabState, divider = {
            Divider(
                color = Color.Transparent
            )
        }, indicator = {
            TabRowDefaults.Indicator(
                color = SecondaryColor,
                height = 5.dp,
                modifier = Modifier.tabIndicatorOffset(it[viewModel.tabState])
            )
        }) {
            tabList.forEachIndexed { index, i ->
                Tab(selected = viewModel.tabState == index, onClick = {
                    viewModel.tabState = index
                }, selectedContentColor = Vaccine, unselectedContentColor = Color.Gray, modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)) {
                    Text(text = stringResource(id = i), style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = Fonts.font,
                        fontWeight = FontWeight.Bold
                    ))
                }
            }
        }
        LazyColumn(modifier = Modifier.fillMaxSize()){
            items(eventList.value){event->
                when(viewModel.tabState) {
                    0 -> {
                        EventUIItem(event = event, {
                            viewModel.onEvent(HomeScreenEvent.OnEditEvent(it))
                        }, {
                            viewModel.onEvent(HomeScreenEvent.OnDeleteEvent(it))
                        })
                    }
                    1 -> {
                        if (!event.isPersonal) EventUIItem(event = event, {
                            viewModel.onEvent(HomeScreenEvent.OnEditEvent(it))
                        }, {
                            viewModel.onEvent(HomeScreenEvent.OnDeleteEvent(it))
                        })
                    }
                    2 -> {
                        if (event.isPersonal) EventUIItem(event = event, {
                            viewModel.onEvent(HomeScreenEvent.OnEditEvent(it))
                        }, {
                            viewModel.onEvent(HomeScreenEvent.OnDeleteEvent(it))
                        })
                    }
                }

            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayContent(dayState: NonSelectableDayState,
                       viewModel: HomeScreenViewModel = hiltViewModel()) {
    val backgroundColor = when(dayState.date){
        viewModel.choseDate.value -> Vaccine
        LocalDate.now()-> Color.Gray
        else -> {
            Color.White
        }
    }
    val textColor = when(dayState.date){
        viewModel.choseDate.value -> Color.White
        LocalDate.now() -> Color.White
        else -> {
            Vaccine
        }
    }
    Box(modifier = Modifier
        .clickable {
            viewModel.onEvent(HomeScreenEvent.OnDayChoose(dayState.date))
        }, contentAlignment = Alignment.TopEnd
    ) {
        Card(
            modifier = Modifier
                ,
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
                    .size(20.dp),
                textAlign = TextAlign.Center,

                )
        }
        Card(
            colors = CardDefaults.cardColors(
                containerColor = SecondaryColor
            ), shape = RoundedCornerShape(1000.dp)
        ) {
            var count = 0
            viewModel.monthList.value.forEach {
                if (it.day == dayState.date.dayOfMonth) count++
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DefaultDaysOfWeekHeader(
    daysOfWeek: List<DayOfWeek>,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        daysOfWeek.forEach { dayOfWeek ->
            Text(
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(java.time.format.TextStyle.SHORT, Locale.getDefault()),
                modifier = modifier
                    .weight(1f)
                    .wrapContentHeight()
            )
        }
    }
}

@Composable
fun EventUIItem(event: EventItem, onEdit: (EventItem)->Unit,onDelete: (EventItem)->Unit,) {
    Box(modifier = Modifier
        .padding(top = 15.dp)
        .fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
        Card(modifier = Modifier
            .padding(top = 10.dp, end = 10.dp)
            .fillMaxWidth(), colors = CardDefaults.cardColors(
                containerColor = CardColor
            ), shape = RoundedCornerShape(21.dp)
        ) {
            Row(modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()) {
                val img = if (event.isPersonal) R.drawable.icon_personal
                else R.drawable.icon_general
                Image(
                    painter = painterResource(id = img),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(15.dp))
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = MonthConverter.fromIndexToMonthShort(event.month).capitalize(
                        androidx.compose.ui.text.intl.Locale.current) +
                            " " +
                        event.day + ", " +
                        event.year, style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = Fonts.font,
                        color = Pink
                    ))
                    Text(text = event.name, style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = Fonts.font,
                        color = Vaccine
                    ), maxLines = 1)
                    Text(text = event.title, style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = Fonts.font,
                        color = Color.Gray
                    ), maxLines = 2)
                }
            }
        }
        Column {
            Image(
                painter = painterResource(id = R.drawable.edit_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        onEdit(event)
                    },
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(10.dp))
            FloatingActionButton(onClick = {
                onDelete(event)
            }, containerColor = SecondaryColor,
                modifier = Modifier
                    .size(40.dp), shape = RoundedCornerShape(1000.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }

    }
}