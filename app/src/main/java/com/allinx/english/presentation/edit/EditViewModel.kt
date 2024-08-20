package com.allinx.english.presentation.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.allinx.domain.models.Word
import com.allinx.domain.usecases.InsertWordUseCase
import com.allinx.domain.usecases.UpdateWordUseCase
import com.allinx.english.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val updateWordByIdUseCase: UpdateWordUseCase,
    private val insertWordUseCase: InsertWordUseCase
) : ViewModel(){
    private val _uiMessageChannel: MutableSharedFlow<Int> = MutableSharedFlow()
    val uiMessageChannel = _uiMessageChannel.asSharedFlow()

    private val loadingStateMutable = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = loadingStateMutable

    fun updateWord(word: Word, id: Long) {
        loadingStateMutable.value = true
        viewModelScope.launch {
            try {
                updateWordByIdUseCase.execute(word, id)
            } catch (e: Exception) {
                _uiMessageChannel.emit(R.string.error_update)
            } finally {
                loadingStateMutable.value = false
            }
        }
    }

    fun insertWord(word: Word) {
        loadingStateMutable.value = true
        viewModelScope.launch {
            try {
                insertWordUseCase.execute(word)
            } catch (e: Exception) {
                _uiMessageChannel.emit(R.string.error_insert)
            } finally {
                loadingStateMutable.value = false
            }
        }
    }
}