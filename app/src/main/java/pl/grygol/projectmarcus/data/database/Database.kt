package pl.grygol.projectmarcus.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pl.grygol.projectmarcus.data.database.dao.ExpenseDao
import pl.grygol.projectmarcus.data.database.dao.ProjectDao
import pl.grygol.projectmarcus.data.database.model.ExpenseEntity
import pl.grygol.projectmarcus.data.database.model.ProjectEntity

@Database(entities = [ExpenseEntity::class, ProjectEntity::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract val expenses: ExpenseDao
    abstract val projects: ProjectDao

    companion object {
        fun open(context: Context): pl.grygol.projectmarcus.data.database.Database = Room.databaseBuilder(
            context, pl.grygol.projectmarcus.data.database.Database::class.java, "marcus.db"
        ).addCallback(DatabaseCallback(context))
            .build()
    }
}