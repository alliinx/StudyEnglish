package com.allinx.english.di

import android.content.Context
import androidx.room.Room
import com.allinx.data.repository.WordRepository
import com.allinx.data.storage.database.AppDatabase
import com.allinx.data.storage.database.dao.WordDao
import com.allinx.domain.repository.IWordRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "word_database"
        ).createFromAsset("database/english_words.db").build()
    }

    @Provides
    fun provideWordDao(database: AppDatabase) = database.wordDao()

    @Provides
    @Singleton
    fun provideWordRepository(
        wordDao: WordDao
    ): IWordRepository {
        return WordRepository(wordDao)
    }

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }
}