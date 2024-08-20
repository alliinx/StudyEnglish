package com.allinx.english.presentation.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.allinx.domain.models.Word
import com.allinx.english.Constants.NULL_WORD_ID
import com.allinx.english.Constants.WORD_ID
import com.allinx.english.R
import com.allinx.english.databinding.FragmentDetailBinding
import com.allinx.english.extension.hideLoading
import com.allinx.english.extension.navigateBack
import com.allinx.english.extension.openEditingScreen
import com.allinx.english.extension.showLoading
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()

    private var wordId: Long = NULL_WORD_ID

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkArguments()
        initObservers()
        initListeners()
    }

    private fun checkArguments() {
        wordId = arguments?.getLong(WORD_ID) ?: NULL_WORD_ID
        detailViewModel.getWordById(wordId)
    }

    private fun initObservers() {
        detailViewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                showLoading()
            } else {
                hideLoading()
            }
        }
        detailViewModel.resultWord.observe(viewLifecycleOwner) {
            updateUi(it)
        }
        lifecycleScope.launch{
            detailViewModel.uiMessageChannel.collect {
                Toast.makeText(requireActivity(),getString(it), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initListeners() {
        with(binding) {
            btnEdit.setOnClickListener {
                openEditingScreen(wordId)
            }
            btnBack.setOnClickListener {
                navigateBack()
            }
            btnDelete.setOnClickListener {
                detailViewModel.deleteWordById(wordId)
                navigateBack()
            }
        }
    }

    private fun updateUi(word: Word) {
        with(binding) {
            imgDetail.load(word.image)
            wordTitleDetails.text = word.word
            meaningDetails.text = word.meaning
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = DetailFragment()
    }
}