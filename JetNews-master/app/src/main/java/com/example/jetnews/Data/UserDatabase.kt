package com.example.jetnews.Data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jetnews.Model.UserData
import javax.inject.Inject


@Database(entities = arrayOf(UserData::class), version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase(){

    abstract fun userDataDao():UserDataDao

}