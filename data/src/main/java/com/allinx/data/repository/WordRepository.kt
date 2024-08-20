package com.allinx.data.repository

import com.allinx.data.mappers.WordMapper.toDomain
import com.allinx.data.mappers.WordMapper.toEntity
import com.allinx.data.storage.database.dao.WordDao
import com.allinx.domain.models.Question
import com.allinx.domain.models.Word
import com.allinx.domain.repository.IWordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.SecureRandom


class WordRepository (
    private val wordDao: WordDao
): IWordRepository {

    override suspend fun getAllWords(): List<Word> {
        return withContext(Dispatchers.IO) {
            val dataWords = wordDao.getAllWords()
            val domainWords = dataWords.map{ wordEntity ->
                wordEntity.toDomain()
            }
            domainWords
        }
    }

    override suspend fun getWordById(id: Long): Word{
        return withContext(Dispatchers.IO) {
            val word = wordDao.getWordById(id).toDomain()
            word
        }
    }

    override suspend fun insertWord(word: Word) {
        withContext(Dispatchers.IO) {
            val dataWord = word.toEntity()
            wordDao.insertWord(dataWord)
        }
    }

    override suspend fun update(word: Word, id: Long) {
        withContext(Dispatchers.IO) {
            wordDao.update(word.word, word.meaning, word.image, id)
        }
    }

    override suspend fun deleteWordById(id: Long) {
        withContext(Dispatchers.IO) {
            wordDao.deleteWordById(id)
        }
    }

    override suspend fun getSize(): Int {
        return withContext(Dispatchers.IO) {
            wordDao.getSize()
        }
    }

    private fun getOptionsEnglish(word: Word, listWord: List<Word>): List<String> {
        val listOptions = mutableListOf<String>()
        val setWords = listWord.toMutableSet()
        setWords.remove(word)
        val random = SecureRandom()
        val list: ArrayList<Word> = ArrayList(setWords)
        for (i in 0 until 3) {
            val randomIndex = random.nextInt(list.size)
            val randomElement = list[randomIndex]
            listOptions.add(randomElement.word)
            println(randomElement)
            list.removeAt(randomIndex)
        }
        val randomIndex = random.nextInt(listOptions.size + 1)
        if(randomIndex!=listOptions.size){
            listOptions.add(randomIndex,word.word)
        }
        else{
            listOptions.add(word.word)
        }
        return listOptions
    }

    override suspend fun getQuestionsEnglish(): List<Question> {
        val listQuestion = mutableListOf<Question>()
        withContext(Dispatchers.IO) {
            val dataWords = wordDao.getAllWords()
            val domainWords = dataWords.map{ wordEntity ->
                wordEntity.toDomain()
            }
            domainWords.forEach { word ->
                val question = Question(word,getOptionsEnglish(word,domainWords))
                listQuestion.add(question)
            }
        }
        return listQuestion
    }

    private fun getOptionsRussian(word: Word, listWord: List<Word>): List<String> {
        val listOptions = mutableListOf<String>()
        val setWords = listWord.toMutableSet()
        setWords.remove(word)
        val random = SecureRandom()
        val list: ArrayList<Word> = ArrayList(setWords)
        for (i in 0 until 3) {
            val randomIndex = random.nextInt(list.size)
            val randomElement = list[randomIndex]
            listOptions.add(randomElement.meaning)
            println(randomElement)
            list.removeAt(randomIndex)
        }
        val randomIndex = random.nextInt(listOptions.size + 1)
        if(randomIndex!=listOptions.size){
            listOptions.add(randomIndex,word.meaning)
        }
        else{
            listOptions.add(word.meaning)
        }
        return listOptions
    }

    override suspend fun getQuestionsRussian(): List<Question> {
        val listQuestion = mutableListOf<Question>()
        withContext(Dispatchers.IO) {
            val dataWords = wordDao.getAllWords()
            val domainWords = dataWords.map{ wordEntity ->
                wordEntity.toDomain()
            }
            domainWords.forEach { word ->
                val question = Question(word,getOptionsRussian(word,domainWords))
                listQuestion.add(question)
            }
        }
        return listQuestion
    }
}