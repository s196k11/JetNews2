package com.example.jetnews.di

import android.content.Context
import androidx.room.Room
import com.example.jetnews.Data.UserDataDao
import com.example.jetnews.Data.UserDatabase
import com.example.jetnews.Model.UserData
import com.example.jetnews.Network.NewsAPI
import com.example.jetnews.Repository.NewsRepository
import com.example.jetnews.constants.Constants
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Singleton
    @Provides
    fun provideNewsApi(): NewsAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Singleton
    @Provides
    fun provideUserDatabase(@ApplicationContext context: Context): UserDatabase =
        Room.databaseBuilder(context, UserDatabase::class.java, "userDatabase")
            .fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideUserDao(userDatabase: UserDatabase):UserDataDao = userDatabase.userDataDao()



}