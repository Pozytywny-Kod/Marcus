package pl.grygol.projectmarcus.Data

import java.time.LocalDate
import java.util.Date

data class Expense (
    val id: Int,
    val name: String,
    val date: LocalDate,
)