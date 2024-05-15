package com.example.myeventsmanagmentapp.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myeventsmanagmentapp.ui.theme.Navy
import com.example.myeventsmanagmentapp.ui.theme.PrimaryColor
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.core.yearMonth
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.util.Locale


@Composable
fun CalendarWeeklyView(onDateSelected: (LocalDate) -> Unit) {
    val currentDate = remember { LocalDate.now() }
    val currentMonth = remember { YearMonth.now() }
    val startDate = remember { currentMonth.minusMonths(100).atStartOfMonth() } // Adjust as needed
    val endDate = remember { currentMonth.plusMonths(100).atEndOfMonth() } // Adjust as needed
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() } // Available from the library
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    val state = rememberWeekCalendarState(
        startDate = startDate,
        endDate = endDate,
        firstVisibleWeekDate = currentDate,
        firstDayOfWeek = firstDayOfWeek
    )

    WeekCalendar(
        modifier = Modifier
            .fillMaxWidth(),
        state = state,
        weekHeader = {
            WeekHeaderView(currentDate.yearMonth.toString())
        },
        dayContent = { day ->
            Day(day, isSelected = selectedDate == day.date) {
                selectedDate = if (selectedDate == it) null else it
            }

        }
    )
}

@Composable
fun MonthlyHorizontalCalendarView(navController: NavHostController) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) } // Adjust as needed
    val endMonth = remember { currentMonth.plusMonths(100) } // Adjust as needed
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() } // Available from the library

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek
    )

    HorizontalCalendar(
        modifier = Modifier.background(Color.White),
        state = state,
        dayContent = {
            Column(modifier = Modifier.clickable {
                navController.previousBackStackEntry?.savedStateHandle?.set(
                    "selectedDate",
                    it.date.toString()
                )
            }) {
                Text(
                    text = it.date.dayOfWeek.getDisplayName(
                        java.time.format.TextStyle.SHORT,
                        Locale.getDefault()
                    ),
                    color = Color.Black
                )
                Text(
                    text = it.date.dayOfMonth.toString(),
                    color = Color.Black
                )
            }
        }
    )

//    If you need a vertical calendar.
//    VerticalCalendar(
//        state = state,
//        dayContent = { Day(it) }
//    )
}

@Composable
fun Day(day: WeekDay, isSelected: Boolean, onDateSelected: (LocalDate) -> Unit) {
    Column(
        modifier = Modifier
            .aspectRatio(0.8f)
            .background(if (isSelected) PrimaryColor else Color.White, RoundedCornerShape(16.dp))
            .clickable {
                onDateSelected.invoke(day.date)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = day.date.dayOfWeek.getDisplayName(
                java.time.format.TextStyle.SHORT,
                Locale.getDefault()
            ),
            color = if (isSelected) Color.White else Color.Black,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = day.date.dayOfMonth.toString(),
            color = if (isSelected) Color.White else Color.Black,
            fontSize = 12.sp,
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}


@Composable
fun WeekHeaderView(monthName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            "Task",
            modifier = Modifier,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = Navy,
            textAlign = TextAlign.Center
        )

        Row {
            Icon(Icons.Outlined.DateRange, contentDescription = "")
            Text(
                monthName,
                modifier = Modifier,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = Color.LightGray,
                textAlign = TextAlign.Center
            )
        }
    }
}