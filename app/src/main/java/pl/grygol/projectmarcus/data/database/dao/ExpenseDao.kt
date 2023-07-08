package pl.grygol.projectmarcus.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import pl.grygol.projectmarcus.data.database.model.ExpenseEntity

@Dao
interface ExpenseDao {
    @Insert
    fun insertExpense(expense: ExpenseEntity)

    @Transaction
    @Query("SELECT * FROM expense ORDER BY id DESC LIMIT :numberOfElements")
    fun getLatest(numberOfElements: Int): List<ExpenseEntity>
}