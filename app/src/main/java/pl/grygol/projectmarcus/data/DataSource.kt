package pl.grygol.projectmarcus.data

object DataSource {
    val expenses = mutableListOf<Expense>(
        Expense(),
        Expense(),
        Expense(),
    )
    val projects = mutableListOf<Project>(
        Project(),
        Project(),
        Project(),
    )
    val pictures = mutableListOf<Uri>()
    val positions = mutableListOf<Position>()
}
