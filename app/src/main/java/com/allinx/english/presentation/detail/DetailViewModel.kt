package com.allinx.english.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.allinx.domain.models.Word
import com.allinx.domain.usecases.DeleteWordUseCase
import com.allinx.domain.usecases.GetWordByIdUseCase
import com.allinx.english.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getWordByIdUseCase: GetWordByIdUseCase,
    private val deleteWordUseCase: DeleteWordUseCase
) : ViewModel(){
    private val wordMutable = MutableLiveData<Word>()
    val resultWord: LiveData<Word> = wordMutable

    private val _uiMessageChannel: MutableSharedFlow<Int> = MutableSharedFlow()
    val uiMessageChannel = _uiMessageChannel.asSharedFlow()

    private val loadingStateMutable = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = loadingStateMutable

    fun getWordById(id: Long) {
        loadingStateMutable.value = true
        viewModelScope.launch {
            try {
                val words = getWordByIdUseCase.execute(id)
                wordMutable.value = words
            } catch (e: Exception) {
                _uiMessageChannel.emit(R.string.error_database)
            } finally {
                loadingStateMutable.value = false
            }
        }
    }

    fun deleteWordById(id: Long) {
        loadingStateMutable.value = true
        viewModelScope.launch {
            try {
                deleteWordUseCase.execute(id)
            } catch (e: Exception) {
                _uiMessageChannel.emit(R.string.error_database)
            } finally {
                loadingStateMutable.value = false
            }
        }
    }
}