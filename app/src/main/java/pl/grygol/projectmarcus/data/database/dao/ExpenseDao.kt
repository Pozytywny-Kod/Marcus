package pl.grygol.projectmarcus.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import pl.grygol.projectmarcus.data.database.model.ExpenseEntity

@Dao
interface ExpenseDao {
    @Insert
    fun insertExpense(expense: ExpenseEntity)
}