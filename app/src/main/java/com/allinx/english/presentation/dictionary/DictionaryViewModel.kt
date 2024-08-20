package com.allinx.english.presentation.dictionary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.allinx.domain.models.Word
import com.allinx.domain.usecases.GetAllWordsUseCase
import com.allinx.english.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel @Inject constructor(
    private val getAllWordsUseCase: GetAllWordsUseCase
) : ViewModel(){
    private val wordsMutable = MutableLiveData<List<Word>>()
    val resultWords: LiveData<List<Word>> = wordsMutable

    private val _uiMessageChannel: MutableSharedFlow<Int> = MutableSharedFlow()
    val uiMessageChannel = _uiMessageChannel.asSharedFlow()

    private val loadingStateMutable = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = loadingStateMutable

    fun getAllWords() {
        loadingStateMutable.value = true
        viewModelScope.launch {
            try {
                val words = getAllWordsUseCase.execute()
                wordsMutable.value = words
            } catch (e: Exception) {
                _uiMessageChannel.emit(R.string.error_database)
            } finally {
                loadingStateMutable.value = false
            }
        }
    }
}