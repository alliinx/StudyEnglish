package com.allinx.english.presentation.test.writetest

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.allinx.domain.models.Word
import com.allinx.english.R
import com.allinx.english.databinding.FragmentWriteTestTextBinding
import com.allinx.english.extension.hideLoading
import com.allinx.english.extension.navigateBack
import com.allinx.english.extension.openFinishFromWriteTestPictureScreen
import com.allinx.english.extension.openFinishFromWriteTestTextEnglishScreen
import com.allinx.english.extension.showLoading
import com.allinx.english.presentation.dictionary.DictionaryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WriteTestTextEnglishFragment : Fragment() {

    private lateinit var binding: FragmentWriteTestTextBinding

    private val dictionaryViewModel: DictionaryViewModel by viewModels()

    private lateinit var listWords: List<Word>
    private var position: Int = -1
    private var right = 0
    private var wrong = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWriteTestTextBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dictionaryViewModel.getAllWords()

        initObservers()
        initListeners()
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
            setWords(it)
        }
        lifecycleScope.launch{
            dictionaryViewModel.uiMessageChannel.collect {
                Toast.makeText(requireActivity(),getString(it), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setWords(list: List<Word>){
        listWords = list
        position = 0
        updateUI()
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(){
        if(position == listWords.size){
            openFinishFromWriteTestTextEnglishScreen(right,wrong)
        }
        else {
            if(position == listWords.size - 1){
                binding.btnNext.text = getString(R.string.btn_finish_text)
            }
            with(binding) {
                tietWord.setText("")
                tietWord.text = null
                wordWriteTest.text = listWords[position].meaning
                kolQuestion.text = "${position+1}/${listWords.size}"
            }
        }
    }

    private fun isAnswer(): Boolean{
        return binding.tietWord.text.toString().isNotBlank()
    }

    private fun checkAnswer(answer: String){
        if (answer.equals(listWords[position].word, true)) {
            right += 1
        }
        else{
            wrong += 1
        }
    }

    private fun toNextQuestion(){
        if(!isAnswer()){
            binding.tilWord.error = getString(R.string.error_input_word)
        }
        else {
            binding.tilWord.error = null
            checkAnswer(binding.tietWord.text.toString())
            position += 1
            updateUI()
        }
    }

    private fun initListeners(){
        with(binding){
            btnNext.setOnClickListener {
                toNextQuestion()
            }
            tietWord.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if(isAnswer()){
                        tilWord.error = null
                    }
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int
                ) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int
                ) {}
            })
            tietWord.setOnKeyListener(View.OnKeyListener { _, keyCode, keyEvent ->
                if (keyEvent.action == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    toNextQuestion()
                    return@OnKeyListener true
                }
                false
            })
            btnBack.setOnClickListener {
                navigateBack()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = WriteTestTextEnglishFragment()
    }
}