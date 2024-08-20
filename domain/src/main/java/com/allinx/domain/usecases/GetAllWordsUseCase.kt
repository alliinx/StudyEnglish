package com.allinx.domain.usecases

import com.allinx.domain.models.Word
import com.allinx.domain.repository.IWordRepository

class GetAllWordsUseCase (private val repository: IWordRepository) {
    suspend fun execute(): List<Word> {
        return repository.getAllWords()
    }
}