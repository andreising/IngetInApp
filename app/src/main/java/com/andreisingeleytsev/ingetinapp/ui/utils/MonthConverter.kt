package com.andreisingeleytsev.ingetinapp.ui.utils

object MonthConverter {
    fun fromIndexToMonthLong(index: Int): String{
        return when(index){
            0 -> {
                "January"
            }
            1 -> {
                "February"
            }
            2 -> {
                "March"
            }
            3 -> {
                "April"
            }
            4 -> {
                "May"
            }
            5 -> {
                "June"
            }
            6 -> {
                "July"
            }
            7 -> {
                "August"
            }
            8 -> {
                "September"
            }
            9 -> {
                "October"
            }
            10 -> {
                "November"
            }
            11 -> {
                "December"
            }
            else -> {
                "March"
            }
        }
    }
    fun fromIndexToMonthShort(index: Int): String{
        return when(index){
            0 -> {
                "jan"
            }
            1 -> {
                "feb"
            }
            2 -> {
                "mar"
            }
            3 -> {
                "apr"
            }
            4 -> {
                "may"
            }
            5 -> {
                "jun"
            }
            6 -> {
                "jul"
            }
            7 -> {
                "aug"
            }
            8 -> {
                "sep"
            }
            9 -> {
                "oct"
            }
            10 -> {
                "nov"
            }
            11 -> {
                "dec"
            }
            else -> {
                "mrc"
            }
        }
    }
    fun fromMonthToInt(month: String): Int{
        return when(month.lowercase()){
            "january" -> {
                0
            }
            "february" -> {
                1
            }
            "march" -> {
                2
            }
            "april" -> {
                3
            }
            "may" -> {
                4
            }
            "june" -> {
                5
            }
            "july" -> {
                6
            }
            "august" -> {
                7
            }
            "september" -> {
                8
            }
            "october" -> {
                9
            }
            "november" -> {
                10
            }
            "december" -> {
                11
            }
            else -> {
                3
            }
        }
    }
}