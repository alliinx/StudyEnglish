package com.allinx.domain.usecases

import com.allinx.domain.models.Word
import com.allinx.domain.repository.IWordRepository

class UpdateWordUseCase (private val repository: IWordRepository) {
    suspend fun execute(word: Word, id: Long) {
        repository.update(word, id)
    }
}