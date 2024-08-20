package com.allinx.domain.usecases

import com.allinx.domain.models.Word
import com.allinx.domain.repository.IWordRepository

class InsertWordUseCase (private val repository: IWordRepository) {
    suspend fun execute(word: Word) {
        repository.insertWord(word)
    }
}