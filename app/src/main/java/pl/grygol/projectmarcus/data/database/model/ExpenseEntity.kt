package pl.grygol.projectmarcus.data.database.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import pl.grygol.projectmarcus.data.database.converter.DateConverter
import pl.grygol.projectmarcus.data.database.converter.PositionConverter
import pl.grygol.projectmarcus.data.database.converter.UriConverter
import pl.grygol.projectmarcus.data.model.Position
import java.util.Date

@Entity(
    tableName = "expense",
    foreignKeys = [ForeignKey(
        entity = ProjectEntity::class,
        parentColumns = ["id"],
        childColumns = ["projectId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("projectId")]
)
@TypeConverters(DateConverter::class, UriConverter::class, PositionConverter::class)
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val expenseDate: Date,
    val nip: String,
    val location: String,
    val description: String,
    val currency: String,
    val positions: ArrayList<Position>,
    val photo: Uri?,
    val projectId: Long
)
