package com.example.evados.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name: String = "",
    var bought: Boolean = false
)
