package pl.grygol.projectmarcus.dataSource

import java.time.LocalDate

data class Expense (
    val id: Int,
    val name: String,
    val date: LocalDate,
)