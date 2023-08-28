package com.example.evados.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ItemDao {
    @Query("SELECT * FROM Item ORDER BY bought ASC")
    fun getAllSortedByBought(): List<Item>

    @Insert
    fun insert(item: Item): Long

    @Update
    fun update(item: Item)

    @Delete
    fun delete(item: Item)
}