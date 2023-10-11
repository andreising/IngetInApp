package com.andreisingeleytsev.ingetinapp.domain.provider

import java.time.LocalDate

interface CurrentDateProvider {
    fun getDate(): LocalDate
    fun insertDate(date: LocalDate)
}