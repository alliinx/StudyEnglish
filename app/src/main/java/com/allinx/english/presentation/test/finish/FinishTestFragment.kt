package com.allinx.english.presentation.test.finish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.allinx.english.Constants.RIGHT
import com.allinx.english.Constants.WRONG
import com.allinx.english.R
import com.allinx.english.databinding.FragmentFinishTestBinding
import com.allinx.english.extension.backToTestScreen
import com.allinx.english.presentation.test.finish.customview.AnalyticalPieChart
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FinishTestFragment : Fragment() {

    private lateinit var binding: FragmentFinishTestBinding

    private var right = 0
    private var wrong = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFinishTestBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkArguments()
        setChart()
        initListeners()
    }

    private fun checkArguments() {
        right = arguments?.getInt(RIGHT) ?: 0
        wrong = arguments?.getInt(WRONG) ?: 0
    }

    private fun setChart(){
        with(binding) {
            analyticalPieChart.setDataChart(
                listOf(Pair(right, "Верно"), Pair(wrong, "Неверно"))
            )
            analyticalPieChart.startAnimation()
        }
    }

    private fun initListeners(){
        binding.btnToTest.setOnClickListener {
            backToTestScreen()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FinishTestFragment()
    }
}