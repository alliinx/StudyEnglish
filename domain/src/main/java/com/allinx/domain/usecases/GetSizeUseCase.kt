package com.allinx.domain.usecases

import com.allinx.domain.repository.IWordRepository

class GetSizeUseCase (private val repository: IWordRepository) {
    suspend fun execute() {
        repository.getSize()
    }
}