package com.example.duck.fastnotes.di

import android.app.Application
import androidx.room.Room
import com.example.duck.fastnotes.database.db.TaskDatabase
import com.example.duck.fastnotes.database.db.TaskDatabase.Companion.DB_NAME
import com.example.duck.fastnotes.domain.repository.TasksRepository
import com.example.duck.fastnotes.domain.usecase.DeleteTask
import com.example.duck.fastnotes.domain.usecase.GetTasks
import com.example.duck.fastnotes.domain.usecase.InsertTask
import com.example.duck.fastnotes.domain.usecase.TasksUseCase
import com.example.duck.fastnotes.manager.TasksRepositoryManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton

@Module
@InstallIn(Singleton::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): TaskDatabase {
        return Room.databaseBuilder(
            app,
            TaskDatabase::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTasksRepository(db: TaskDatabase): TasksRepository {
        return TasksRepositoryManager(db.taskDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCase(repository: TasksRepository): TasksUseCase {
        return TasksUseCase(
            getTasks = GetTasks(repository),
            insertTask = InsertTask(repository),
            deleteTask = DeleteTask(repository)
        )
    }

}