package pl.grygol.projectmarcus.data

import java.time.LocalDate

data class Expense (
    val id: Int,
    val name: String,
    val date: LocalDate,
)