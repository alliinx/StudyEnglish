package com.allinx.english.presentation.edit

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.allinx.domain.models.Word
import com.allinx.english.Constants.NULL_WORD_ID
import com.allinx.english.Constants.REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE
import com.allinx.english.Constants.WORD_ID
import com.allinx.english.R
import com.allinx.english.databinding.FragmentDetailBinding
import com.allinx.english.databinding.FragmentEditBinding
import com.allinx.english.extension.hideLoading
import com.allinx.english.extension.navigateBack
import com.allinx.english.extension.openEditingScreen
import com.allinx.english.extension.showLoading
import com.allinx.english.presentation.detail.DetailViewModel
import com.allinx.english.presentation.dictionary.DictionaryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditFragment : Fragment(R.layout.fragment_edit) {

    private lateinit var binding: FragmentEditBinding
    private val detailViewModel: DetailViewModel by viewModels()
    private val editViewModel: EditViewModel by viewModels()

    private var wordId: Long = NULL_WORD_ID
    private var wordUri: String = ""

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                binding.imgEdit.load(it)
                wordUri = it.toString()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkArguments()
        observeDetail()
        observeEdit()
        initListeners()
    }

    private fun checkArguments() {
        wordId = arguments?.getLong(WORD_ID) ?: NULL_WORD_ID
        if (wordId != NULL_WORD_ID)
            detailViewModel.getWordById(wordId)
    }

    private fun observeDetail() {
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

    private fun observeEdit() {
        editViewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                showLoading()
            } else {
                hideLoading()
            }
        }
        lifecycleScope.launch{
            editViewModel.uiMessageChannel.collect {
                Toast.makeText(requireActivity(),getString(it), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUi(word: Word) {
        with(binding) {
            wordTitleDetails.text = getString(R.string.edit_title_toolbar)
            imgEdit.load(word.image)
            tietWord.setText(word.word)
            tietMeaning.setText(word.meaning)
        }
        wordUri = word.image
    }

    private fun initListeners() {
        with(binding) {
            btnSave.setOnClickListener {
                val word = getInfo()
                word?.let {
                    when(wordId) {
                        NULL_WORD_ID -> editViewModel.insertWord(word)
                        else -> editViewModel.updateWord(word, wordId)
                    }
                    navigateBack()
                }
            }
            btnBack.setOnClickListener {
                navigateBack()
            }
            imgEdit.setOnClickListener {
                val permissionStatus =
                    ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)

                if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery()
                } else {
                    ActivityCompat.requestPermissions(
                        requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE
                    )
                }
            }
            tietWord.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if(isWordText()){
                        tilWord.error = null
                    }
                    else{
                        tilWord.error = getString(R.string.error_input_word)
                    }
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int
                ) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int
                ) {}
            })
            tietMeaning.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if(isWordText()){
                        tilMeaning.error = null
                    }
                    else{
                        tilMeaning.error = getString(R.string.error_input_meaning)
                    }
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int
                ) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int
                ) {}
            })
        }
    }

    private fun pickImageFromGallery() {
        permissionLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun getInfo(): Word? {
        return if (checkWord()) {
            Word(
                id = wordId,
                image = wordUri,
                word = binding.tietWord.text.toString(),
                meaning = binding.tietMeaning.text.toString()
            )
        } else null
    }

    private fun isWordText() = binding.tietWord.text.toString().isNotBlank()

    private fun isMeaningText() = binding.tietMeaning.text.toString().isNotBlank()

    private fun isImgWord(): Boolean {
        return binding.imgEdit.drawable != R.drawable.placeholder.toDrawable()
    }

    private fun checkWord(): Boolean{
        if (isWordText() && isMeaningText() && isImgWord())
            return true
        else {
            if (!isWordText())
                binding.tilWord.error = getString(R.string.error_input_word)
            if(!isMeaningText())
                binding.tilMeaning.error = getString(R.string.error_input_meaning)
            if(!isImgWord())
                Toast.makeText(requireActivity(), getString(R.string.error_image),Toast.LENGTH_SHORT).show()
            return false
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = EditFragment()
    }
}