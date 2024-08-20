package com.allinx.english.presentation.test.choosetest.chooseenglish

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.allinx.domain.models.Question
import com.allinx.domain.usecases.GetQuestionsEnglishUseCase
import com.allinx.english.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(
    private val getQuestionsEnglishUseCase: GetQuestionsEnglishUseCase
) : ViewModel(){
    private val questionsMutable = MutableLiveData<List<Question>>()
    val resultQuestions: LiveData<List<Question>> = questionsMutable

    private val _uiMessageChannel: MutableSharedFlow<Int> = MutableSharedFlow()
    val uiMessageChannel = _uiMessageChannel.asSharedFlow()

    private val loadingStateMutable = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = loadingStateMutable

    fun getQuestions() {
        loadingStateMutable.value = true
        viewModelScope.launch {
            try {
                val words = getQuestionsEnglishUseCase.execute()
                questionsMutable.value = words
            } catch (e: Exception) {
                _uiMessageChannel.emit(R.string.error_database)
            } finally {
                loadingStateMutable.value = false
            }
        }
    }
}