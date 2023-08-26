package com.example.evados.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1)
abstract class ListDB: RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var DATABASE: ListDB? = null

        fun getInstance(context: Context): ListDB {
            return DATABASE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    ListDB::class.java,
                    "list.db"
                ).fallbackToDestructiveMigration().build().also {
                    DATABASE = it
                }
            }
        }
    }
}