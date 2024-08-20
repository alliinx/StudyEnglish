package com.allinx.data.mappers

import com.allinx.data.storage.database.entities.WordEntity
import com.allinx.domain.models.Word

object WordMapper {
    fun WordEntity.toDomain(): Word = this.let {
        Word(
            id = it.id,
            word = it.word,
            meaning = it.meaning,
            image = it.image
        )
    }
    fun Word.toEntity(): WordEntity = this.let {
        WordEntity(
            id = it.id,
            word = it.word,
            meaning = it.meaning,
            image = it.image
        )
    }
}