package com.allinx.english.di

import com.allinx.domain.repository.IWordRepository
import com.allinx.domain.usecases.DeleteWordUseCase
import com.allinx.domain.usecases.GetAllWordsUseCase
import com.allinx.domain.usecases.GetQuestionsEnglishUseCase
import com.allinx.domain.usecases.GetQuestionsRussianUseCase
import com.allinx.domain.usecases.GetSizeUseCase
import com.allinx.domain.usecases.GetWordByIdUseCase
import com.allinx.domain.usecases.InsertWordUseCase
import com.allinx.domain.usecases.UpdateWordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetAllWordsUseCase(repository: IWordRepository): GetAllWordsUseCase {
        return GetAllWordsUseCase(repository)
    }

    @Provides
    fun provideGetWordByIdUseCase(repository: IWordRepository): GetWordByIdUseCase {
        return GetWordByIdUseCase(repository)
    }

    @Provides
    fun provideInsertWordUseCase(repository: IWordRepository): InsertWordUseCase {
        return InsertWordUseCase(repository)
    }

    @Provides
    fun provideUpdateWordUseCase(repository: IWordRepository): UpdateWordUseCase {
        return UpdateWordUseCase(repository)
    }

    @Provides
    fun provideDeleteWordUseCase(repository: IWordRepository): DeleteWordUseCase {
        return DeleteWordUseCase(repository)
    }

    @Provides
    fun provideGetSizeUseCase(repository: IWordRepository): GetSizeUseCase {
        return GetSizeUseCase(repository)
    }

    @Provides
    fun provideGetQuestionsEnglishUseCase(repository: IWordRepository): GetQuestionsEnglishUseCase {
        return GetQuestionsEnglishUseCase(repository)
    }

    @Provides
    fun provideGetQuestionsRussianUseCase(repository: IWordRepository): GetQuestionsRussianUseCase {
        return GetQuestionsRussianUseCase(repository)
    }
}