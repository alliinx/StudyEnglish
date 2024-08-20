package com.allinx.data.storage.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "english_words")
data class WordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(collate = ColumnInfo.NOCASE) var word: String,
    @ColumnInfo(collate = ColumnInfo.NOCASE) var meaning: String,
    var image: String
)
