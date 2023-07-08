package pl.grygol.projectmarcus.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.grygol.projectmarcus.data.model.Expense

@Entity(tableName = "project")
data class ProjectEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val companyName: String,
    val currency: String,
    val budget: Float,
)