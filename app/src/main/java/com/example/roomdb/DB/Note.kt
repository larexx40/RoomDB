package com.example.roomdb.DB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note (
    var title: String,
    var note: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int =0
}