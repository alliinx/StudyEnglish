package com.allinx.domain.usecases

import com.allinx.domain.models.Question
import com.allinx.domain.repository.IWordRepository

class GetQuestionsRussianUseCase (private val repository: IWordRepository) {
    suspend fun execute(): List<Question> {
        return repository.getQuestionsRussian()
    }
}