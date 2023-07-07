package pl.grygol.projectmarcus.data.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.grygol.projectmarcus.R
import pl.grygol.projectmarcus.data.ResourceUriHelper
import pl.grygol.projectmarcus.data.model.Position
import pl.grygol.projectmarcus.data.database.model.ExpenseEntity
import pl.grygol.projectmarcus.data.database.model.ProjectEntity
import java.util.Date

class DatabaseCallback(private val context: Context) : RoomDatabase.Callback() {

    interface DataSeedingCallback {
        fun onDataSeedingComplete()
    }

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        seedDatabase(db, object : DataSeedingCallback {
            override fun onDataSeedingComplete() {
                // Data seeding is complete
            }
        })
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun seedDatabase(db: SupportSQLiteDatabase, callback: DataSeedingCallback) {
        // Insert projects
        db.execSQL("INSERT INTO project (name, companyName, currency, budget) VALUES ('Projekt 1','Franwear','USD', 1000.0)")
        db.execSQL("INSERT INTO project (name, companyName, currency, budget) VALUES ('Projekt 2','Grygdom','EUR', 2000.0)")

        // Insert expenses
        val expense1Values = ContentValues().apply {
            put("title", "Expense 1")
            put("expenseDate", Date().time)
            put("nip", "1234567890")
            put("location", "Location 1")
            put("description", "Expense 1 Description")
            put("currency", "USD")
            put("positions", Gson().toJson(arrayListOf(Position("Position 1", 10.0f), Position("Position 2", 20.0f))))
            put("photo", Uri.parse(ResourceUriHelper.getUriFromDrawableId(context,R.drawable.sheep).toString()).toString())
            put("projectId", 1)
        }
        val expense2Values = ContentValues().apply {
            put("title", "Expense 2")
            put("expenseDate", Date().time)
            put("nip", "9876543210")
            put("location", "Location 2")
            put("description", "Expense 2 Description")
            put("currency", "EUR")
            put("positions", Gson().toJson(arrayListOf(Position("Position 3", 30.0f), Position("Position 4", 40.0f))))
            put("photo", Uri.parse(ResourceUriHelper.getUriFromDrawableId(context,R.drawable.sheep).toString()).toString())
            put("projectId", 1)
        }
        db.insert("expense", SQLiteDatabase.CONFLICT_REPLACE, expense1Values)
        db.insert("expense", SQLiteDatabase.CONFLICT_REPLACE, expense2Values)

        // Invoke the callback to notify that the seeding is complete
        callback.onDataSeedingComplete()
    }

}
