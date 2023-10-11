package com.andreisingeleytsev.ingetinapp.ui.screens.edit_event_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.andreisingeleytsev.ingetinapp.R
import com.andreisingeleytsev.ingetinapp.ui.screens.home_screen.DayContent
import com.andreisingeleytsev.ingetinapp.ui.screens.home_screen.DefaultDaysOfWeekHeader
import com.andreisingeleytsev.ingetinapp.ui.theme.Vaccine
import com.andreisingeleytsev.ingetinapp.ui.utils.Fonts
import com.andreisingeleytsev.ingetinapp.ui.utils.MonthConverter
import com.andreisingeleytsev.ingetinapp.ui.utils.UIEvents
import io.github.boguszpawlowski.composecalendar.SelectableWeekCalendar
import io.github.boguszpawlowski.composecalendar.rememberSelectableWeekCalendarState
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditEventScreen(navHostController: NavHostController, observer: MutableState<Boolean>, viewModel: EditEventScreenViewModel = hiltViewModel()) {
    if (observer.value) {
        viewModel.onEvent(EditEventScreenEvents.OnSave(observer))
    }
    var status by remember {
        mutableIntStateOf(0)
    }
    val dropdownItems = listOf(
        R.string.generals,
        R.string.personal
    )

    var isDropDownMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }

    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{
            when(it) {
                is UIEvents.OnBack -> {
                    navHostController.popBackStack()
                }

                else -> {}
            }
        }
    }

    Column(
        Modifier
            .padding(32.dp)
            .fillMaxSize()
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = viewModel.dayOfWeek.value, style = TextStyle(
                        color = Color.LightGray,
                        fontSize = 14.sp,
                        fontFamily = Fonts.font
                    )
                )
                Text(
                    text = viewModel.day.value.toString() + " " + MonthConverter.fromIndexToMonthLong(
                        viewModel.month.value
                    ) + " " + viewModel.year.value.toString(), style = TextStyle(
                        color = Vaccine,
                        fontSize = 18.sp,
                        fontFamily = Fonts.font
                    )
                )
            }
            Card(
                modifier = Modifier
                    .pointerInput(true) {
                        detectTapGestures {
                            isDropDownMenuVisible = true
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
                    expanded = isDropDownMenuVisible,
                    onDismissRequest = { isDropDownMenuVisible = false }) {
                    dropdownItems.forEach { item ->
                        DropdownMenuItem(text = {
                            Text(
                                text = stringResource(id = item), style = TextStyle(
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontFamily = Fonts.font
                                )
                            )
                        }, onClick = {
                            viewModel.dropdownIndex.value = dropdownItems.indexOf(item)
                            isDropDownMenuVisible = false
                        }, modifier = Modifier.background(Vaccine))
                    }
                }
            }
        }
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextField(
                            modifier = Modifier.weight(1f),
                            value = viewModel.name.value,
                            onValueChange = { name ->
                                viewModel.name.value = name
                            },
                            label = {
                                Text(
                                    text = stringResource(R.string.title),
                                    fontSize = 14.sp
                                )
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Vaccine,
                                containerColor = Color.White
                            ),
                            singleLine = true,
                            textStyle = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                fontFamily = Fonts.eventFont
                            )
                        )
                    }
                    TextField(
                        modifier = Modifier.fillMaxSize(),
                        value = viewModel.title.value,
                        onValueChange = { title ->
                            viewModel.title.value = title
                        },
                        label = {
                            Text(
                                text = stringResource(R.string.description),
                                fontSize = 14.sp
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            color = Color.Black,
                            fontFamily = Fonts.eventFont
                        )

                    )
                }

            }
    }
}