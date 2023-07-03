package pl.grygol.projectmarcus.data

import android.content.ContentResolver
import android.content.Context
import android.net.Uri

object ResourceUriHelper {
    fun getUriFromDrawableId(context: Context, drawableId: Int): Uri {
        return Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(context.resources.getResourcePackageName(drawableId))
            .appendPath(context.resources.getResourceTypeName(drawableId))
            .appendPath(context.resources.getResourceEntryName(drawableId))
            .build()
    }
}
