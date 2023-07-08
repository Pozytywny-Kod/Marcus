package pl.grygol.projectmarcus.data.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class ProjectWithExpenses(
    @Embedded val project: ProjectEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "projectId"
    )
    val expenses: List<ExpenseEntity>
)

