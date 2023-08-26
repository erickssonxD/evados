package com.example.evados.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update

@Dao
interface ItemDao {
    @Query("SELECT * FROM Item")
    fun getAll(): List<Item>

    @Insert
    fun insert(item: Item): Long

    @Update
    fun update(item: Item)

    @Delete
    fun delete(item: Item)
}