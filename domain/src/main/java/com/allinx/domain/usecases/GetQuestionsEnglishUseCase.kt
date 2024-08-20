package com.allinx.domain.usecases

import com.allinx.domain.models.Question
import com.allinx.domain.repository.IWordRepository

class GetQuestionsEnglishUseCase (private val repository: IWordRepository) {
    suspend fun execute(): List<Question> {
        return repository.getQuestionsEnglish()
    }
}