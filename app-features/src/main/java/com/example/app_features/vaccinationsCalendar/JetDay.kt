package com.example.app_features.vaccinationsCalendar

import java.time.LocalDate

open class JetCalendarType

data class JetDay(val date: LocalDate, val isPartOfMonth: Boolean) : JetCalendarType()