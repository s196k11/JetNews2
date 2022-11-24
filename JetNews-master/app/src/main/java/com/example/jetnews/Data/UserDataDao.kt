package com.example.jetnews.Data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.jetnews.Model.UserData
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDataDao {

    @Query("SELECT * FROM USERDATATBL")
    fun getUserData():Flow<List<UserData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserData(userData: UserData)

    @Query("UPDATE UserDataTbl SET email=:email")
    fun updateEmail(email:String)

    @Query("UPDATE UserDataTbl SET UserName=:userName")
    fun updateUserName(userName:String)

    @Query("UPDATE UserDataTbl SET AboutYou=:aboutYou")
    fun updateAboutYou(aboutYou:String)


}