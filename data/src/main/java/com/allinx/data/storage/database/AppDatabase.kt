package com.allinx.data.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.allinx.data.Constants.DATABASE_VERSION_CODE
import com.allinx.data.storage.database.dao.WordDao
import com.allinx.data.storage.database.entities.WordEntity

@Database(entities = [WordEntity::class], version = DATABASE_VERSION_CODE)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
}