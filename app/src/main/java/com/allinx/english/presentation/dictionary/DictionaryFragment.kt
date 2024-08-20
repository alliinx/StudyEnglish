package com.allinx.english.presentation.dictionary

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.allinx.domain.models.Word
import com.allinx.english.R
import com.allinx.english.databinding.FragmentDictionaryBinding
import com.allinx.english.extension.hideLoading
import com.allinx.english.extension.openCreatingScreen
import com.allinx.english.extension.openDetailedScreen
import com.allinx.english.extension.showLoading
import com.allinx.english.presentation.dictionary.adapter.WordAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DictionaryFragment : Fragment(R.layout.fragment_dictionary) {

    private lateinit var binding: FragmentDictionaryBinding
    private lateinit var wordAdapter: WordAdapter

    private val dictionaryViewModel: DictionaryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDictionaryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        wordAdapter = WordAdapter { openDetailedScreen(it) }

        dictionaryViewModel.getAllWords()

        initObservers()
        initListeners()
        initRecycler()
    }

    private fun initObservers() {
        dictionaryViewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                showLoading()
            } else {
                hideLoading()
            }
        }
        dictionaryViewModel.resultWords.observe(viewLifecycleOwner) {
            setAdapter(it)
        }
        lifecycleScope.launch{
            dictionaryViewModel.uiMessageChannel.collect {
                Toast.makeText(requireActivity(),getString(it),Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initListeners() {
        binding.btnAddWord.setOnClickListener {
            openCreatingScreen()
        }
    }

    private fun initRecycler() {
        binding.rvWords.apply{
            adapter = wordAdapter
        }
    }

    private fun setAdapter(list: List<Word>) {
        wordAdapter.submitList(list.toMutableList())
    }

    companion object {
        @JvmStatic
        fun newInstance() = DictionaryFragment()
    }
}