package com.allinx.english.presentation.dictionary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.allinx.domain.models.Word
import com.allinx.english.databinding.ItemWordBinding

class WordHolder (
    private val binding: ItemWordBinding,
    private val action: (Long) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Word) {
        with(binding) {
            titleDictionaryRv.text = item.word
            imgDictionaryRv.load(item.image)
        }
        itemView.setOnClickListener {
            action(item.id)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            action: (Long) -> Unit
        ) = WordHolder(
            ItemWordBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            action
        )
    }
}