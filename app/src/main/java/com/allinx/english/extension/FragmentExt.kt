package com.allinx.english.extension

import android.view.View
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import com.allinx.english.Constants.RIGHT
import com.allinx.english.Constants.WORD_ID
import com.allinx.english.Constants.WRONG
import com.allinx.english.R
import com.allinx.english.presentation.main.MainActivity

fun Fragment.showLoading() {
    (activity as MainActivity).findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
}

fun Fragment.hideLoading() {
    (activity as MainActivity).findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
}

fun Fragment.openDetailedScreen(id: Long) {
    findNavController().navigate(
        R.id.action_dictionaryFragment_to_detailFragment,
        bundleOf(WORD_ID to id)
    )
}

fun Fragment.openEditingScreen(id: Long?) {
    findNavController().navigate(
        R.id.action_detailFragment_to_editFragment,
        bundleOf(WORD_ID to id)
    )
}

fun Fragment.openCreatingScreen() {
    findNavController().navigate(
        R.id.action_dictionaryFragment_to_editFragment,
        bundleOf(WORD_ID to null)
    )
}

fun Fragment.navigateBack() {
    //findNavController().popBackStack()
    findNavController().navigateUp()
}

fun Fragment.openChooseTestPictureScreen(){
    findNavController().navigate(
        R.id.action_test_to_chooseTestPictureFragment
    )
}

fun Fragment.openChooseTestTextEnglishScreen(){
    findNavController().navigate(
        R.id.action_test_to_chooseTestTextEnglishFragment
    )
}

fun Fragment.openChooseTestTextRussianScreen(){
    findNavController().navigate(
        R.id.action_test_to_chooseTestTextRussianFragment
    )
}

fun Fragment.openWriteTestPictureScreen(){
    findNavController().navigate(
        R.id.action_test_to_writeTestPictureFragment
    )
}

fun Fragment.openWriteTestTextEnglishScreen(){
    findNavController().navigate(
        R.id.action_test_to_writeTestTextEnglishFragment
    )
}

fun Fragment.openWriteTestTextRussianScreen(){
    findNavController().navigate(
        R.id.action_test_to_writeTestTextRussianFragment
    )
}

fun Fragment.openFinishFromChooseTestPictureScreen(right: Int, wrong: Int){
    findNavController().navigate(
        R.id.action_chooseTestPictureFragment_to_finishTestFragment,
        bundleOf(RIGHT to right, WRONG to wrong)
    )
}

fun Fragment.openFinishFromChooseTestTextEnglishScreen(right: Int, wrong: Int){
    findNavController().navigate(
        R.id.action_chooseTestTextEnglishFragment_to_finishTestFragment,
        bundleOf(RIGHT to right, WRONG to wrong)
    )
}

fun Fragment.openFinishFromChooseTestTextRussianScreen(right: Int, wrong: Int){
    findNavController().navigate(
        R.id.action_chooseTestTextRussianFragment_to_finishTestFragment,
        bundleOf(RIGHT to right, WRONG to wrong)
    )
}

fun Fragment.openFinishFromWriteTestPictureScreen(right: Int, wrong: Int){
    findNavController().navigate(
        R.id.action_writeTestPictureFragment_to_finishTestFragment,
        bundleOf(RIGHT to right, WRONG to wrong)
    )
}

fun Fragment.openFinishFromWriteTestTextEnglishScreen(right: Int, wrong: Int){
    findNavController().navigate(
        R.id.action_writeTestTextEnglishFragment_to_finishTestFragment,
        bundleOf(RIGHT to right, WRONG to wrong)
    )
}

fun Fragment.openFinishFromWriteTestTextRussianScreen(right: Int, wrong: Int){
    findNavController().navigate(
        R.id.action_writeTestTextRussianFragment_to_finishTestFragment,
        bundleOf(RIGHT to right, WRONG to wrong)
    )
}

fun Fragment.backToTestScreen(){
    findNavController().navigate(
        R.id.action_finishTestFragment_to_test
    )
}