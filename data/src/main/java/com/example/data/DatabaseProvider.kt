package com.example.data

import android.content.Context
import androidx.room.Room

class DatabaseProvider private constructor(context: Context) {
    val database: AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "database"
    ).build()

    companion object {
        @Volatile private var instance: DatabaseProvider? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: DatabaseProvider(context).also { instance = it }
            }
    }
}