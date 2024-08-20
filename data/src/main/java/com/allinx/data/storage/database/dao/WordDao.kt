package com.allinx.data.storage.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.allinx.data.storage.database.entities.WordEntity

@Dao
interface WordDao {
    @Query("SELECT * FROM english_words")
    fun getAllWords(): List<WordEntity>

    @Query("SELECT * FROM english_words WHERE id = :id")
    fun getWordById(id: Long): WordEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word:WordEntity)

    @Query("UPDATE english_words SET word = :word, meaning = :meaning, image = :image WHERE id = :id")
    suspend fun update(word: String, meaning: String, image: String, id: Long)

    @Query("DELETE FROM english_words WHERE id = :id")
    suspend fun deleteWordById(id: Long)

    @Query("SELECT COUNT(*) FROM english_words")
    suspend fun getSize() : Int
}