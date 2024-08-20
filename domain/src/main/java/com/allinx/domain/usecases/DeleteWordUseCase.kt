package com.allinx.domain.usecases

import com.allinx.domain.models.Word
import com.allinx.domain.repository.IWordRepository

class DeleteWordUseCase (private val repository: IWordRepository) {
    suspend fun execute(id: Long) {
        repository.deleteWordById(id)
    }
}