package com.vickbt.shared.utils

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vickbt.shared.data.cache.room.RoomAppDatabase

object DatabaseBuilder {

    fun createDatabase(context:Context): RoomDatabase.Builder<RoomAppDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath("notflix.db")

        return Room.databaseBuilder<RoomAppDatabase>(
            context = appContext, name = dbFile.absolutePath
        )
    }

}
