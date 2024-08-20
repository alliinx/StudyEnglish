package com.allinx.english.presentation.study

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.animation.doOnEnd
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.allinx.domain.models.Word
import com.allinx.english.R
import com.allinx.english.databinding.FragmentDictionaryBinding
import com.allinx.english.databinding.FragmentStudyBinding
import com.allinx.english.extension.hideLoading
import com.allinx.english.extension.openDetailedScreen
import com.allinx.english.extension.showLoading
import com.allinx.english.presentation.dictionary.DictionaryViewModel
import com.allinx.english.presentation.dictionary.adapter.WordAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StudyFragment : Fragment(R.layout.fragment_study) {

    private lateinit var binding: FragmentStudyBinding
    private lateinit var listWords: List<Word>
    private val dictionaryViewModel: DictionaryViewModel by viewModels()
    private var position: Int = -1
    private var isFront: Boolean = true
    private lateinit var frontAnim: AnimatorSet
    private lateinit var backAnim: AnimatorSet

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudyBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dictionaryViewModel.getAllWords()

        initObservers()
        initListeners()
        setAnimation()
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
        with(binding) {
            imgStudyWord.load(listWords[position].image)
            imgStudyWord.visibility = View.VISIBLE
            backStudyCard.visibility = View.GONE
            wordStudy.text = listWords[position].word
            kolWords.text = "${position+1}/${listWords.size}"
        }
        when (position) {
            0 -> {
                binding.btnBack.visibility = View.GONE
                binding.btnNext.visibility = View.VISIBLE
            }
            listWords.size - 1 -> {
                binding.btnNext.visibility = View.GONE
                binding.btnBack.visibility = View.VISIBLE
            }
            else -> {
                binding.btnBack.visibility = View.VISIBLE
                binding.btnNext.visibility = View.VISIBLE
            }
        }
    }

    private fun initListeners(){
        with(binding){
            btnNext.setOnClickListener {
                position += 1
                isFront = false
                flipCard()
                updateUI()
            }
            btnBack.setOnClickListener {
                position -= 1
                isFront = false
                flipCard()
                updateUI()
            }
            btnFlip.setOnClickListener {
                flipCard()
            }
            imgStudyWord.setOnClickListener {
                flipCard()
            }
        }
    }

    private fun setAnimation(){
        val visibleView = binding.imgStudyWord
        val inVisibleView = binding.backStudyCard
        val scale = requireContext().resources.displayMetrics.density
        val cameraDist = 8000 * scale
        visibleView.cameraDistance = cameraDist
        inVisibleView.cameraDistance = cameraDist
        frontAnim =
            AnimatorInflater.loadAnimator(
                context,
                R.animator.front_animator
            ) as AnimatorSet
        backAnim =
            AnimatorInflater.loadAnimator(
                context,
                R.animator.back_animator
            ) as AnimatorSet
    }

    private fun flipCard() {
        if (isFront) {
            frontAnim.setTarget(binding.imgStudyWord);
            backAnim.setTarget(binding.backStudyCard);
            frontAnim.start()
            backAnim.start()
            backAnim.doOnEnd {
                binding.backStudyCard.visibility = View.VISIBLE
            }
            isFront = false
        }
        else {
            frontAnim.setTarget(binding.backStudyCard)
            backAnim.setTarget(binding.imgStudyWord)
            backAnim.start()
            frontAnim.start()
            isFront = true
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = StudyFragment()
    }
}