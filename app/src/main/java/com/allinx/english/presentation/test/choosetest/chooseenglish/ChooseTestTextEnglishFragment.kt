package com.allinx.english.presentation.test.choosetest.chooseenglish

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.allinx.domain.models.Question
import com.allinx.english.R
import com.allinx.english.databinding.FragmentChooseTestTextBinding
import com.allinx.english.extension.hideLoading
import com.allinx.english.extension.navigateBack
import com.allinx.english.extension.openFinishFromChooseTestTextEnglishScreen
import com.allinx.english.extension.showLoading
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChooseTestTextEnglishFragment : Fragment() {

    private lateinit var binding: FragmentChooseTestTextBinding

    private val questionsViewModel: QuestionsViewModel by viewModels()

    private lateinit var listQuestions: List<Question>
    private var position: Int = -1
    private var right = 0
    private var wrong = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseTestTextBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        questionsViewModel.getQuestions()

        initObservers()
        initListeners()
    }

    private fun initObservers() {
        questionsViewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                showLoading()
            } else {
                hideLoading()
            }
        }
        questionsViewModel.resultQuestions.observe(viewLifecycleOwner) {
            setQuestions(it)
        }
        lifecycleScope.launch{
            questionsViewModel.uiMessageChannel.collect {
                Toast.makeText(requireActivity(),getString(it), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setQuestions(list: List<Question>){
        listQuestions = list
        position = 0
        updateUI()
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(){
        if (position >= listQuestions.size){
            openFinishFromChooseTestTextEnglishScreen(right,wrong)
        }
        else{
            with(binding) {
                btnFirstAnswer.text = listQuestions[position].optionsEnglish[0]
                btnSecondAnswer.text = listQuestions[position].optionsEnglish[1]
                btnThirdAnswer.text = listQuestions[position].optionsEnglish[2]
                btnFourthAnswer.text = listQuestions[position].optionsEnglish[3]
                wordChooseTest.text = listQuestions[position].word.meaning
                kolQuestion.text = "${position+1}/${listQuestions.size}"
            }
        }
    }

    private fun setButtonColor(button: Button){
        if(checkAnswer(button.text.toString())){
            button.background = ContextCompat.getDrawable(requireContext(),
                R.drawable.bg_button_test_right_answer_selector)
        }
        else {
            button.background = ContextCompat.getDrawable(requireContext(),
                R.drawable.bg_button_test_wrong_answer_selector)
        }
    }

    private fun checkAnswer(answer: String): Boolean {
        return answer == listQuestions[position].word.word
    }

    private fun toNextQuestion(button: Button){
        if(checkAnswer(button.text.toString())){
            right += 1
        }
        else{
            wrong += 1
        }
        button.background = ContextCompat.getDrawable(requireContext(),
            R.drawable.bg_button_selector)
        position += 1
        updateUI()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListeners(){
        with(binding) {
            btnBack.setOnClickListener {
                navigateBack()
            }
            btnFirstAnswer.setOnClickListener {
                toNextQuestion(btnFirstAnswer)
            }
            btnFirstAnswer.setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        setButtonColor(btnFirstAnswer)
                    }
                }
                false
            }
            btnSecondAnswer.setOnClickListener {
                toNextQuestion(btnSecondAnswer)
            }
            btnSecondAnswer.setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        setButtonColor(btnSecondAnswer)
                    }
                }
                false
            }
            btnThirdAnswer.setOnClickListener {
                toNextQuestion(btnThirdAnswer)
            }
            btnThirdAnswer.setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        setButtonColor(btnThirdAnswer)
                    }
                }
                false
            }
            btnFourthAnswer.setOnClickListener {
                toNextQuestion(btnFourthAnswer)
            }
            btnFourthAnswer.setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        setButtonColor(btnFourthAnswer)
                    }
                }
                false
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ChooseTestTextEnglishFragment()
    }
}