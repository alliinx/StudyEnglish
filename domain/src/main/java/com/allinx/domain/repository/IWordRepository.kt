package com.allinx.domain.repository

import com.allinx.domain.models.Question
import com.allinx.domain.models.Word

interface IWordRepository {
    suspend fun getAllWords(): List<Word>
    suspend fun getWordById(id: Long): Word
    suspend fun insertWord(word: Word)
    suspend fun update(word: Word, id: Long)
    suspend fun deleteWordById(id: Long)
    suspend fun getSize() : Int
    suspend fun getQuestionsEnglish(): List<Question>
    suspend fun getQuestionsRussian(): List<Question>
}