package com.allinx.english.presentation.dictionary.adapter

import androidx.recyclerview.widget.DiffUtil
import com.allinx.domain.models.Word

class WordDiffCallback : DiffUtil.ItemCallback<Word>() {

    override fun areItemsTheSame(
        oldItem: Word,
        newItem: Word
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Word,
        newItem: Word
    ): Boolean = oldItem == newItem
}