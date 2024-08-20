package com.allinx.english.presentation.test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.allinx.english.R
import com.allinx.english.databinding.FragmentTestBinding
import com.allinx.english.extension.openChooseTestPictureScreen
import com.allinx.english.extension.openChooseTestTextEnglishScreen
import com.allinx.english.extension.openChooseTestTextRussianScreen
import com.allinx.english.extension.openWriteTestPictureScreen
import com.allinx.english.extension.openWriteTestTextEnglishScreen
import com.allinx.english.extension.openWriteTestTextRussianScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestFragment : Fragment(R.layout.fragment_test) {

    private lateinit var binding: FragmentTestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTestBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners(){
        with(binding){
            btnChooseTestFirst.setOnClickListener {
                openChooseTestPictureScreen()
            }
            btnChooseTextSecond.setOnClickListener {
                openChooseTestTextEnglishScreen()
            }
            btnChooseTextThird.setOnClickListener {
                openChooseTestTextRussianScreen()
            }
            btnWriteTestFirst.setOnClickListener {
                openWriteTestPictureScreen()
            }
            btnWriteTextSecond.setOnClickListener {
                openWriteTestTextEnglishScreen()
            }
            btnWriteTextThird.setOnClickListener {
                openWriteTestTextRussianScreen()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = TestFragment()
    }
}