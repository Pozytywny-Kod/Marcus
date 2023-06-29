package pl.grygol.projectmarcus.Data

import java.time.LocalDate

object DataSource {
    val expenses = mutableListOf<Expense>(
        Expense(
            0,
            "Piżama",
            LocalDate.of(2023,8,17)
        )
    )
    val projects = mutableListOf<Project>(
        Project(),
        Project(),
    )
}