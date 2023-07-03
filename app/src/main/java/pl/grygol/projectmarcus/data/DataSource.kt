package pl.grygol.projectmarcus.data

import android.graphics.drawable.Drawable
import android.net.Uri
import pl.grygol.projectmarcus.R
import java.util.Objects

object DataSource {
    val expenses = mutableListOf<Expense>(
        Expense(),
        Expense(),
        Expense(),
    )
    val projects = mutableListOf<Project>(
        Project(),
        Project(),
        Project(),
    )
    val pictures = mutableListOf<Uri>()
    val positions = mutableListOf<Position>()
}
