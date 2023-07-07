package pl.grygol.projectmarcus.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import pl.grygol.projectmarcus.data.database.model.ProjectEntity
import pl.grygol.projectmarcus.data.database.model.ProjectWithExpenses

@Dao
interface ProjectDao {
    @Query("SELECT * FROM project")
    fun getAll(): List<ProjectWithExpenses>

    @Transaction
    @Query("SELECT * FROM project WHERE id = :projectId")
    fun getProjectWithExpenses(projectId: Int): ProjectWithExpenses
}