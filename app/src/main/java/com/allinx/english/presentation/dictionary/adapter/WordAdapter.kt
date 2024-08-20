package com.allinx.english.presentation.dictionary.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.allinx.domain.models.Word

class WordAdapter (
    private val action: (Long) -> Unit
) : ListAdapter<Word, WordHolder>(WordDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        WordHolder.create(parent, action)

    override fun onBindViewHolder(holder: WordHolder, position: Int) =
        holder.bind(getItem(position))

    override fun submitList(list: MutableList<Word>?) {
        super.submitList(
            if (list == null) null
            else ArrayList(list)
        )
    }
}