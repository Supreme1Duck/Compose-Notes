package com.example.duck.fastnotes.di

import android.app.Application
import androidx.room.Room
import com.example.duck.fastnotes.database.NotesDatabase
import com.example.duck.fastnotes.database.NotesDatabase.Companion.DB_NAME
import com.example.duck.fastnotes.domain.repository.NotesRepository
import com.example.duck.fastnotes.domain.repository.UserInfoRepository
import com.example.duck.fastnotes.domain.usecase.*
import com.example.duck.fastnotes.manager.NotesRepositoryManager
import com.example.duck.fastnotes.manager.UserInfoManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NotesDatabase {
        return Room.databaseBuilder(
            app,
            NotesDatabase::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTasksRepository(db: NotesDatabase): NotesRepository {
        return NotesRepositoryManager(db.notesDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCase(repository: NotesRepository): NotesUseCase {
        return NotesUseCase(
            getTasks = GetNotes(repository),
            insertNote = InsertNote(repository)
        )
    }

    @Provides
    @Singleton
    fun provideEditNoteUseCase(repository: NotesRepository): EditNoteUseCase {
        return EditNoteUseCase(
                getNoteById = GetNoteById(repository),
                insertNote = InsertNote(repository),
                deleteNote = DeleteNote(repository)
        )
    }

    @Provides
    @Singleton
    fun provideUserInfoRepository(): UserInfoRepository {
        return UserInfoManager()
    }
}