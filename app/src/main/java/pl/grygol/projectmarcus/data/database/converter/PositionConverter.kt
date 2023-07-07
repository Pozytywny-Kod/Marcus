package pl.grygol.projectmarcus.data.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pl.grygol.projectmarcus.data.model.Position

class PositionConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromPositions(positions: ArrayList<Position>?): String? {
        return positions?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toPositions(positionsJson: String?): ArrayList<Position>? {
        val type = object : TypeToken<ArrayList<Position>>() {}.type
        return positionsJson?.let { gson.fromJson(it, type) }
    }
}
