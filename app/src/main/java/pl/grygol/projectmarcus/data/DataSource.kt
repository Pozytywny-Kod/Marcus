package pl.grygol.projectmarcus.data

import android.net.Uri
import pl.grygol.projectmarcus.data.database.model.ExpenseEntity
import pl.grygol.projectmarcus.data.database.model.ProjectWithExpenses
import pl.grygol.projectmarcus.data.model.Expense
import pl.grygol.projectmarcus.data.model.Position
import pl.grygol.projectmarcus.data.model.Project
import java.util.Date

object DataSource {
    val pictures = mutableListOf<Uri>()
    var date: Date? = null
    var photo : Uri? = null
    val positions = mutableListOf<Position>()
    var currentProjectWithExpenses: ProjectWithExpenses? = null
    var currentExpense: ExpenseEntity? = null
    var currencies: String? = null
}
